import React, { useState } from "react"
import Controls from "./components/Controls"
import Track from "./components/Track"
import "./TrainApp.css"
import { GetTrackLayoutRequest, AddTrainRequest, GetTrainPositionsRequest } from "./grpc/train_service_pb"
const { ProrailClient } = require("./grpc/train_service_grpc_web_pb.js")

// const { GetTrackLayoutRequest, GetTrackLayoutResponse } = require("./grpc/train_service_pb.js")
// const request = new GetTrackLayoutRequest()
// prorailClient.getTrackLayout(request, {}, function(err, resp) {
//   if (err) {
//     console.log("err", err)
//   } else {
//     console.log("height", resp.getHeight())
//     console.log("width", resp.getWidth())

//     const compontents = resp.getComponentsList()
//     compontents.forEach(c => console.log("pos ", c.getX(), c.getY(), " char = ", c.getChar()))
//   }
// })

const TrainApp = props => {
  const prorailClient = new ProrailClient("http://localhost:8083")
  const [error, setError] = useState()
  const [track, setTrack] = useState()
  const [trains, setTrains] = useState({})

  const getTrack = () => {
    prorailClient.getTrackLayout(new GetTrackLayoutRequest(), {}, function(err, response) {
      if (err) {
        console.log(err)
        setError(`grpc error: ${err}`)
      } else {
        setTrack(response)
      }
    })

    // prorailClient.getTrackLayout actually returns a request that can be used to cancel the call or register event listeners.
  }
  const addTrain = trainId => {
    const addTrainRequest = new AddTrainRequest()
    addTrainRequest.setTrainId(trainId)
    prorailClient.addTrain(addTrainRequest, {}, function(err, response) {
      if (err) {
        console.log(err)
        setError(`grpc error: ${err}`)
      } else {
        if (response.getOk()) {
          window.alert(`Train with id ${trainId} added`)
        } else {
          window.alert(`Train with id ${trainId} could not be added because: ${response.getErr()}`)
        }
      }
    })
  }

  const getPositionUpdates = trainId => {
    const request = new GetTrainPositionsRequest()
    request.setTrainId(trainId)
    const stream = prorailClient.getPositionUpdates(request, {})
    stream.on("data", function(response) {
      console.log("Position Update", response)
      setTrains(currentTrains => {
        return { ...currentTrains, [response.getTrainId()]: response }
      })
    })
    stream.on("status", function(status) {
      console.log(status.code)
      console.log(status.details)
      console.log(status.metadata)
    })
    stream.on("end", function(end) {
      console.log("position stream ended for ", trainId)
      setTrains(currentTrains => {
        const updatedTrains = currentTrains[trainId]
        delete updatedTrains[trainId]
        return updatedTrains
      })
    })
  }

  return (
    <div>
      <div className={"error-box"} style={{ display: error ? "block" : "none" }}>
        Error: {error}
      </div>
      <div className={"container"}>
        <div className={"controls"}>
          <Controls getTrack={getTrack} addTrain={addTrain} getPositionUpdates={getPositionUpdates} />
        </div>
        <div className={"track"}>
          <Track track={track} trains={trains} />
        </div>
      </div>
    </div>
  )
}

export default TrainApp

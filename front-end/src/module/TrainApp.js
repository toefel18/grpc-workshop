import React, { useState } from "react"
import Controls from "./components/Controls"
import Track from "./components/Track"
import "./TrainApp.css"
import { GetTrackLayoutRequest, AddTrainRequest, GetTrainPositionsRequest } from "./grpc/prorail_service_pb"
const { ProrailClient } = require("./grpc/prorail_service_grpc_web_pb.js")

const TrainApp = props => {
  const prorailClient = new ProrailClient("http://localhost:8083")
  const [error, setError] = useState()
  const [track, setTrack] = useState()
  const [trains, setTrains] = useState({})

  const getTrack = () => {
    prorailClient.getTrackLayout(new GetTrackLayoutRequest(), {}, function(err, response) {
      if (err) {
        console.log(err)
        setError(`grpc error: ${JSON.stringify(err)}`)
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
        setError(`grpc error: ${JSON.stringify(err)}`)
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
    stream.on("error", function(err) {
      setError(`grpc error: ${JSON.stringify(err)}`)
    })
  }

  return (
    <div>
      <div className={"error-box"} style={{ display: error ? "block" : "none" }}>
        Error: {error} <button onClick={() => setError("")}>Clear error</button>
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

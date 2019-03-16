import React, { useState } from "react"
import Controls from "./components/Controls"
import Track from "./components/Track"
import "./TrainApp.css"
import { GetTrackLayoutRequest } from "./grpc/gettracklayoutrequest.js"
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

  const getTrack = () => {
    const request = prorailClient.getTrackLayout(new GetTrackLayoutRequest(), {}, function(err, response) {
      if (err) {
        console.log(err)
        setError(`grpc error: ${err}`)
      } else {
        setTrack(response)
      }
    })

    // request can be used to cancel the call or register event listeners.
  }
  const addTrain = trainId => {
    console.log("adding train ", trainId)
  }

  const getPositionUpdates = trainId => {
    console.log("getting position updates for ", trainId)
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
          <Track track={track}>hoi</Track>
        </div>
      </div>
    </div>
  )
}

export default TrainApp

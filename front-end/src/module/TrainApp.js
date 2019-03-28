import React, { useState } from "react"
import Controls from "../components/Controls"
import Track from "../components/Track"
import "./TrainApp.css"

const TrainApp = props => {
  const [error, setError] = useState()
  const [track, setTrack] = useState()
  const [trains, setTrains] = useState({})

  const getTrack = () => {
    setError("get track not implemented yet")
  }

  const addTrain = trainId => {
    setError("addTrain not implemented yet")
  }

  const getPositionUpdates = trainId => {
    setError("getPositionUpdates not implemented yet")
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

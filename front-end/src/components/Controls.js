import React, { useState } from "react"

import "./Controls.css"

const Controls = props => {
  const [newTrainId, setNewTrainId] = useState("")
  const [fetchTrainId, setFetchTrainId] = useState("")

  return (
    <div>
      <h2>Controls</h2>
      <div className={"control-grid"}>
        <button className={"control-button"} onClick={props.getTrack}>
          Get Track
        </button>
        <input
          className={"control-input"}
          type="text"
          onChange={e => setNewTrainId(e.target.value)}
          value={newTrainId}
          placeholder="new train id"
        />
        <button disabled={newTrainId === ""} className={"control-button"} onClick={e => props.addTrain(newTrainId)}>
          Add Train
        </button>
        <input
          className={"control-input"}
          onChange={e => setFetchTrainId(e.target.value)}
          type="text"
          value={fetchTrainId}
          placeholder="fetch train id"
        />
        <button
          disabled={fetchTrainId === ""}
          className={"control-button"}
          onClick={e => props.getPositionUpdates(fetchTrainId)}
        >
          Get live positions
        </button>
      </div>
    </div>
  )
}

export default Controls

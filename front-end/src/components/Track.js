import React from "react"
import { Stage, Layer, Line, Image } from "react-konva"
import Konva from "konva"
import horizontalTrackUrl from "./horizontal.jpg"
import verticalTrackUrl from "./vertical.jpg"
import intersectionTrackUrl from "./intersection.jpg"
import bottomToLeftTrackUrl from "./bottom-to-left.jpg"
import bottomToRightTrackUrl from "./bottom-to-right.jpg"
import topToLeftTrackUrl from "./top-to-left.jpg"
import topToRightTrackUrl from "./top-to-right.jpg"

const simpleDirections = {}
simpleDirections["|"] = new window.Image()
simpleDirections["|"].src = verticalTrackUrl
simpleDirections["-"] = new window.Image()
simpleDirections["-"].src = horizontalTrackUrl
simpleDirections["+"] = new window.Image()
simpleDirections["+"].src = intersectionTrackUrl

const turns = {}
turns["bottom-to-right"] = new window.Image()
turns["bottom-to-right"].src = bottomToRightTrackUrl
turns["bottom-to-left"] = new window.Image()
turns["bottom-to-left"].src = bottomToLeftTrackUrl
turns["top-to-right"] = new window.Image()
turns["top-to-right"].src = topToRightTrackUrl
turns["top-to-left"] = new window.Image()
turns["top-to-left"].src = topToLeftTrackUrl

const Track = ({ track }) => {
  const scale = 36
  if (!track) {
    return <div>No track received yet...</div>
  }

  return (
    <div>
      <div>
        Received a track of {track.getWidth()}x{track.getHeight()}horizontal
      </div>
      <Stage width={track.getWidth() * scale} height={track.getHeight() * scale}>
        <Layer>
          {track.getComponentsList().map(c => {
            const x = c.getX() * scale
            const y = c.getY() * scale
            console.log(c)
            const image = ~"/\\".indexOf(c.getChar()) ? turns[c.getTurnType()] : simpleDirections[c.getChar()]
            return <Image key={`${x}${y}`} x={x} y={y} width={scale} height={scale} image={image} />
          })}
        </Layer>
      </Stage>
    </div>
  )
}

export default Track

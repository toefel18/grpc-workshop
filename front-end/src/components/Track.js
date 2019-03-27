import React from "react"
import { Stage, Layer, Image, Group, Text } from "react-konva"
import horizontalTrackUrl from "./img/horizontal.jpg"
import verticalTrackUrl from "./img/vertical.jpg"
import intersectionTrackUrl from "./img/intersection.jpg"
import bottomToLeftTrackUrl from "./img/bottom-to-left.jpg"
import bottomToRightTrackUrl from "./img/bottom-to-right.jpg"
import topToLeftTrackUrl from "./img/top-to-left.jpg"
import topToRightTrackUrl from "./img/top-to-right.jpg"
import trainUrl from "./img/train.jpg"

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

const trainImage = new window.Image()
trainImage.src = trainUrl

const Track = ({ track, trains }) => {
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
            const image = ~"/\\".indexOf(c.getChar()) ? turns[c.getTurnType()] : simpleDirections[c.getChar()]
            return <Image key={`${x}${y}`} x={x} y={y} width={scale} height={scale} image={image} />
          })}

          {Object.keys(trains).map(trainName => {
            const currentTrain = trains[trainName]
            console.log(trainImage)
            return (
              // offsetX is required for rotating with center as origin
              // direction is 0 for TOP, 1 for RIGHT, etc..
              <Group key={`train-img${currentTrain.getTrainId()}`}>
                <Image
                  x={currentTrain.getX() * scale + scale / 2}
                  y={currentTrain.getY() * scale + scale / 2}
                  offsetX={scale / 2}
                  offsetY={scale / 2}
                  width={scale}
                  height={scale}
                  rotation={90 * currentTrain.getDirection()}
                  image={trainImage}
                />
                <Text
                  key={currentTrain.getTrainId}
                  x={currentTrain.getX() * scale}
                  y={currentTrain.getY() * scale + scale / 2}
                  fontSize={15}
                  stroke={"red"}
                  strokeWidth={1}
                  text={currentTrain.getTrainId()}
                />
              </Group>
            )
          })}
        </Layer>
      </Stage>
    </div>
  )
}

export default Track

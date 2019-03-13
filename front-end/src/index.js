import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import App from "./App";
import * as serviceWorker from "./serviceWorker";
import { resolve } from "url";

ReactDOM.render(<App />, document.getElementById("root"));

const {
  GetTrackLayoutRequest,
  GetTrackLayoutResponse
} = require("./grpc/train_service_pb.js");

const { ProrailClient } = require("./grpc/train_service_grpc_web_pb.js");

const prorailClient = new ProrailClient("http://localhost:8083");
const request = new GetTrackLayoutRequest();

prorailClient.getTrackLayout(request, {}, function(err, resp) {
  if (err) {
    console.log("err", err);
  } else {
    console.log("height", resp.getHeight());
    console.log("width", resp.getWidth());

    const compontents = resp.getComponentsList();
    compontents.forEach(c =>
      console.log("pos ", c.getX(), c.getY(), " char = ", c.getChar())
    );
  }
});

// ...

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();

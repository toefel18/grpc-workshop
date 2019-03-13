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
    resp.
    console.log("resp", resp);
  }
});

// ...

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();

/* eslint-disable */
import * as grpcWeb from 'grpc-web';
import {
  AddTrainRequest,
  AddTrainResponse,
  GetTrackLayoutRequest,
  GetTrackLayoutResponse,
  GetTrainPositionsRequest,
  InfraComponent,
  TrainPositionUpdate} from './train_service_pb';

export class ProrailClient {
  constructor (hostname: string,
               credentials: null | { [index: string]: string; },
               options: null | { [index: string]: string; });

  getTrackLayout(
    request: GetTrackLayoutRequest,
    metadata: grpcWeb.Metadata,
    callback: (err: grpcWeb.Error,
               response: GetTrackLayoutResponse) => void
  ): grpcWeb.ClientReadableStream<GetTrackLayoutResponse>;

  addTrain(
    request: AddTrainRequest,
    metadata: grpcWeb.Metadata,
    callback: (err: grpcWeb.Error,
               response: AddTrainResponse) => void
  ): grpcWeb.ClientReadableStream<AddTrainResponse>;

  getPositionUpdates(
    request: GetTrainPositionsRequest,
    metadata: grpcWeb.Metadata
  ): grpcWeb.ClientReadableStream<TrainPositionUpdate>;

}

export class ProrailPromiseClient {
  constructor (hostname: string,
               credentials: null | { [index: string]: string; },
               options: null | { [index: string]: string; });

  getTrackLayout(
    request: GetTrackLayoutRequest,
    metadata: grpcWeb.Metadata
  ): Promise<GetTrackLayoutResponse>;

  addTrain(
    request: AddTrainRequest,
    metadata: grpcWeb.Metadata
  ): Promise<AddTrainResponse>;

  getPositionUpdates(
    request: GetTrainPositionsRequest,
    metadata: grpcWeb.Metadata
  ): grpcWeb.ClientReadableStream<TrainPositionUpdate>;

}


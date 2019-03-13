/* eslint-disable */
import * as grpcWeb from 'grpc-web';
import {
  GetTrackLayoutRequest,
  GetTrackLayoutResponse,
  InfraComponent} from './train_service_pb';

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

}

export class ProrailPromiseClient {
  constructor (hostname: string,
               credentials: null | { [index: string]: string; },
               options: null | { [index: string]: string; });

  getTrackLayout(
    request: GetTrackLayoutRequest,
    metadata: grpcWeb.Metadata
  ): Promise<GetTrackLayoutResponse>;

}


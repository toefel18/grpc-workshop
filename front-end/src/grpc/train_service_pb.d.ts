/* eslint-disable */
export class AddTrainRequest {
  constructor ();
  getTrainId(): string;
  setTrainId(a: string): void;
  toObject(): AddTrainRequest.AsObject;
  serializeBinary(): Uint8Array;
  static deserializeBinary: (bytes: {}) => AddTrainRequest;
}

export namespace AddTrainRequest {
  export type AsObject = {
    TrainId: string;
  }
}

export class AddTrainResponse {
  constructor ();
  getOk(): boolean;
  setOk(a: boolean): void;
  getErr(): string;
  setErr(a: string): void;
  toObject(): AddTrainResponse.AsObject;
  serializeBinary(): Uint8Array;
  static deserializeBinary: (bytes: {}) => AddTrainResponse;
}

export namespace AddTrainResponse {
  export type AsObject = {
    Ok: boolean;
    Err: string;
  }
}

export class GetTrackLayoutRequest {
  constructor ();
  toObject(): GetTrackLayoutRequest.AsObject;
  serializeBinary(): Uint8Array;
  static deserializeBinary: (bytes: {}) => GetTrackLayoutRequest;
}

export namespace GetTrackLayoutRequest {
  export type AsObject = {
  }
}

export class GetTrackLayoutResponse {
  constructor ();
  getWidth(): number;
  setWidth(a: number): void;
  getHeight(): number;
  setHeight(a: number): void;
  getComponentsList(): InfraComponent[];
  setComponentsList(a: InfraComponent[]): void;
  toObject(): GetTrackLayoutResponse.AsObject;
  serializeBinary(): Uint8Array;
  static deserializeBinary: (bytes: {}) => GetTrackLayoutResponse;
}

export namespace GetTrackLayoutResponse {
  export type AsObject = {
    Width: number;
    Height: number;
    ComponentsList: InfraComponent[];
  }
}

export class GetTrainPositionsRequest {
  constructor ();
  getTrainId(): string;
  setTrainId(a: string): void;
  toObject(): GetTrainPositionsRequest.AsObject;
  serializeBinary(): Uint8Array;
  static deserializeBinary: (bytes: {}) => GetTrainPositionsRequest;
}

export namespace GetTrainPositionsRequest {
  export type AsObject = {
    TrainId: string;
  }
}

export class InfraComponent {
  constructor ();
  getX(): number;
  setX(a: number): void;
  getY(): number;
  setY(a: number): void;
  getChar(): string;
  setChar(a: string): void;
  getTurnType(): string;
  setTurnType(a: string): void;
  toObject(): InfraComponent.AsObject;
  serializeBinary(): Uint8Array;
  static deserializeBinary: (bytes: {}) => InfraComponent;
}

export namespace InfraComponent {
  export type AsObject = {
    X: number;
    Y: number;
    Char: string;
    TurnType: string;
  }
}

export class TrainPositionUpdate {
  constructor ();
  getTrainId(): string;
  setTrainId(a: string): void;
  getX(): number;
  setX(a: number): void;
  getY(): number;
  setY(a: number): void;
  getDirection(): Direction;
  setDirection(a: Direction): void;
  toObject(): TrainPositionUpdate.AsObject;
  serializeBinary(): Uint8Array;
  static deserializeBinary: (bytes: {}) => TrainPositionUpdate;
}

export namespace TrainPositionUpdate {
  export type AsObject = {
    TrainId: string;
    X: number;
    Y: number;
    Direction: Direction;
  }
}


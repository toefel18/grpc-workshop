/* eslint-disable */
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

export class InfraComponent {
  constructor ();
  getX(): number;
  setX(a: number): void;
  getY(): number;
  setY(a: number): void;
  getChar(): string;
  setChar(a: string): void;
  toObject(): InfraComponent.AsObject;
  serializeBinary(): Uint8Array;
  static deserializeBinary: (bytes: {}) => InfraComponent;
}

export namespace InfraComponent {
  export type AsObject = {
    X: number;
    Y: number;
    Char: string;
  }
}


/* eslint-disable */
/**
 * @fileoverview gRPC-Web generated client stub for nl.toefel.trains
 * @enhanceable
 * @public
 */

// GENERATED CODE -- DO NOT EDIT!

const grpc = {};
grpc.web = require("grpc-web");

const proto = {};
proto.nl = {};
proto.nl.toefel = {};
proto.nl.toefel.trains = require("./train_service_pb.js");

/**
 * @param {string} hostname
 * @param {?Object} credentials
 * @param {?Object} options
 * @constructor
 * @struct
 * @final
 */
proto.nl.toefel.trains.ProrailClient = function(
  hostname,
  credentials,
  options
) {
  if (!options) options = {};
  options["format"] = "text";

  /**
   * @private @const {!grpc.web.GrpcWebClientBase} The client
   */
  this.client_ = new grpc.web.GrpcWebClientBase(options);

  /**
   * @private @const {string} The hostname
   */
  this.hostname_ = hostname;

  /**
   * @private @const {?Object} The credentials to be used to connect
   *    to the server
   */
  this.credentials_ = credentials;

  /**
   * @private @const {?Object} Options for the client
   */
  this.options_ = options;
};

/**
 * @param {string} hostname
 * @param {?Object} credentials
 * @param {?Object} options
 * @constructor
 * @struct
 * @final
 */
proto.nl.toefel.trains.ProrailPromiseClient = function(
  hostname,
  credentials,
  options
) {
  if (!options) options = {};
  options["format"] = "text";

  /**
   * @private @const {!proto.nl.toefel.trains.ProrailClient} The delegate callback based client
   */
  this.delegateClient_ = new proto.nl.toefel.trains.ProrailClient(
    hostname,
    credentials,
    options
  );
};

/**
 * @const
 * @type {!grpc.web.AbstractClientBase.MethodInfo<
 *   !proto.nl.toefel.trains.GetTrackLayoutRequest,
 *   !proto.nl.toefel.trains.GetTrackLayoutResponse>}
 */
const methodInfo_Prorail_GetTrackLayout = new grpc.web.AbstractClientBase.MethodInfo(
  proto.nl.toefel.trains.GetTrackLayoutResponse,
  /** @param {!proto.nl.toefel.trains.GetTrackLayoutRequest} request */
  function(request) {
    return request.serializeBinary();
  },
  proto.nl.toefel.trains.GetTrackLayoutResponse.deserializeBinary
);

/**
 * @param {!proto.nl.toefel.trains.GetTrackLayoutRequest} request The
 *     request proto
 * @param {!Object<string, string>} metadata User defined
 *     call metadata
 * @param {function(?grpc.web.Error, ?proto.nl.toefel.trains.GetTrackLayoutResponse)}
 *     callback The callback function(error, response)
 * @return {!grpc.web.ClientReadableStream<!proto.nl.toefel.trains.GetTrackLayoutResponse>|undefined}
 *     The XHR Node Readable Stream
 */
proto.nl.toefel.trains.ProrailClient.prototype.getTrackLayout = function(
  request,
  metadata,
  callback
) {
  return this.client_.rpcCall(
    this.hostname_ + "/nl.toefel.trains.Prorail/GetTrackLayout",
    request,
    metadata,
    methodInfo_Prorail_GetTrackLayout,
    callback
  );
};

/**
 * @param {!proto.nl.toefel.trains.GetTrackLayoutRequest} request The
 *     request proto
 * @param {!Object<string, string>} metadata User defined
 *     call metadata
 * @return {!Promise<!proto.nl.toefel.trains.GetTrackLayoutResponse>}
 *     The XHR Node Readable Stream
 */
proto.nl.toefel.trains.ProrailPromiseClient.prototype.getTrackLayout = function(
  request,
  metadata
) {
  return new Promise((resolve, reject) => {
    this.delegateClient_.getTrackLayout(
      request,
      metadata,
      (error, response) => {
        error ? reject(error) : resolve(response);
      }
    );
  });
};

module.exports = proto.nl.toefel.trains;

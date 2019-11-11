// source: src/main/proto/Message.proto
/**
 * @fileoverview
 * @enhanceable
 * @suppress {messageConventions} JS Compiler reports an error if a variable or
 *     field starts with 'MSG_' and isn't a translatable message.
 * @public
 */
// GENERATED CODE -- DO NOT EDIT!

goog.provide('proto.MyMessage');
goog.provide('proto.MyMessage.DataType');
goog.provide('proto.MyMessage.DatabodyCase');

goog.require('jspb.BinaryReader');
goog.require('jspb.BinaryWriter');
goog.require('jspb.Message');
goog.require('proto.Game');
goog.require('proto.Room');

/**
 * Generated by JsPbCodeGenerator.
 * @param {Array=} opt_data Optional initial data array, typically from a
 * server response, or constructed directly in Javascript. The array is used
 * in place and becomes part of the constructed object. It is not cloned.
 * If no data is provided, the constructed object will be empty, but still
 * valid.
 * @extends {jspb.Message}
 * @constructor
 */
proto.MyMessage = function(opt_data) {
  jspb.Message.initialize(this, opt_data, 0, -1, null, proto.MyMessage.oneofGroups_);
};
goog.inherits(proto.MyMessage, jspb.Message);
if (goog.DEBUG && !COMPILED) {
  /**
   * @public
   * @override
   */
  proto.MyMessage.displayName = 'proto.MyMessage';
}

/**
 * Oneof group definitions for this message. Each group defines the field
 * numbers belonging to that group. When of these fields' value is set, all
 * other fields in the group are cleared. During deserialization, if multiple
 * fields are encountered for a group, only the last value seen will be kept.
 * @private {!Array<!Array<number>>}
 * @const
 */
proto.MyMessage.oneofGroups_ = [[2,3]];

/**
 * @enum {number}
 */
proto.MyMessage.DatabodyCase = {
  DATABODY_NOT_SET: 0,
  ROOM: 2,
  GAME: 3
};

/**
 * @return {proto.MyMessage.DatabodyCase}
 */
proto.MyMessage.prototype.getDatabodyCase = function() {
  return /** @type {proto.MyMessage.DatabodyCase} */(jspb.Message.computeOneofCase(this, proto.MyMessage.oneofGroups_[0]));
};



if (jspb.Message.GENERATE_TO_OBJECT) {
/**
 * Creates an object representation of this proto.
 * Field names that are reserved in JavaScript and will be renamed to pb_name.
 * Optional fields that are not set will be set to undefined.
 * To access a reserved field use, foo.pb_<name>, eg, foo.pb_default.
 * For the list of reserved names please see:
 *     net/proto2/compiler/js/internal/generator.cc#kKeyword.
 * @param {boolean=} opt_includeInstance Deprecated. whether to include the
 *     JSPB instance for transitional soy proto support:
 *     http://goto/soy-param-migration
 * @return {!Object}
 */
proto.MyMessage.prototype.toObject = function(opt_includeInstance) {
  return proto.MyMessage.toObject(opt_includeInstance, this);
};


/**
 * Static version of the {@see toObject} method.
 * @param {boolean|undefined} includeInstance Deprecated. Whether to include
 *     the JSPB instance for transitional soy proto support:
 *     http://goto/soy-param-migration
 * @param {!proto.MyMessage} msg The msg instance to transform.
 * @return {!Object}
 * @suppress {unusedLocalVariables} f is only used for nested messages
 */
proto.MyMessage.toObject = function(includeInstance, msg) {
  var f, obj = {
    dataType: jspb.Message.getFieldWithDefault(msg, 1, 0),
    room: (f = msg.getRoom()) && proto.Room.toObject(includeInstance, f),
    game: (f = msg.getGame()) && proto.Game.toObject(includeInstance, f)
  };

  if (includeInstance) {
    obj.$jspbMessageInstance = msg;
  }
  return obj;
};
}


/**
 * Deserializes binary data (in protobuf wire format).
 * @param {jspb.ByteSource} bytes The bytes to deserialize.
 * @return {!proto.MyMessage}
 */
proto.MyMessage.deserializeBinary = function(bytes) {
  var reader = new jspb.BinaryReader(bytes);
  var msg = new proto.MyMessage;
  return proto.MyMessage.deserializeBinaryFromReader(msg, reader);
};


/**
 * Deserializes binary data (in protobuf wire format) from the
 * given reader into the given message object.
 * @param {!proto.MyMessage} msg The message object to deserialize into.
 * @param {!jspb.BinaryReader} reader The BinaryReader to use.
 * @return {!proto.MyMessage}
 */
proto.MyMessage.deserializeBinaryFromReader = function(msg, reader) {
  while (reader.nextField()) {
    if (reader.isEndGroup()) {
      break;
    }
    var field = reader.getFieldNumber();
    switch (field) {
    case 1:
      var value = /** @type {!proto.MyMessage.DataType} */ (reader.readEnum());
      msg.setDataType(value);
      break;
    case 2:
      var value = new proto.Room;
      reader.readMessage(value,proto.Room.deserializeBinaryFromReader);
      msg.setRoom(value);
      break;
    case 3:
      var value = new proto.Game;
      reader.readMessage(value,proto.Game.deserializeBinaryFromReader);
      msg.setGame(value);
      break;
    default:
      reader.skipField();
      break;
    }
  }
  return msg;
};


/**
 * Serializes the message to binary data (in protobuf wire format).
 * @return {!Uint8Array}
 */
proto.MyMessage.prototype.serializeBinary = function() {
  var writer = new jspb.BinaryWriter();
  proto.MyMessage.serializeBinaryToWriter(this, writer);
  return writer.getResultBuffer();
};


/**
 * Serializes the given message to binary data (in protobuf wire
 * format), writing to the given BinaryWriter.
 * @param {!proto.MyMessage} message
 * @param {!jspb.BinaryWriter} writer
 * @suppress {unusedLocalVariables} f is only used for nested messages
 */
proto.MyMessage.serializeBinaryToWriter = function(message, writer) {
  var f = undefined;
  f = message.getDataType();
  if (f !== 0.0) {
    writer.writeEnum(
      1,
      f
    );
  }
  f = message.getRoom();
  if (f != null) {
    writer.writeMessage(
      2,
      f,
      proto.Room.serializeBinaryToWriter
    );
  }
  f = message.getGame();
  if (f != null) {
    writer.writeMessage(
      3,
      f,
      proto.Game.serializeBinaryToWriter
    );
  }
};


/**
 * @enum {number}
 */
proto.MyMessage.DataType = {
  ROOMTYPE: 0,
  GAMETYPE: 1
};

/**
 * optional DataType data_type = 1;
 * @return {!proto.MyMessage.DataType}
 */
proto.MyMessage.prototype.getDataType = function() {
  return /** @type {!proto.MyMessage.DataType} */ (jspb.Message.getFieldWithDefault(this, 1, 0));
};


/**
 * @param {!proto.MyMessage.DataType} value
 * @return {!proto.MyMessage} returns this
 */
proto.MyMessage.prototype.setDataType = function(value) {
  return jspb.Message.setProto3EnumField(this, 1, value);
};


/**
 * optional Room room = 2;
 * @return {?proto.Room}
 */
proto.MyMessage.prototype.getRoom = function() {
  return /** @type{?proto.Room} */ (
    jspb.Message.getWrapperField(this, proto.Room, 2));
};


/**
 * @param {?proto.Room|undefined} value
 * @return {!proto.MyMessage} returns this
*/
proto.MyMessage.prototype.setRoom = function(value) {
  return jspb.Message.setOneofWrapperField(this, 2, proto.MyMessage.oneofGroups_[0], value);
};


/**
 * Clears the message field making it undefined.
 * @return {!proto.MyMessage} returns this
 */
proto.MyMessage.prototype.clearRoom = function() {
  return this.setRoom(undefined);
};


/**
 * Returns whether this field is set.
 * @return {boolean}
 */
proto.MyMessage.prototype.hasRoom = function() {
  return jspb.Message.getField(this, 2) != null;
};


/**
 * optional Game game = 3;
 * @return {?proto.Game}
 */
proto.MyMessage.prototype.getGame = function() {
  return /** @type{?proto.Game} */ (
    jspb.Message.getWrapperField(this, proto.Game, 3));
};


/**
 * @param {?proto.Game|undefined} value
 * @return {!proto.MyMessage} returns this
*/
proto.MyMessage.prototype.setGame = function(value) {
  return jspb.Message.setOneofWrapperField(this, 3, proto.MyMessage.oneofGroups_[0], value);
};


/**
 * Clears the message field making it undefined.
 * @return {!proto.MyMessage} returns this
 */
proto.MyMessage.prototype.clearGame = function() {
  return this.setGame(undefined);
};


/**
 * Returns whether this field is set.
 * @return {boolean}
 */
proto.MyMessage.prototype.hasGame = function() {
  return jspb.Message.getField(this, 3) != null;
};



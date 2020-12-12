// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: steam.proto

package com.iscas.common.rpc.tools.grpc.stream;

/**
 * Protobuf type {@code com.iscas.common.rpc.tools.grpc.stream.StreamRequest}
 */
public  final class StreamRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:com.iscas.common.rpc.tools.grpc.stream.StreamRequest)
    StreamRequestOrBuilder {
  // Use StreamRequest.newBuilder() to construct.
  private StreamRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private StreamRequest() {
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private StreamRequest(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    int mutable_bitField0_ = 0;
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          default: {
            if (!input.skipField(tag)) {
              done = true;
            }
            break;
          }
          case 10: {
            com.iscas.common.rpc.tools.grpc.stream.StreamPoint.Builder subBuilder = null;
            if (pt_ != null) {
              subBuilder = pt_.toBuilder();
            }
            pt_ = input.readMessage(com.iscas.common.rpc.tools.grpc.stream.StreamPoint.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(pt_);
              pt_ = subBuilder.buildPartial();
            }

            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.iscas.common.rpc.tools.grpc.stream.StreamProto.internal_static_com_iscas_common_rpc_tools_grpc_stream_StreamRequest_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.iscas.common.rpc.tools.grpc.stream.StreamProto.internal_static_com_iscas_common_rpc_tools_grpc_stream_StreamRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.iscas.common.rpc.tools.grpc.stream.StreamRequest.class, com.iscas.common.rpc.tools.grpc.stream.StreamRequest.Builder.class);
  }

  public static final int PT_FIELD_NUMBER = 1;
  private com.iscas.common.rpc.tools.grpc.stream.StreamPoint pt_;
  /**
   * <code>.com.iscas.common.rpc.tools.grpc.stream.StreamPoint pt = 1;</code>
   */
  public boolean hasPt() {
    return pt_ != null;
  }
  /**
   * <code>.com.iscas.common.rpc.tools.grpc.stream.StreamPoint pt = 1;</code>
   */
  public com.iscas.common.rpc.tools.grpc.stream.StreamPoint getPt() {
    return pt_ == null ? com.iscas.common.rpc.tools.grpc.stream.StreamPoint.getDefaultInstance() : pt_;
  }
  /**
   * <code>.com.iscas.common.rpc.tools.grpc.stream.StreamPoint pt = 1;</code>
   */
  public com.iscas.common.rpc.tools.grpc.stream.StreamPointOrBuilder getPtOrBuilder() {
    return getPt();
  }

  private byte memoizedIsInitialized = -1;
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (pt_ != null) {
      output.writeMessage(1, getPt());
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (pt_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getPt());
    }
    memoizedSize = size;
    return size;
  }

  private static final long serialVersionUID = 0L;
  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof com.iscas.common.rpc.tools.grpc.stream.StreamRequest)) {
      return super.equals(obj);
    }
    com.iscas.common.rpc.tools.grpc.stream.StreamRequest other = (com.iscas.common.rpc.tools.grpc.stream.StreamRequest) obj;

    boolean result = true;
    result = result && (hasPt() == other.hasPt());
    if (hasPt()) {
      result = result && getPt()
          .equals(other.getPt());
    }
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    if (hasPt()) {
      hash = (37 * hash) + PT_FIELD_NUMBER;
      hash = (53 * hash) + getPt().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.iscas.common.rpc.tools.grpc.stream.StreamRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.iscas.common.rpc.tools.grpc.stream.StreamRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.iscas.common.rpc.tools.grpc.stream.StreamRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.iscas.common.rpc.tools.grpc.stream.StreamRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.iscas.common.rpc.tools.grpc.stream.StreamRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.iscas.common.rpc.tools.grpc.stream.StreamRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.iscas.common.rpc.tools.grpc.stream.StreamRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.iscas.common.rpc.tools.grpc.stream.StreamRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.iscas.common.rpc.tools.grpc.stream.StreamRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.iscas.common.rpc.tools.grpc.stream.StreamRequest parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.iscas.common.rpc.tools.grpc.stream.StreamRequest prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code com.iscas.common.rpc.tools.grpc.stream.StreamRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:com.iscas.common.rpc.tools.grpc.stream.StreamRequest)
      com.iscas.common.rpc.tools.grpc.stream.StreamRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.iscas.common.rpc.tools.grpc.stream.StreamProto.internal_static_com_iscas_common_rpc_tools_grpc_stream_StreamRequest_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.iscas.common.rpc.tools.grpc.stream.StreamProto.internal_static_com_iscas_common_rpc_tools_grpc_stream_StreamRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.iscas.common.rpc.tools.grpc.stream.StreamRequest.class, com.iscas.common.rpc.tools.grpc.stream.StreamRequest.Builder.class);
    }

    // Construct using com.iscas.common.rpc.tools.grpc.stream.StreamRequest.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    public Builder clear() {
      super.clear();
      if (ptBuilder_ == null) {
        pt_ = null;
      } else {
        pt_ = null;
        ptBuilder_ = null;
      }
      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.iscas.common.rpc.tools.grpc.stream.StreamProto.internal_static_com_iscas_common_rpc_tools_grpc_stream_StreamRequest_descriptor;
    }

    public com.iscas.common.rpc.tools.grpc.stream.StreamRequest getDefaultInstanceForType() {
      return com.iscas.common.rpc.tools.grpc.stream.StreamRequest.getDefaultInstance();
    }

    public com.iscas.common.rpc.tools.grpc.stream.StreamRequest build() {
      com.iscas.common.rpc.tools.grpc.stream.StreamRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public com.iscas.common.rpc.tools.grpc.stream.StreamRequest buildPartial() {
      com.iscas.common.rpc.tools.grpc.stream.StreamRequest result = new com.iscas.common.rpc.tools.grpc.stream.StreamRequest(this);
      if (ptBuilder_ == null) {
        result.pt_ = pt_;
      } else {
        result.pt_ = ptBuilder_.build();
      }
      onBuilt();
      return result;
    }

    public Builder clone() {
      return (Builder) super.clone();
    }
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.setField(field, value);
    }
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.iscas.common.rpc.tools.grpc.stream.StreamRequest) {
        return mergeFrom((com.iscas.common.rpc.tools.grpc.stream.StreamRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.iscas.common.rpc.tools.grpc.stream.StreamRequest other) {
      if (other == com.iscas.common.rpc.tools.grpc.stream.StreamRequest.getDefaultInstance()) return this;
      if (other.hasPt()) {
        mergePt(other.getPt());
      }
      onChanged();
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      com.iscas.common.rpc.tools.grpc.stream.StreamRequest parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.iscas.common.rpc.tools.grpc.stream.StreamRequest) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private com.iscas.common.rpc.tools.grpc.stream.StreamPoint pt_ = null;
    private com.google.protobuf.SingleFieldBuilderV3<
        com.iscas.common.rpc.tools.grpc.stream.StreamPoint, com.iscas.common.rpc.tools.grpc.stream.StreamPoint.Builder, com.iscas.common.rpc.tools.grpc.stream.StreamPointOrBuilder> ptBuilder_;
    /**
     * <code>.com.iscas.common.rpc.tools.grpc.stream.StreamPoint pt = 1;</code>
     */
    public boolean hasPt() {
      return ptBuilder_ != null || pt_ != null;
    }
    /**
     * <code>.com.iscas.common.rpc.tools.grpc.stream.StreamPoint pt = 1;</code>
     */
    public com.iscas.common.rpc.tools.grpc.stream.StreamPoint getPt() {
      if (ptBuilder_ == null) {
        return pt_ == null ? com.iscas.common.rpc.tools.grpc.stream.StreamPoint.getDefaultInstance() : pt_;
      } else {
        return ptBuilder_.getMessage();
      }
    }
    /**
     * <code>.com.iscas.common.rpc.tools.grpc.stream.StreamPoint pt = 1;</code>
     */
    public Builder setPt(com.iscas.common.rpc.tools.grpc.stream.StreamPoint value) {
      if (ptBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        pt_ = value;
        onChanged();
      } else {
        ptBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.com.iscas.common.rpc.tools.grpc.stream.StreamPoint pt = 1;</code>
     */
    public Builder setPt(
        com.iscas.common.rpc.tools.grpc.stream.StreamPoint.Builder builderForValue) {
      if (ptBuilder_ == null) {
        pt_ = builderForValue.build();
        onChanged();
      } else {
        ptBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.com.iscas.common.rpc.tools.grpc.stream.StreamPoint pt = 1;</code>
     */
    public Builder mergePt(com.iscas.common.rpc.tools.grpc.stream.StreamPoint value) {
      if (ptBuilder_ == null) {
        if (pt_ != null) {
          pt_ =
            com.iscas.common.rpc.tools.grpc.stream.StreamPoint.newBuilder(pt_).mergeFrom(value).buildPartial();
        } else {
          pt_ = value;
        }
        onChanged();
      } else {
        ptBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.com.iscas.common.rpc.tools.grpc.stream.StreamPoint pt = 1;</code>
     */
    public Builder clearPt() {
      if (ptBuilder_ == null) {
        pt_ = null;
        onChanged();
      } else {
        pt_ = null;
        ptBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.com.iscas.common.rpc.tools.grpc.stream.StreamPoint pt = 1;</code>
     */
    public com.iscas.common.rpc.tools.grpc.stream.StreamPoint.Builder getPtBuilder() {
      
      onChanged();
      return getPtFieldBuilder().getBuilder();
    }
    /**
     * <code>.com.iscas.common.rpc.tools.grpc.stream.StreamPoint pt = 1;</code>
     */
    public com.iscas.common.rpc.tools.grpc.stream.StreamPointOrBuilder getPtOrBuilder() {
      if (ptBuilder_ != null) {
        return ptBuilder_.getMessageOrBuilder();
      } else {
        return pt_ == null ?
            com.iscas.common.rpc.tools.grpc.stream.StreamPoint.getDefaultInstance() : pt_;
      }
    }
    /**
     * <code>.com.iscas.common.rpc.tools.grpc.stream.StreamPoint pt = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        com.iscas.common.rpc.tools.grpc.stream.StreamPoint, com.iscas.common.rpc.tools.grpc.stream.StreamPoint.Builder, com.iscas.common.rpc.tools.grpc.stream.StreamPointOrBuilder> 
        getPtFieldBuilder() {
      if (ptBuilder_ == null) {
        ptBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            com.iscas.common.rpc.tools.grpc.stream.StreamPoint, com.iscas.common.rpc.tools.grpc.stream.StreamPoint.Builder, com.iscas.common.rpc.tools.grpc.stream.StreamPointOrBuilder>(
                getPt(),
                getParentForChildren(),
                isClean());
        pt_ = null;
      }
      return ptBuilder_;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }


    // @@protoc_insertion_point(builder_scope:com.iscas.common.rpc.tools.grpc.stream.StreamRequest)
  }

  // @@protoc_insertion_point(class_scope:com.iscas.common.rpc.tools.grpc.stream.StreamRequest)
  private static final com.iscas.common.rpc.tools.grpc.stream.StreamRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.iscas.common.rpc.tools.grpc.stream.StreamRequest();
  }

  public static com.iscas.common.rpc.tools.grpc.stream.StreamRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<StreamRequest>
      PARSER = new com.google.protobuf.AbstractParser<StreamRequest>() {
    public StreamRequest parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new StreamRequest(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<StreamRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<StreamRequest> getParserForType() {
    return PARSER;
  }

  public com.iscas.common.rpc.tools.grpc.stream.StreamRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}


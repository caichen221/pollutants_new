package com.iscas.sso.mix.auth.service;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.4.0)",
    comments = "Source: auth.proto")
public final class AuthGrpc {

  private AuthGrpc() {}

  public static final String SERVICE_NAME = "service.Auth";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.iscas.sso.mix.auth.model.UserEntity.User,
      com.iscas.sso.mix.auth.model.ResponseEntity.ResEntity> METHOD_LOGIN =
      io.grpc.MethodDescriptor.<com.iscas.sso.mix.auth.model.UserEntity.User, com.iscas.sso.mix.auth.model.ResponseEntity.ResEntity>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "service.Auth", "login"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.iscas.sso.mix.auth.model.UserEntity.User.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.iscas.sso.mix.auth.model.ResponseEntity.ResEntity.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.iscas.sso.mix.auth.model.UserEntity.User,
      com.iscas.sso.mix.auth.model.ResponseEntity.ResEntity> METHOD_VERIFY =
      io.grpc.MethodDescriptor.<com.iscas.sso.mix.auth.model.UserEntity.User, com.iscas.sso.mix.auth.model.ResponseEntity.ResEntity>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "service.Auth", "verify"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.iscas.sso.mix.auth.model.UserEntity.User.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.iscas.sso.mix.auth.model.ResponseEntity.ResEntity.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AuthStub newStub(io.grpc.Channel channel) {
    return new AuthStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AuthBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new AuthBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AuthFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new AuthFutureStub(channel);
  }

  /**
   */
  public static abstract class AuthImplBase implements io.grpc.BindableService {

    /**
     */
    public void login(com.iscas.sso.mix.auth.model.UserEntity.User request,
        io.grpc.stub.StreamObserver<com.iscas.sso.mix.auth.model.ResponseEntity.ResEntity> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_LOGIN, responseObserver);
    }

    /**
     */
    public void verify(com.iscas.sso.mix.auth.model.UserEntity.User request,
        io.grpc.stub.StreamObserver<com.iscas.sso.mix.auth.model.ResponseEntity.ResEntity> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_VERIFY, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_LOGIN,
            asyncUnaryCall(
              new MethodHandlers<
                com.iscas.sso.mix.auth.model.UserEntity.User,
                com.iscas.sso.mix.auth.model.ResponseEntity.ResEntity>(
                  this, METHODID_LOGIN)))
          .addMethod(
            METHOD_VERIFY,
            asyncUnaryCall(
              new MethodHandlers<
                com.iscas.sso.mix.auth.model.UserEntity.User,
                com.iscas.sso.mix.auth.model.ResponseEntity.ResEntity>(
                  this, METHODID_VERIFY)))
          .build();
    }
  }

  /**
   */
  public static final class AuthStub extends io.grpc.stub.AbstractStub<AuthStub> {
    private AuthStub(io.grpc.Channel channel) {
      super(channel);
    }

    private AuthStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AuthStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new AuthStub(channel, callOptions);
    }

    /**
     */
    public void login(com.iscas.sso.mix.auth.model.UserEntity.User request,
        io.grpc.stub.StreamObserver<com.iscas.sso.mix.auth.model.ResponseEntity.ResEntity> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_LOGIN, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void verify(com.iscas.sso.mix.auth.model.UserEntity.User request,
        io.grpc.stub.StreamObserver<com.iscas.sso.mix.auth.model.ResponseEntity.ResEntity> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_VERIFY, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class AuthBlockingStub extends io.grpc.stub.AbstractStub<AuthBlockingStub> {
    private AuthBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private AuthBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AuthBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new AuthBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.iscas.sso.mix.auth.model.ResponseEntity.ResEntity login(com.iscas.sso.mix.auth.model.UserEntity.User request) {
      return blockingUnaryCall(
          getChannel(), METHOD_LOGIN, getCallOptions(), request);
    }

    /**
     */
    public com.iscas.sso.mix.auth.model.ResponseEntity.ResEntity verify(com.iscas.sso.mix.auth.model.UserEntity.User request) {
      return blockingUnaryCall(
          getChannel(), METHOD_VERIFY, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class AuthFutureStub extends io.grpc.stub.AbstractStub<AuthFutureStub> {
    private AuthFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private AuthFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AuthFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new AuthFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.iscas.sso.mix.auth.model.ResponseEntity.ResEntity> login(
        com.iscas.sso.mix.auth.model.UserEntity.User request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_LOGIN, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.iscas.sso.mix.auth.model.ResponseEntity.ResEntity> verify(
        com.iscas.sso.mix.auth.model.UserEntity.User request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_VERIFY, getCallOptions()), request);
    }
  }

  private static final int METHODID_LOGIN = 0;
  private static final int METHODID_VERIFY = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AuthImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(AuthImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_LOGIN:
          serviceImpl.login((com.iscas.sso.mix.auth.model.UserEntity.User) request,
              (io.grpc.stub.StreamObserver<com.iscas.sso.mix.auth.model.ResponseEntity.ResEntity>) responseObserver);
          break;
        case METHODID_VERIFY:
          serviceImpl.verify((com.iscas.sso.mix.auth.model.UserEntity.User) request,
              (io.grpc.stub.StreamObserver<com.iscas.sso.mix.auth.model.ResponseEntity.ResEntity>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class AuthDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.iscas.sso.mix.auth.service.AuthService.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (AuthGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AuthDescriptorSupplier())
              .addMethod(METHOD_LOGIN)
              .addMethod(METHOD_VERIFY)
              .build();
        }
      }
    }
    return result;
  }
}

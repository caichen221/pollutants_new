package com.iscas.msi.api;

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
    comments = "Source: hello.proto")
public final class HelloWorldGrpc {

  private HelloWorldGrpc() {}

  public static final String SERVICE_NAME = "com.iscas.msi.api.HelloWorld";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.iscas.msi.api.HelloWorldService.HelloRequest,
      com.iscas.msi.api.HelloWorldService.HelloResponse> METHOD_SAY_HELLO =
      io.grpc.MethodDescriptor.<com.iscas.msi.api.HelloWorldService.HelloRequest, com.iscas.msi.api.HelloWorldService.HelloResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "com.iscas.msi.api.HelloWorld", "SayHello"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.iscas.msi.api.HelloWorldService.HelloRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.iscas.msi.api.HelloWorldService.HelloResponse.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static HelloWorldStub newStub(io.grpc.Channel channel) {
    return new HelloWorldStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static HelloWorldBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new HelloWorldBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static HelloWorldFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new HelloWorldFutureStub(channel);
  }

  /**
   */
  public static abstract class HelloWorldImplBase implements io.grpc.BindableService {

    /**
     */
    public void sayHello(com.iscas.msi.api.HelloWorldService.HelloRequest request,
        io.grpc.stub.StreamObserver<com.iscas.msi.api.HelloWorldService.HelloResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SAY_HELLO, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_SAY_HELLO,
            asyncUnaryCall(
              new MethodHandlers<
                com.iscas.msi.api.HelloWorldService.HelloRequest,
                com.iscas.msi.api.HelloWorldService.HelloResponse>(
                  this, METHODID_SAY_HELLO)))
          .build();
    }
  }

  /**
   */
  public static final class HelloWorldStub extends io.grpc.stub.AbstractStub<HelloWorldStub> {
    private HelloWorldStub(io.grpc.Channel channel) {
      super(channel);
    }

    private HelloWorldStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HelloWorldStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new HelloWorldStub(channel, callOptions);
    }

    /**
     */
    public void sayHello(com.iscas.msi.api.HelloWorldService.HelloRequest request,
        io.grpc.stub.StreamObserver<com.iscas.msi.api.HelloWorldService.HelloResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SAY_HELLO, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class HelloWorldBlockingStub extends io.grpc.stub.AbstractStub<HelloWorldBlockingStub> {
    private HelloWorldBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private HelloWorldBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HelloWorldBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new HelloWorldBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.iscas.msi.api.HelloWorldService.HelloResponse sayHello(com.iscas.msi.api.HelloWorldService.HelloRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SAY_HELLO, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class HelloWorldFutureStub extends io.grpc.stub.AbstractStub<HelloWorldFutureStub> {
    private HelloWorldFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private HelloWorldFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HelloWorldFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new HelloWorldFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.iscas.msi.api.HelloWorldService.HelloResponse> sayHello(
        com.iscas.msi.api.HelloWorldService.HelloRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SAY_HELLO, getCallOptions()), request);
    }
  }

  private static final int METHODID_SAY_HELLO = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final HelloWorldImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(HelloWorldImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SAY_HELLO:
          serviceImpl.sayHello((com.iscas.msi.api.HelloWorldService.HelloRequest) request,
              (io.grpc.stub.StreamObserver<com.iscas.msi.api.HelloWorldService.HelloResponse>) responseObserver);
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

  private static final class HelloWorldDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.iscas.msi.api.HelloWorldService.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (HelloWorldGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new HelloWorldDescriptorSupplier())
              .addMethod(METHOD_SAY_HELLO)
              .build();
        }
      }
    }
    return result;
  }
}

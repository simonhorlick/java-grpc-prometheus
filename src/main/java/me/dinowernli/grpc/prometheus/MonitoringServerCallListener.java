// Copyright 2016 Dino Wernli. All Rights Reserved. See LICENSE for licensing terms.

package me.dinowernli.grpc.prometheus;

import io.grpc.ForwardingServerCallListener;
import io.grpc.ServerCall;
import io.grpc.ServerCall.Listener;
import io.grpc.Status;

/**
 * A {@link ForwardingServerCallListener} which updates Prometheus metrics for a single rpc based
 * on updates received from grpc.
 */
class MonitoringServerCallListener<R, S> extends ForwardingServerCallListener<R> {
  private final ServerCall.Listener<R> delegate;
  private final GrpcMethod grpcMethod;
  private final MonitoringServerCall<R, S> monitoringCall;
  private final ServerMetrics serverMetrics;

  MonitoringServerCallListener(
      Listener<R> delegate,
      MonitoringServerCall<R, S> monitoringCall,
      ServerMetrics serverMetrics, GrpcMethod grpcMethod) {
    this.delegate = delegate;
    this.monitoringCall = monitoringCall;
    this.serverMetrics = serverMetrics;
    this.grpcMethod = grpcMethod;
  }

  @Override
  protected ServerCall.Listener<R> delegate() {
    return delegate;
  }

  @Override
  public void onMessage(R request) {
    if (grpcMethod.streamsRequests()) {
      serverMetrics.recordStreamMessageReceived();
    }
    super.onMessage(request);
  }

  @Override public void onHalfClose() {
    try {
      super.onHalfClose();
    } catch (RuntimeException ex) {
      handleException(ex);
      throw ex;
    }
  }

  @Override public void onReady() {
    try {
      super.onReady();
    } catch (RuntimeException ex) {
      handleException(ex);
      throw ex;
    }
  }

  private void handleException(RuntimeException ex) {
    monitoringCall.reportEndMetrics(Status.fromThrowable(ex));
  }

}
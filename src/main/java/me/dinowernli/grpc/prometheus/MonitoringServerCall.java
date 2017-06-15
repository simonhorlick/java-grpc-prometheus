// Copyright 2016 Dino Wernli. All Rights Reserved. See LICENSE for licensing terms.

package me.dinowernli.grpc.prometheus;

import com.google.common.base.Stopwatch;
import io.grpc.ForwardingServerCall;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.Status;
import java.util.concurrent.TimeUnit;

/**
 * A {@link ForwardingServerCall} which updates Prometheus metrics based on the server-side actions
 * taken for a single rpc, e.g., messages sent, latency, etc.
 */
class MonitoringServerCall<R,S> extends ForwardingServerCall.SimpleForwardingServerCall<R,S> {
  private static final long MICROS_PER_SECOND = 1000_000L;

  private final Stopwatch stopwatch;
  private final GrpcMethod grpcMethod;
  private final ServerMetrics serverMetrics;
  private final Configuration configuration;

  MonitoringServerCall(
      ServerCall<R,S> delegate,
      Stopwatch stopwatch,
      GrpcMethod grpcMethod,
      ServerMetrics serverMetrics,
      Configuration configuration) {
    super(delegate);
    this.stopwatch = stopwatch;
    this.grpcMethod = grpcMethod;
    this.serverMetrics = serverMetrics;
    this.configuration = configuration;

    // TODO(dino): Consider doing this in the onReady() method of the listener instead.
    reportStartMetrics();
  }

  @Override
  public void close(Status status, Metadata responseHeaders) {
    reportEndMetrics(status);
    super.close(status, responseHeaders);
  }

  @Override
  public void sendMessage(S message) {
    if (grpcMethod.streamsResponses()) {
      serverMetrics.recordStreamMessageSent();
    }
    super.sendMessage(message);
  }

  private void reportStartMetrics() {
    serverMetrics.recordCallStarted();
  }

  void reportEndMetrics(Status status) {
    serverMetrics.recordServerHandled(status.getCode());
    if (configuration.isIncludeLatencyHistograms()) {
      double latencySec =
          (stopwatch.elapsed(TimeUnit.MICROSECONDS)) / (double) MICROS_PER_SECOND;
      serverMetrics.recordLatency(latencySec);
    }
  }
}
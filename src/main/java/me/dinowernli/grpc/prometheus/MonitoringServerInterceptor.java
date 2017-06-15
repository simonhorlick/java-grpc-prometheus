// Copyright 2016 Dino Wernli. All Rights Reserved. See LICENSE for licensing terms.

package me.dinowernli.grpc.prometheus;

import com.google.common.base.Stopwatch;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.ServerCall;
import io.grpc.ServerCall.Listener;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import java.time.Clock;

/** A {@link ServerInterceptor} which sends stats about incoming grpc calls to Prometheus. */
public class MonitoringServerInterceptor implements ServerInterceptor {
  private final Clock clock;
  private final Configuration configuration;
  private final ServerMetrics.Factory serverMetricsFactory;

  public static MonitoringServerInterceptor create(Configuration configuration) {
    return new MonitoringServerInterceptor(
        Clock.systemDefaultZone(), configuration, new ServerMetrics.Factory(configuration));
  }

  private MonitoringServerInterceptor(
      Clock clock, Configuration configuration, ServerMetrics.Factory serverMetricsFactory) {
    this.clock = clock;
    this.configuration = configuration;
    this.serverMetricsFactory = serverMetricsFactory;
  }

  @Override
  public <R, S> ServerCall.Listener<R> interceptCall(
      ServerCall<R, S> call,
      Metadata requestHeaders,
      ServerCallHandler<R, S> next) {
    MethodDescriptor<R, S> method = call.getMethodDescriptor();
    ServerMetrics metrics = serverMetricsFactory.createMetricsForMethod(method);
    GrpcMethod grpcMethod = GrpcMethod.of(method);

    MonitoringServerCall<R,S> monitoringCall = new MonitoringServerCall<>(call, Stopwatch.createStarted(), grpcMethod, metrics, configuration);

    Listener<R> listener = next.startCall(monitoringCall, requestHeaders);

    return new MonitoringServerCallListener<>(listener, monitoringCall, metrics, GrpcMethod.of(method));
  }

}

package(default_visibility = ["//visibility:public"])

java_library(
    name = "prometheus",
    srcs = glob(["*.java"]),
    deps = [
        "@io_grpc_grpc_java//core",
        "@io_grpc_grpc_java//stub",
        "@io_prometheus_simpleclient//jar",
        "@com_google_guava//jar",
    ],
)

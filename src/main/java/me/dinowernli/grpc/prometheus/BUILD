package(default_visibility = ["//visibility:public"])

java_library(
    name = "prometheus",
    srcs = glob(["*.java"]),
    deps = [
        "@grpc_java//core",
        "@grpc_java//stub",
        "@io_prometheus_simpleclient//jar",
        "@com_google_guava//jar",
    ],
)

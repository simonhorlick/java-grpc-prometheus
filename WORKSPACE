workspace(name = "me_dinowernli_java_grpc_prometheus")

# This is currently forked to fix support for protobuf java lite on Android.
GRPC_JAVA_COMMIT = "61f2528ccd839199de2a750bad407b188a6fe68d"

http_archive(
    name = "io_grpc_grpc_java",
    sha256 = "71a32201ac6e1315c0883a84fb17d41159e4d78a74c8f38c1226931909caa5f6",
    strip_prefix = "grpc-java-" + GRPC_JAVA_COMMIT,
    urls = ["https://github.com/grpc/grpc-java/archive/" + GRPC_JAVA_COMMIT + ".tar.gz"],
)

load("@io_grpc_grpc_java//:repositories.bzl", "grpc_java_repositories")

grpc_java_repositories(omit_com_google_guava=True)

maven_jar(
    name = "com_google_guava",
    artifact = "com.google.guava:guava:20.0",
    sha1 = "89507701249388e1ed5ddcf8c41f4ce1be7831ef",
)

maven_jar(
    name = "junit_artifact",
    artifact = "junit:junit:4.10",
)

maven_jar(
    name = "mockito_artifact",
    artifact = "org.mockito:mockito-all:1.10.19",
)

maven_jar(
    name = "io_prometheus_simpleclient",
    artifact = "io.prometheus:simpleclient:0.0.19",
    sha1 = "c1424b444a7ec61e056a180d52470ff397bc428d",
)

maven_jar(
    name = "truth_artifact",
    artifact = "com.google.truth:truth:0.28",
)

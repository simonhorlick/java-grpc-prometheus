workspace(name = "me_dinowernli_java_grpc_prometheus")

http_archive(
    name = "grpc_java",
    sha256 = "956c7258c53f45824e3ce6d7e7e2211dc40640c1ba685f7c895c174fbd9106ba",
    strip_prefix = "grpc-java-5ea8323164f7dae9ca972324d6e42344a1d04035",
    urls = ["https://github.com/simonhorlick/grpc-java/archive/5ea8323164f7dae9ca972324d6e42344a1d04035.tar.gz"],
)

load("@grpc_java//:repositories.bzl", "grpc_java_repositories")

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

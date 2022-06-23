FROM bitnami/kafka:3.1.0-debian-10-r89

USER root
COPY distributed-sink-connector/connect-distributed.properties /opt/bitnami/kafka/config/
COPY _base/camel-aws2-kinesis-kafka-connector /opt/bitnami/kafka/plugins/camel-aws2-kinesis-kafka-connector
ENV CLASSPATH=/opt/bitnami/kafka/plugins/*.jar:${CLASSPATH}


FROM bitnami/kafka:3.1.0-debian-10-r89

USER root

COPY distributed-sink-connector/connect-distributed.properties /opt/bitnami/kafka/config/

# kinesis connector binaries
COPY _base/camel-aws2-kinesis-kafka-connector /libs/camel-aws2-kinesis-kafka-connector

# we replace the start command creating a connector instead.
COPY distributed-sink-connector/start-kafka.sh /usr/bin/


# permissions
RUN chmod a+x /usr/bin/start-kafka.sh

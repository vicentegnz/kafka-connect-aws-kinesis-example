FROM wurstmeister/kafka:latest

# we replace the default connect-standalone.properties so we can properly resolve to our local kafka docker development
COPY connect-standalone.properties /opt/kafka/config/
COPY connect-kinesis-sink.properties /opt/kafka/config/

# kinesis connector binaries
COPY amazon-kinesis-kafka-connector-0.0.9-DEMO-CUSTOM.jar /libs/
ENV CLASSPATH=/libs/amazon-kinesis-kafka-connector-0.0.9-DEMO-CUSTOM.jar:${CLASSPATH}

ARG ACCESS_KEY_ID
ARG SECRET_ACCESS_KEY

ENV AWS_ACCESS_KEY_ID=${ACCESS_KEY_ID}
ENV AWS_SECRET_ACCESS_KEY=${SECRET_ACCESS_KEY}

# we replace the start command creating a connector instead.
COPY start-kafka.sh /usr/bin/

# permissions
RUN chmod a+x /usr/bin/start-kafka.sh

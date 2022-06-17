FROM wurstmeister/kafka:latest

# we replace the default connect-standalone.properties so we can properly resolve to our local kafka docker development
COPY connect-standalone.properties /opt/kafka/config/
COPY connect-kinesis-sink.properties /opt/kafka/config/

# kinesis connector binaries
COPY amazon-kinesis-kafka-connector-0.0.9.jar /libs/
ENV CLASSPATH=/libs/amazon-kinesis-kafka-connector-0.0.9.jar:${CLASSPATH}

RUN mkdir -p ~/.aws/
RUN echo "[default]\naws_access_key_id = \naws_secret_access_key = " > ~/.aws/credentials
RUN echo "[default]\nregion = eu-west-1\noutput = json" > ~/.aws/config

# we replace the start command creating a connector instead.
COPY start-kafka.sh /usr/bin/

# permissions
RUN chmod a+x /usr/bin/start-kafka.sh

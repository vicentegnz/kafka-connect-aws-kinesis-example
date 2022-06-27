# About

Example repository using docker compose to pull and push events from AWS Kinesis services to Kafka.

## How to running

1. Build the docker images `docker-compose build`, which will build the environment with the following components.
  
   - Zookeeper
   - Kafka
   - Kafka UI
   - Kafka REST proxy
   - Kafka init
   - Kafka sink connector
   - Kafka source connector
   - Kinesis
   - Kinesis stream consumer
   - Kinesis stream producer

2. Run the docker containers, it will run Zookeeper, Kafka and the Connectors in
   the background `docker-compose up`.

3. Query the Connect REST API of the distributed connector to verify it is
  running using the Postman collection in the connectors section.

4. Execute POST requests for both Sink and Source connectors using the postman
   collection. If all goes well, you should see an output similar to below.

```yaml
{
  "name": "kinesis-sink-connector",
  "config": {
    "name": "kinesis-sink-connector",
    ......
  },
  "tasks": [
    {
      "connector": "kinesis-sink-connector",
      "task": 0
    }
  ],
  "type": "sink"
}
```

```yaml
{
  "name": "kinesis-source-connector",
  "config": {
    "name": "kinesis-source-connector",
    ......
  },
  "tasks": [
    {
      "connector": "kinesis-source-connector",
      "task": 0
    }
  ],
  "type": "source"
}
```

If all goes well, in http://localhost:5223/ you should see two topics `external.fake.company.event.payment` and `gateway.event.payment`.

- `external.fake.company.event.payment`: is used to receive the events from Kinesis that
  the kinesis producer service is sending each 10 seconds.
- `gateway.event.payment`: is used to receive the events that you send via Postman
  collection on the produce events section, all of those events should be listed
  on kinesis consumer.

## References

- [How to use Kafka Connect](https://docs.confluent.io/platform/current/connect/userguide.html)
- [Kafka Connect deep dive (Serialization)](https://www.confluent.io/es-es/blog/kafka-connect-deep-dive-converters-serialization-explained/)
- [Apache Kafka Connect docs](https://kafka.apache.org/documentation/#connect)
- [Camel Kafka connectors](https://camel.apache.org/camel-kafka-connector/0.11.x/reference/index.html)
- [Camel Apache sink Kinesis connector configuration](https://camel.apache.org/camel-kafka-connector/0.11.x/reference/index.html)
- [Camel Apache source Kinesis connector configuration](https://camel.apache.org/camel-kafka-connector/0.11.x/reference/connectors/camel-aws2-kinesis-kafka-source-connector.html)
- [Camel Apache common connector configuration](https://camel.apache.org/camel-kafka-connector/0.11.x/user-guide/basic-configuration.html)
  
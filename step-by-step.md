# Quick step by step

- Build the docker images as the connect image is required to build to include
connector standalone configurations, we could have used a map to provide the
files but building it for now.

`docker-compose build`

- Run the docker containers, it will run Zookeeper, Kafka and the Connectors in background.

`docker-compose up`

- Check the docker container logs attaching a terminal window to tail/follow them.

`docker-compose logs -f`

- When running the docker-compose in some environments the folder where we will
create the file from where the connector is reading might be created as root
user, this is due to how docker-compose and docker are setup and images are
built, change the folder permissions to your current user, in a terminal if
necessary:

`sudo chown $USER: input/connect-input-file`

- Run in a new terminal window a command line client attached to the connect
destination topic and wait for messages for validation as this is a source
connector.

`docker exec -it kafka /opt/kafka/bin/kafka-console-consumer.sh --bootstrap-server kafka:9092 --topic simple-connect --from-beginning`

- A sample data file should already exist in `input/connect-input-file` but
  if it isn't there, create a folder connect-input-file in the standalone
  directory where inside the connector expects to find the file where it will
  read lines from and publish to kafka, enter this directory and create a file
  named `my-source-file.txt`.

`cd connect-input-file && touch my-source-file.txt`

- Open the file with your preferred text editor and add lines to it and notice
that the lines are automatically read by the connector and published to the
kafka topic where we have attached our console client every time you save the
file, it relies in a new line / blank line at the end of the file, so make sure
to add it in order for it to work properly.

- Retrieve the ports used by one of the distributed connector instances

`docker ps`

    CONTAINER ID        IMAGE                                COMMAND                  CREATED             STATUS              PORTS                                                NAMES
    cd7c061d9ef2        docker-compose_connect-distributed   "start-kafka.sh"         35 seconds ago      Up 34 seconds       0.0.0.0:32776->8083/tcp                              docker-compose_connect-distributed_3
    c4eb751169be        docker-compose_connect-distributed   "start-kafka.sh"         35 seconds ago      Up 34 seconds       0.0.0.0:32775->8083/tcp                              docker-compose_connect-distributed_1
    aa62908512ff        docker-compose_connect-standalone    "start-kafka.sh"         35 seconds ago      Up 34 seconds       0.0.0.0:8083->8083/tcp                               connect-standalone
    7722da0e7e48        docker-compose_connect-distributed   "start-kafka.sh"         35 seconds ago      Up 34 seconds       0.0.0.0:32774->8083/tcp                              docker-compose_connect-distributed_2

- Query the Connect REST API of the distributed connector to verify it is
  running

`curl http://localhost:32774/connectors`

- Convert `output/connect-file-sink.properties` to JSON format. I've
  provided a version of this as `connect-file-sink.json`.

- Push this JSON file content to the REST API

`curl -XPUT -H "Content-Type: application/json"  --data "@connect-file-sink.json" http://localhost:8084/connectors/file-sink-connector/config | jq`

- If all goes well, you should see an output similar to below (thanks to `jq`)

```yaml
{
  "name": "file-sink-connector",
  "config": {
    "name": "file-sink-connector",
    "connector.class": "org.apache.kafka.connect.file.FileStreamSinkConnector",
    "tasks.max": "1",
    "topics": "simple-connect",
    "file": "/tmp/my-output-file.txt",
    "key.converter": "org.apache.kafka.connect.storage.StringConverter",
    "value.converter": "org.apache.kafka.connect.storage.StringConverter"
  },
  "tasks": [
    {
      "connector": "file-sink-connector",
      "task": 0
    }
  ],
  "type": "sink"
}
```

- If data exists on the topic, then you should see data output to
  `distributed/connect-output-file/my-output-file.txt`.

- Furthermore, you can check the consumer groups via `kafka-consumer-groups`

`docker exec -it kafka /opt/kafka/bin/kafka-consumer-groups.sh --bootstrap-server kafka:9092 --describe --all-groups`

    GROUP                       TOPIC           PARTITION  CURRENT-OFFSET  LOG-END-OFFSET  LAG             CONSUMER-ID                                                                   HOST            CLIENT-ID
    connect-file-sink-connector simple-connect  1          4667            4667            0               connector-consumer-file-sink-connector-1-f009d64b-b2ad-42e9-be14-a77b9acfc6c0 /172.23.0.6     connector-consumer-file-sink-connector-1
    connect-file-sink-connector simple-connect  0          4666            4666            0               connector-consumer-file-sink-connector-0-e413eb56-30ec-4a3b-89fc-bf4b2aea01a9 /172.23.0.5     connector-consumer-file-sink-connector-0
    connect-file-sink-connector simple-connect  2          4667            4667            0               connector-consumer-file-sink-connector-2-c34e154b-8efb-4b41-aea0-a133a5f8556c /172.23.0.7     connector-consumer-file-sink-connector-2
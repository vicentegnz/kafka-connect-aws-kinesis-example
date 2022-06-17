$JSON = @{
"connector.class"="org.apache.kafka.connect.file.FileStreamSinkConnector"
"name"="file-sink-connector"
"file"= "/tmp/my-output-file.txt"
"value.converter"= "org.apache.kafka.connect.storage.StringConverter"
"key.converter"= "org.apache.kafka.connect.storage.StringConverter"
"tasks.max"= "3"
"topics"= "simple-connect"
}

Invoke-WebRequest -Uri http://localhost:8084/connectors/file-sink-connector/config -Method PUt -Body ($JSON|ConvertTo-Json) -ContentType "application/json"
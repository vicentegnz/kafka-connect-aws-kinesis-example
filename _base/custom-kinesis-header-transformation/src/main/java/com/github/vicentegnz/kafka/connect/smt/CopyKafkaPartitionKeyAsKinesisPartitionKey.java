package com.github.vicentegnz.kafka.connect.smt;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.ConnectRecord;
import org.apache.kafka.connect.data.ConnectSchema;
import org.apache.kafka.connect.header.Headers;
import org.apache.kafka.connect.transforms.Transformation;

import java.util.Map;

public class CopyKafkaPartitionKeyAsKinesisPartitionKey<R extends ConnectRecord<R>> implements Transformation<R> {

  public static final ConfigDef CONFIG_DEF = new ConfigDef();

  @Override
  public void configure(Map<String, ?> props) {
  }

  @Override
  public R apply(R record) {
    String headerValue = record.key().toString();
    Headers newHeaders = record.headers().duplicate().add("CamelHeader.CamelAwsKinesisPartitionKey", headerValue, ConnectSchema.STRING_SCHEMA);

    return record.newRecord(record.topic(), record.kafkaPartition(), record.keySchema(),
        record.key(), record.valueSchema(), record.value(), record.timestamp(), newHeaders);
  }

  @Override
  public ConfigDef config() {
    return CONFIG_DEF;
  }

  @Override
  public void close() {
  }
}



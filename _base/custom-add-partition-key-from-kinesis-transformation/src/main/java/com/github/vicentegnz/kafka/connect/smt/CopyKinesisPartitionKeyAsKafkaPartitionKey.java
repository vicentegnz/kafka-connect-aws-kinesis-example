package com.github.vicentegnz.kafka.connect.smt;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.ConnectRecord;
import org.apache.kafka.connect.data.ConnectSchema;
import org.apache.kafka.connect.header.Headers;
import org.apache.kafka.connect.transforms.Transformation;

import java.util.Map;

public class CopyKinesisPartitionKeyAsKafkaPartitionKey<R extends ConnectRecord<R>> implements Transformation<R> {

  public static final ConfigDef CONFIG_DEF = new ConfigDef();

  @Override
  public void configure(Map<String, ?> props) {
  }

  @Override
  public R apply(R record) {
    org.apache.kafka.connect.header.Header kinesisPartitionKey = record.headers().lastWithName("CamelHeader.CamelAwsKinesisPartitionKey");
    
    if (kinesisPartitionKey == null) {
      return record;
    }

    return record.newRecord(record.topic(), record.kafkaPartition(), record.keySchema(),
        kinesisPartitionKey.value(), record.valueSchema(), record.value(), record.timestamp());
  }

  @Override
  public ConfigDef config() {
    return CONFIG_DEF;
  }

  @Override
  public void close() {
  }
}



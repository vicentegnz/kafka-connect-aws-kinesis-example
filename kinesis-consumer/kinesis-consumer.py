import boto3
import json
import time

print('Starting..')
session = boto3.Session(region_name='eu-west-1')
client = session.client(
    'kinesis',
    aws_access_key_id='FakeKey',
    aws_secret_access_key='FakeKey',
    endpoint_url='http://kinesis:4566'
)

stream_details = client.describe_stream(StreamName='simple-connect-sink-stream')
shard_id = stream_details['StreamDescription']['Shards'][0]['ShardId']

response = client.get_shard_iterator(
    StreamName='simple-connect-sink-stream',
    ShardId=shard_id,
    ShardIteratorType='LATEST'
)

shard_iterator = response['ShardIterator']


print("Listening stream data.")
while True:
    response = client.get_records(ShardIterator=shard_iterator, Limit=5)
    shard_iterator = response['NextShardIterator']
    for record in response['Records']:
        if 'Data' in record and len(record['Data']) > 0:
            print('\nEvent received from KAFKA:')
            print(json.loads(record['Data']))
    time.sleep(0.75)

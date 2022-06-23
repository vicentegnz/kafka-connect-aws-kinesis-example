import boto3
import json
import time
import uuid

print('Starting..')
session = boto3.Session(region_name='eu-west-1')
client = session.client(
    'kinesis',
    aws_access_key_id='FakeKey',
    aws_secret_access_key='FakeKey',
    endpoint_url='http://kinesis:4566'
)

print("Pushing data to Kinesis stream.")
while True:
    print('\nPushing new record:')
    test_record = {'this': 'is', 'a': 'test', 'id': str(uuid.uuid4())}
    client.put_record(StreamName='simple-source-connect-stream', Data=json.dumps(test_record), PartitionKey=str(uuid.uuid4()))
    print(test_record)
    time.sleep(10)


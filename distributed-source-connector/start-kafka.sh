#!/bin/bash -e

# connector start command here.
exec "/opt/bitnami/kafka/bin/connect-distributed.sh" "/opt/bitnami/kafka/config/connect-distributed.properties"
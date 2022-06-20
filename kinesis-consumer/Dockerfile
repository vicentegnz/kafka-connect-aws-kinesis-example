FROM python:3.9-alpine

ADD . /code

WORKDIR /code

RUN pip install -r requeriments.txt

CMD ["python", "-u", "kinesis-consumer.py"]
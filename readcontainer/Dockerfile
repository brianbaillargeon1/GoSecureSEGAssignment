# syntax=docker/dockerfile:1

FROM python:latest

ADD ./readserver.py .

RUN pip3 install redis

EXPOSE 8080

CMD python3 -u ./readserver.py

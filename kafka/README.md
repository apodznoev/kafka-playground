Explanation of Kafka listeners:
https://rmoff.net/2018/08/02/kafka-listeners-explained/

Setup kafka in docker compose
https://hackernoon.com/setting-up-kafka-on-docker-for-local-development

How to read broker metadata with kcat (https://github.com/edenhill/kcat?ref=hackernoon.com):

```
 kcat -L -b localhost:9093
```
assuming the broker runs with a listener exposed on port 9093, e.g. docker-compose config:

```
- KAFKA_CFG_LISTENERS=INTERNAL://:9092,EXTERNAL://:9093
- KAFKA_CFG_ADVERTISED_LISTENERS=INTERNAL://kafka:9092,EXTERNAL://localhost:9093
- KAFKA_CFG_LISTENER_SECURITY_PROTOCOL_MAP=INTERNAL:PLAINTEXT,EXTERNAL:PLAINTEXT
```

Read and produce data from file
```
kcat -P -b localhost:9093 -t coffee-orders -K : -T -l coffee-orders.txt
```


Consume data from topic from the beginning
```
kcat -C -b localhost:9093 -o 0 -J -t coffee-orders -K :
```
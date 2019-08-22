# pinger

See our [helm charts](https://github.com/netifi/netifi-helm-charts) tutorial for how to really use this project.

# Releasing

```bash
./gradlew clean build dockerBuildImage
docker tag netifi/pinger-ping:latest netifi/pinger-ping:1.6.8
docker tag netifi/pinger-pong:latest netifi/pinger-pong:1.6.8
docker push netifi/pinger-ping:1.6.8
docker push netifi/pinger-pong:1.6.8
```

# Local Demo

Start a broker:
```bash
docker run --rm \
-p 7001:7001 \
-p 8001:8001 \
-p 8101:8101 \
-e BROKER_SERVER_OPTS=" \
'-Dnetifi.authentication.0.accessKey=9007199254740991'  \
'-Dnetifi.authentication.0.accessToken=kTBDVtfRBO4tHOnZzSyY5ym2kfY=' \
'-Dnetifi.broker.admin.accessKey=9007199254740991' \
'-Dnetifi.broker.admin.accessToken=kTBDVtfRBO4tHOnZzSyY5ym2kfY='" \
netifi/broker:1.6.8
```

Start a pong service:

```bash
docker run --rm -e SERVER_PORT=0 --net=host netifi/pinger-pong:1.6.8
```

Start a ping service:

```bash
docker run --rm -e SERVER_PORT=0 --net=host netifi/pinger-ping:1.6.8
```

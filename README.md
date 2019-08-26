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

# InfluxData Sandbox

The Ping and Pong instances support sending metrics to InfluxDB. To get started [InfluxData](https://www.influxdata.com/)
maintains a [Sandbox](https://github.com/influxdata/sandbox) project we can use if you already have
[Docker](https://docs.docker.com/install/) and [Docker Compose](https://docs.docker.com/compose/install/)
installed to start the entire TICK stack:

```bash
git clone https://github.com/influxdata/sandbox.git
cd sandbox
./sandbox up
```

# Prometheus

The Ping and Pong instances also support Prometheus metrics. You'll launch all your instances first,
and then edit the [prometheus.mac.yml](helper_files/prometheus.mac.yml) file. Then you can launch
Prometheus like so:

```bash
docker run -d --name=prometheus \
-p 9090:9090 \
-v $(pwd)/helper_files/prometheus.mac.yml:/etc/prometheus/prometheus.yml \
prom/prometheus --config.file=/etc/prometheus/prometheus.yml
```

Then you can go to [localhost:9090](http://localhost:9090) to see the Prometheus Web UI.

# Local Demo OS X

## Community Edition

Start a Netifi Broker:

```bash
docker run --rm \
-p 7001:7001 \
-p 8001:8001 \
-p 8101:8101 \
-e BROKER_SERVER_OPTS=" \
'-Dnetifi.broker.tcp.publicAddress=host.docker.internal' \
'-Dnetifi.authentication.0.accessKey=9007199254740991'  \
'-Dnetifi.authentication.0.accessToken=kTBDVtfRBO4tHOnZzSyY5ym2kfY=' \
'-Dnetifi.broker.admin.accessKey=9007199254740991' \
'-Dnetifi.broker.admin.accessToken=kTBDVtfRBO4tHOnZzSyY5ym2kfY=' \
" \
netifi/broker:1.6.8
```

Note, the keys provided in this tutorial can be changed and generated with the following command:

```bash
docker run --rm \
-e BROKER_SERVER_OPTS="-Dnetifi.broker.generateAccessToken=true" \
netifi/broker:1.6.8
```

Start a Pong service:

```bash
docker run --rm \
-e SPRING_PROFILES_ACTIVE=docker-mac,influx,prometheus \
-P netifi/pinger-pong:1.6.8
```

Start a Ping service:

```bash
docker run --rm \
-e SPRING_PROFILES_ACTIVE=docker-mac,influx,prometheus \
-P netifi/pinger-ping:1.6.8
```

## Enterprise Edition

Start a Netifi Broker:

```bash
docker run --rm \
-p 7001:7001 \
-p 8001:8001 \
-p 8101:8101 \
-e BROKER_SERVER_OPTS=" \
'-Dnetifi.enterprise.accessKey=<SECRET_KEY>' \
'-Dnetifi.enterprise.accessToken=<SECRET_TOKEN>' \
'-Dnetifi.enterprise.address=<SECRET_ADDRESS>' \
'-Dnetifi.broker.tcp.publicAddress=host.docker.internal' \
'-Dnetifi.broker.cluster.clusterName=pinger-local' \
" \
netifi/broker:1.6.8
```

Start a Pong service:

```bash
docker run --rm \
-e NETIFI_CLIENT_ACCESS_KEY=<SECRET_KEY> \
-e NETIFI_CLIENT_ACCESS_TOKEN=<SECRET_TOKEN> \
-e SPRING_PROFILES_ACTIVE=docker-mac,influx,prometheus \
-P netifi/pinger-pong:1.6.8
```

Start a Ping service:

```bash
docker run --rm \
-e NETIFI_CLIENT_ACCESS_KEY=<SECRET_KEY> \
-e NETIFI_CLIENT_ACCESS_TOKEN=<SECRET_TOKEN> \
-e SPRING_PROFILES_ACTIVE=docker-mac,influx,prometheus \
-P netifi/pinger-ping:1.6.8
```

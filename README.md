# pinger

See our [helm charts](https://github.com/netifi/netifi-helm-charts) tutorial for how to really use this project.

# Releasing

```bash
./gradlew clean build dockerBuildImage
docker tag netifi/pinger-ping:latest netifi/pinger-ping:1.6.7
docker tag netifi/pinger-pong:latest netifi/pinger-pong:1.6.7
docker push netifi/pinger-ping:1.6.7
docker push netifi/pinger-pong:1.6.7
```
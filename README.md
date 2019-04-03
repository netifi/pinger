# pinger


kubectl run pong --image=netifi/pinger-pong --image-pull-policy=Never --serviceaccount=musty-uakari-netifi-broker \
--env="NETIFI_PROTEUS_DISCOVERY_ENVIRONMENT=static" \
--env="NETIFI_PROTEUS_DISCOVERY_STATICPROPERTIES_CONNECTIONTYPE=tcp" \
--env="NETIFI_PROTEUS_DISCOVERY_STATICPROPERTIES_ADDRESSES=localhost" \
--port 8080
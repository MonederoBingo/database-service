#!/bin/bash
java $JAVA_OPTS \
    -Dserver.port=$PORT \
    -Deureka.client.service-url.defaultZone=https://mb-discovery-service.herokuapp.com/eureka/ \
    -Deureka.instance.hostname=database.monederobingo.com \
    -Deureka.instance.prefer-ip-address=false \
    -jar app.jar \

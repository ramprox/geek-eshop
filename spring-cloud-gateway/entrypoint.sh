#!/usr/bin/env sh

/apps/wait-service.sh CloudConfig cloud-config 80 && \
/apps/wait-service.sh Eureka eureka-service 80 && \
/opt/java/openjdk/bin/java -Xmx256m -Xss512k -XX:-UseContainerSupport \
              -jar /apps/app.jar

#!/usr/bin/env sh

/apps/wait-service.sh MySQL db 3306 && \
/apps/wait-service.sh CloudConfig cloud-config 80 && \
/opt/java/openjdk/bin/java -Xmx256m -Xss512k -XX:-UseContainerSupport \
              -jar /apps/app.jar

#!/usr/bin/env sh

/apps/wait-service.sh MySQL db 3306 && \
/apps/wait-service.sh CloudConfig cloud-config 80 && \
/apps/wait-service.sh Eureka eureka-service 80 && \
/apps/wait-service.sh RabbitMQ rabbitmq 5672 && \
/apps/wait-service.sh Redis redis 6379 && \
/apps/wait-service.sh Admin shop-admin-app 80 && \
/opt/java/openjdk/bin/java -Xmx256m -Xss512k -XX:-UseContainerSupport \
              -jar /apps/app.jar

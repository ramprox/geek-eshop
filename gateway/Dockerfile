FROM adoptopenjdk/openjdk11:alpine-jre

ARG JAR_FILE

RUN apk add --no-cache netcat-openbsd && rm -rf /var/cache/apk/*

RUN mkdir -p /apps
COPY ./target/${JAR_FILE} /apps/app.jar
COPY ./entrypoint.sh /apps/entrypoint.sh
COPY ../scripts/wait-service.sh /apps/wait-service.sh
RUN chmod +x /apps/entrypoint.sh
RUN chmod +x /apps/wait-service.sh

CMD ["/apps/entrypoint.sh"]

FROM adoptopenjdk/openjdk11:alpine-jre

ARG JAR_FILE

RUN mkdir -p /apps
RUN mkdir -p /apps/eshop-config
COPY ./target/${JAR_FILE} /apps/app.jar
COPY ./entrypoint.sh /apps/entrypoint.sh
RUN chmod +x /apps/entrypoint.sh

CMD ["/apps/entrypoint.sh"]

FROM openjdk:8u121-jdk
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
  && echo 'Asia/Shanghai' >/etc/timezone
ADD msi-client-samples.jar app.jar
COPY application.properties.template /
RUN mv application.properties application.properties
EXPOSE 7879
ENV GRPC_SERVER_HOST=""
ENV GRPC_SERVER_PORT=""
ENTRYPOINT java -jar /app.jar \
 --grpc.server.host=$GRPC_SERVER_HOST \
 --grpc.server.port=$GRPC_SERVER_PORT
FROM openjdk:8u121-jdk
MAINTAINER quanwenz@sina.cn
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
  && echo 'Asia/Shanghai' >/etc/timezone
ADD msi-server-samples.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar", "/app.jar"]
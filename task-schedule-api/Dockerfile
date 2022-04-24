FROM java:8-jre

ARG JAR_FILE

WORKDIR /opt/
ADD ./${JAR_FILE} ./app.jar
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone
EXPOSE 8080
CMD ["java", "-jar", "app.jar", "--spring.profiles.active=dev"]
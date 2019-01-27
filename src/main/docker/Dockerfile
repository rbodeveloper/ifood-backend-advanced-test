FROM openjdk:8-jre-alpine
VOLUME /tmp
ADD ifood-*-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
EXPOSE 8443
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]

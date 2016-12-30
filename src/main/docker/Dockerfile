FROM java:8-jre
MAINTAINER krzysztof.janiec@poczta.pl

EXPOSE 6002
VOLUME /tmp
ADD *.jar app.jar

RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
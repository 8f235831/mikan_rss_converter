FROM amazoncorretto:17
VOLUME /tmp
ADD /build/libs/mikan-rss-converter-0.0.1-SNAPSHOT.jar ./
EXPOSE 40401
ENTRYPOINT [ "java", "-jar", "./mikan-rss-converter-0.0.1-SNAPSHOT.jar" ]
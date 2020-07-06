FROM adoptopenjdk/openjdk11
COPY ./target/linwood-1.0-SNAPSHOT-jar-with-dependencies.jar /usr/app/
WORKDIR /usr/app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "linwood-1.0-SNAPSHOT-jar-with-dependencies.jar"]

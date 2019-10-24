FROM openjdk:11
EXPOSE 8080
copy ./build/libs/running-0.0.1-SNAPSHOT.jar running-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "-Dspring.profiles.active=local", "running-0.0.1-SNAPSHOT.jar"]
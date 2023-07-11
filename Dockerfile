FROM gradle:6.9.4-jdk8-jammy AS build

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle bootJar --no-daemon

FROM openjdk:8-jre-slim

EXPOSE 8000

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/spring-boot-application.jar
COPY --from=build /home/gradle/src/src/main/resources /src/main/resources

ENTRYPOINT ["java", "-jar","/app/spring-boot-application.jar"]
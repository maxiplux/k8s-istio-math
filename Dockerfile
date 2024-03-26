FROM gradle:jdk21 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --exclude-task test  --no-daemon  --exclude-task test

FROM gradle:jdk21


EXPOSE 8080

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/math-0.0.1-SNAPSHOT.jar /app/spring-boot-application.jar

ENTRYPOINT ["java","--enable-preview",  "-Djava.security.egd=file:/dev/./urandom","-jar","/app/spring-boot-application.jar"]
#docker buildx build --platform=linux/arm64,linux/amd64 --push --tag maxiplux/math-division-multiplication:1.0.0 -f ./Dockerfile .

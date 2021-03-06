
FROM gradle:jdk11 AS builder
WORKDIR /home/root/build/
COPY . .
RUN gradle build -x test --info

FROM openjdk:11-jre-slim
WORKDIR /home/root/app/
COPY --from=builder /home/root/build/build/libs/*.jar /home/root/app/server.jar
ENTRYPOINT ["java","-jar","server.jar", "--spring.profiles.active=container"]

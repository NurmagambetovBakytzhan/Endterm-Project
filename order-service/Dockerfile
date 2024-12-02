FROM maven:3.9.8-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .

RUN mvn clean install -DskipTests

COPY src ./src

RUN mvn package -DskipTests

FROM bellsoft/liberica-openjdk-debian:21 AS runtime

WORKDIR /app

COPY --from=build /app/target/*.jar /app/app.jar

EXPOSE 9191

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
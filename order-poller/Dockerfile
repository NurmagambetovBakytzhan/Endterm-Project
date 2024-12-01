FROM eclipse-temurin


ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

EXPOSE 9191

ENTRYPOINT ["java", "-jar", "app.jar"]

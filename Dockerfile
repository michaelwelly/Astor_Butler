FROM eclipse-temurin:17-jre

WORKDIR /app

COPY target/astor-butler.jar /app/astor-butler.jar

RUN chmod +x /app/astor-butler.jar

CMD ["java", "-jar", "/app/astor-butler.jar"]

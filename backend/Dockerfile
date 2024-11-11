FROM openjdk:17-alpine
WORKDIR /app
COPY build/libs/*.jar ./
COPY .env .env
RUN mv $(ls *.jar | grep -v plain) app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
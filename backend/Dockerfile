## 빌드 스테이지
#FROM gradle:7.6.1-jdk17-alpine AS build
#WORKDIR /app
#COPY . .
#RUN gradle clean build --no-daemon
#
## 실행 스테이지
#FROM openjdk:17-alpine
#WORKDIR /app
#COPY --from=build /app/build/libs/*.jar ./
#COPY .env .env
#RUN mv $(ls *.jar | grep -v plain) app.jar
#
## 환경변수 파일을 읽어서 실행할 때 적용
#ENTRYPOINT ["sh", "-c", "source .env && java -jar app.jar"]

FROM openjdk:17-alpine
WORKDIR /app
COPY /app/build/libs/*.jar ./
RUN mv $(ls *.jar | grep -v plain) app.jar

# 환경변수 파일을 읽어서 실행할 때 적용
ENTRYPOINT ["java", "-jar", "app.jar"]

FROM openjdk:17-jdk-slim

# JAR 파일을 컨테이너 안으로 복사
COPY build/libs/*.jar app.jar

EXPOSE 8080

# app 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
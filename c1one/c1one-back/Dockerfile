# 1. 빌드 환경
FROM gradle:8.7-jdk17 AS build
WORKDIR /app

# Gradle 캐시 최적화: 의존성만 먼저 복사
COPY build.gradle settings.gradle ./
RUN gradle build --no-daemon -x test || return 0

# 전체 소스 복사 후 빌드
COPY . .
RUN gradle clean build --no-daemon -x test

# 2. 런타임 환경
FROM eclipse-temurin:17-jre-jammy

# 시간대
ENV TZ=Asia/Seoul
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

WORKDIR /app

# 빌드된 JAR 복사
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

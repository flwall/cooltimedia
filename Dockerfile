FROM openjdk:8 AS TEMP_BUILD_IMAGE
WORKDIR /
COPY build.gradle settings.gradle gradlew /
COPY gradle /gradle
RUN ./gradlew build || return 0
COPY . .
RUN ./gradlew build -Dprofile=prod

FROM openjdk:8
WORKDIR /app
COPY --from=TEMP_BUILD_IMAGE /build/libs/cooltimedia.jar .
EXPOSE 8080
CMD ["java","-jar","cooltimedia.jar"]
FROM adoptopenjdk/openjdk15 AS TEMP_BUILD_IMAGE
WORKDIR /
RUN apt-get update&& apt-get install nodejs npm -y
COPY build.gradle settings.gradle gradlew /
COPY gradle /gradle
RUN chmod +x ./gradlew
COPY . .
RUN ./gradlew build -Dprofile=prod "-Dvaadin.productionMode=true"
RUN ls -la /build/libs

FROM openjdk:15
WORKDIR /app
COPY --from=TEMP_BUILD_IMAGE /build/libs/cooltimedia-plattform-1.0-Beta.jar .
RUN mv cooltimedia-plattform-1.0-Beta.jar cooltimedia.jar
EXPOSE 8080
CMD ["java","-jar","-Dspring.profiles.active=prod","cooltimedia.jar"]
FROM adoptopenjdk/openjdk15 AS TEMP_BUILD_IMAGE
WORKDIR /
RUN apt-get update&& apt-get install nodejs npm -y
COPY . .
RUN chmod +x ./gradlew&& ./gradlew build -Dprofile=prod "-Dvaadin.productionMode=true"


FROM openjdk:15
WORKDIR /app
COPY --from=TEMP_BUILD_IMAGE /build/libs/cooltimedia-plattform-1.0-Beta.jar .
RUN mv cooltimedia-plattform-1.0-Beta.jar cooltimedia.jar
RUN echo $PORT
CMD java $JAVA_OPTS -jar -Dspring.profiles.active=prod -Dserver.port=$PORT cooltimedia.jar
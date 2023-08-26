FROM openjdk:17-oracle
ARG JAR_FILE=./build/libs/*.jar
COPY ${JAR_FILE} app.jar
COPY wait-for-it.sh /usr/local/bin/
ENTRYPOINT ["wait-for-it.sh", "db:5432", "--", "java", "-jar", "-Dspring.profiles.active=local","app.jar"]


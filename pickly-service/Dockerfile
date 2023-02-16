FROM openjdk:17-oracle

#
#ARG JAR_FILE=build/libs/pickly-service-1.0-SNAPSHOT.jar
#COPY ${JAR_FILE} pickly-service-1.0-SNAPSHOT.jar
#
#EXPOSE 8080
#
#CMD java \
#        -Xms200m \
#        -Xmx200m \
#        -Dfile.encoding=UTF-8 \
#        -DlogAppender=STDOUT \
#        -Dspring.profiles.active=prod \
#        -Duser.timezone=Asia/Seoul \
#        -jar pickly-service-1.0-SNAPSHOT.jar

ARG JAR_FILE=./build/libs/*.jar
COPY ${JAR_FILE} app.jar
COPY wait-for-it.sh /usr/local/bin/
ENTRYPOINT ["wait-for-it.sh", "db:5432", "--", "java", "-jar", "-Dspring.profiles.active=local","app.jar"]


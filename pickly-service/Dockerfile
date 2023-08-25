FROM openjdk:17-oracle
ARG JAR_FILE=./build/libs/*.jar
COPY ${JAR_FILE} app.jar
COPY wait-for-it.sh /usr/local/bin/
ENTRYPOINT ["wait-for-it.sh", "db:5432", "--", "java", "-javaagent:/home/ec2-user/scouter/agent.java/scouter.agent.jar -Dscouter.config=/home/ec2-user/scouter/agent.java/conf/scouter.conf -Dobj_name=WAS-01", "-jar", "-Dspring.profiles.active=dev","app.jar"]


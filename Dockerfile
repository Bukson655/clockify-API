FROM openjdk:17
EXPOSE 8080
ADD build/libs/projekt-0.0.1-SNAPSHOT.jar clockify-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/clockify-0.0.1-SNAPSHOT.jar"]

#FROM openjdk:17
#ARG JAR_FILE=build/libs/*.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]
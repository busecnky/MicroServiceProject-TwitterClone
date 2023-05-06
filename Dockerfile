FROM amazoncorretto:17.0.6
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java","-Xmx2048M","-jar","/application.jar"]
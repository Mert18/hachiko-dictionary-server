FROM eclipse-temurin:17

COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package

FROM eclipse-temurin:17
WORKDIR hachiko-dictionary
COPY target/*.jar hachiko-dictionary.jar

ENTRYPOINT ["java","-jar","hachiko-dictionary.jar"]
EXPOSE 8888
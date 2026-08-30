# Stage 1 Build-Stage

FROM maven:3.9.9-eclipse-temurin-21 as build


WORKDIR /app

COPY pom.xml ./

RUN  mvn -B dependency:go-offline

COPY  src  ./src

RUN mvn -B clean package -DskiptTests 

# Stage 2 Runtime Stage

FROM eclipse-temurin:21-jre-jammy

WORKDIR  /app

COPY --from=build /app/target/*.jar /app.jar

EXPOSE 8080

ENTRYPOINT [ "java" , "-jar" "app.jar" ]

FROM maven:3.8.5-openjdk-17 AS build  
WORKDIR "/app"
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:18-jdk-alpine
COPY --from=build /app/target/*.jar /app/app.jar  
# EXPOSE 8080  
ENTRYPOINT ["java","-jar","/app/app.jar"]  
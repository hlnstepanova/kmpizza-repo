FROM openjdk:11
EXPOSE 8080:8080
RUN mkdir /app
COPY ./backend/build/libs/Backend.jar /app/Backend.jar
ENTRYPOINT ["java","-jar","/app/Backend.jar"]

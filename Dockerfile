FROM openjdk:11

EXPOSE 8080

RUN mkdir ./app

COPY ./build/libs/currency-0.0.1-SNAPSHOT.jar ./app

CMD java -jar ./app/currency-0.0.1-SNAPSHOT.jar
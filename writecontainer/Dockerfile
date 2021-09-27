FROM java:8
WORKDIR /
ADD target/writecontainer-1.0-SNAPSHOT-jar-with-dependencies.jar writecontainer-1.0-SNAPSHOT-jar-with-dependencies.jar
ADD people.csv people.csv
CMD java -jar writecontainer-1.0-SNAPSHOT-jar-with-dependencies.jar people.csv

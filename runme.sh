#!/bin/sh
if [ "$1" = "" ]; then
  printf "remember to tell me how many months to look for by passing it on the command line\n"
  exit -1
fi
cd FlightSearch/FlightSearch
mvn package
java -jar target/FlightSearch-0.0.1-SNAPSHOT-jar-with-dependencies.jar $1

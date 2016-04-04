#!/bin/bash

if [[ $1 == "-b" ]]; then
  mvn clean install
  cp ./target/stdout-timer-0.0.1-SNAPSHOT.jar ./test/
fi

java -jar ./test/test-0.0.1-SNAPSHOT.jar  | java -jar ./test/stdout-timer-0.0.1-SNAPSHOT.jar

#!/bin/bash

if [[ $1 == "-b" ]]; then
  mvn clean install
  cp ./target/jnomon-0.0.1-SNAPSHOT.jar ./test/
fi

java -jar ./test/test-0.0.1-SNAPSHOT.jar  | java -jar ./test/jnomon-0.0.1-SNAPSHOT.jar

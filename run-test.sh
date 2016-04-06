#!/bin/bash

if [[ $1 == "-b" ]]; then
  mvn clean install
  cp ./target/jnomon-0.0.1-SNAPSHOT.jar ./test/
fi

STEP_7_LENGTH=1234

java -jar ./test/test-0.0.1-SNAPSHOT.jar $STEP_7_LENGTH | java -jar ./test/jnomon-0.0.1-SNAPSHOT.jar -h 2500 -m 1500

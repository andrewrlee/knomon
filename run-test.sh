#!/bin/bash

if [[ $1 == "-b" ]]; then
  ./gradlew clean build
fi

STEP_7_LENGTH=1234

java -jar test/test-0.0.1-SNAPSHOT.jar | java -jar build/libs/knomon.jar -h 2500 -m 1500 -r 10
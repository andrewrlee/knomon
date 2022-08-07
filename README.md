# knomon [![Build Status](https://api.travis-ci.org/plasma147/knomon.svg?branch=main)](https://travis-ci.org/plasma147/knomon) [![Coverage Status](https://coveralls.io/repos/github/plasma147/knomon/badge.svg?branch=master)](https://coveralls.io/github/plasma147/knomon?branch=main)

Knomon port of jnomon (a partial java port of [gnomon](https://github.com/paypal/gnomon)) using [RxJava](https://github.com/ReactiveX/RxJava).

### Demo: 

[![asciicast](https://asciinema.org/a/0o66a91aifh4fhlvhph2n0w7a.png)](https://asciinema.org/a/0o66a91aifh4fhlvhph2n0w7a)

### Building/Running:

Build and test by passing the -b flag to the `run-test` script:

  `./run-test.sh -b`

The self contained executable can then be found in: `./build/libs/knomon.jar`

Any command line program can be piped through the executable. For instance, from the root project dir:

  `./gradlew clean build | java -jar build/libs/knomon.jar -h 2500 -m 1500 -r 10`

### Options:

Only currently implements a subset of the gnomon switches. 
All switches are optional and described below:

```

--type=<elapsed-line|elapsed-total|absolute>        [default: elapsed-line]
-t <elapsed-line|elapsed-total|absolute>

  Type of timestamp to display.
    elapsed-line: Seconds that displayed line was the last line.
    elapsed-total: Seconds since the start of the process.
    absolute: Absolute timestamp in UTC.

--high millis
-h millis

  High threshold. If the elapsed time for a line is equal to or higher than
  this value in millis, then the timestamp will be coloured red.

--medium millis
-m millis

  Medium threshold. Works just like the high threshold described above, but
  colors the timestamp bright yellow instead. Can be used in conjunction
  with a high threshold for three levels.

--real-time=<number|false>                          [default: 500]
-r

  Time increment to use when updating timestamp, in millis. Pass `false` to
  this option to disable realtime updating. When realtime is disabled,
  the log will always appear one line "behind" the piped output, since it can't
  display the line until it's finished timing it.
```

### Limitations:

 * Does not cope gracefully with long log lines: Gnomon uses node.js tty support to determine how big the current terminal is, but this is a bit more tricky in java
 * Similarly, it won't detect that it has been piped to a non-tty env, so won't automatically disable the --real-time option. 
 * Only tested to work on linux


### Notes on port to kotlin

* Moved to gradle
* Moved to java 18
* Ditched immutables (to avoid getting Immutables annotation processor working)
* Updated Mockito as the old version is incompatible with later versions of java
* Ditch really-executable-jar behaviour and add script and update running description. (There is an example of a "really executable" jar creation [here](https://github.com/pinterest/ktlint/pull/515/files))
* Add fat jar gradle task
* Remove code coverage
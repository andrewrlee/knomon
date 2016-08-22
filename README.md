# jnomon [![Build Status](https://api.travis-ci.org/plasma147/jnomon.svg?branch=master)](https://travis-ci.org/plasma147/jnomon) [![Coverage Status](https://coveralls.io/repos/github/plasma147/jnomon/badge.svg?branch=master)](https://coveralls.io/github/plasma147/jnomon?branch=master)

Partial java port of [gnomon](https://github.com/paypal/gnomon) using [RxJava](https://github.com/ReactiveX/RxJava) and [Immutables](http://immutables.github.io/).

###Demo: 

[![asciicast](https://asciinema.org/a/0o66a91aifh4fhlvhph2n0w7a.png)](https://asciinema.org/a/0o66a91aifh4fhlvhph2n0w7a)

###Building/Running:

Build and test by passing the -b flag to the `run-test` script:

  `./run-test.sh -b`

Any command line program can be piped through the executable. For instance, from the root project dir:

  `mvn clean install | ./target/jnomon -m 200 -h 500`

###Options:

This was an excuse to play with RxJava and as such only currently implements a subset of the gnomon switches. 
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

###Limitations:

 * Does not cope gracefully with long log lines: Gnomon uses node.js tty support to determine how big the current terminal is, but this is a bit more tricky in java
 * Similarly, it won't detect that it has been piped to a non-tty env, so won't automatically disable the --real-time option. 
 * Only tested to work on linux

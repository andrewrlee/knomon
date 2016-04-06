# jnomon

Partial java port of [gnomon](https://github.com/paypal/gnomon) using [RxJava](https://github.com/ReactiveX/RxJava) and [Immutables](http://immutables.github.io/).
 
Build and test by issuing the following:

  `./run-test.sh -b`

[![asciicast](https://asciinema.org/a/1uextdlz8doz1jwu8m7vfgulj.png)](https://asciinema.org/a/1uextdlz8doz1jwu8m7vfgulj)

Run by piping any command line program through the java executable. For instance:

  `mvn clean install | ./jnomon -m 200 -h 500`

###Options

This was an excuse to play with RxJava and as such this  only implements a subset of the gnomon switches. 

```
--high millis
-h millis

  High threshold. If the elapsed time for a line is equal to or higher than
  this value in millis, then the timestamp will be coloured red.

--medium millis
-m millis

  Medium threshold. Works just like the high threshold described above, but
  colors the timestamp bright yellow instead. Can be used in conjunction
  with a high threshold for three levels.
```

###Additional Limitations/Bugs:

 * Does not cope gracefully with long log lines: Gnomon uses node.js tty support to determine how big the current terminal is, but this is a bit more tricky in java


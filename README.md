# jnomon

Partial java port of [gnomon](https://github.com/paypal/gnomon) using [RxJava](https://github.com/ReactiveX/RxJava) and [Immutables](http://immutables.github.io/).

This was an excuse to play with RxJava and as such this currently only implements the basic timing feature of gnomon (no additional command line args). 

Build and test by issuing the following:

  `./run-test.sh -b`

Run by piping any command line program through the java executable, e.g:

  `mvn clean install | java -jar ./target/jnomon-0.0.1-SNAPSHOT.jar`

###Additional Limitations/Bugs:

 * Does not cope gracefully with long log lines: Gnomon uses node.js tty support to determine how big the current terminal is, but this is a bit more tricky in java
 * Minor (easy to fix!) bug, formatting of seconds is a bit skew-whiff for values over 10. 


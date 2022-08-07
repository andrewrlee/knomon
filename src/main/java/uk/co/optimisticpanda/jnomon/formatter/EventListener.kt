package uk.co.optimisticpanda.jnomon.formatter

import uk.co.optimisticpanda.jnomon.Utils.Colour
import java.io.PrintStream
import java.time.Duration

interface EventListener {
  fun onBeforeAll() {
    printer().println()
  }

  fun onLineStart(colour: Colour?, sinceProcessStart: Duration, sinceLastStart: Duration, line: String?) {
    // NO-OP
  }

  fun onUpdate(colour: Colour?, sinceProcessStart: Duration, sinceLastStart: Duration) {
    // NO-OP
  }

  fun onLineEnd(sinceProcessStart: Duration, sinceLastStart: Duration, lastLine: String?) {
    // NO-OP
  }

  fun onFinally(sinceProcessStart: Duration, sinceLastStart: Duration?) {
    // NO-OP
  }

  fun printer(): PrintStream {
    return System.out
  }
}
package uk.co.optimisticpanda.jnomon.formatter

import uk.co.optimisticpanda.jnomon.Utils.Colour
import uk.co.optimisticpanda.jnomon.Utils.MOVE_TO_START_OF_PREV_LINE
import uk.co.optimisticpanda.jnomon.Utils.formatLine
import uk.co.optimisticpanda.jnomon.Utils.formatSeconds
import java.time.Duration

class ElapsedLineOutputWriter : EventListener {
  override fun onLineStart(colour: Colour?, sinceProcessStart: Duration, sinceLastStart: Duration, line: String?) {
    onUpdate(colour, sinceProcessStart, sinceLastStart)
    printer().println(formatLine(8, "", line))
  }

  override fun onUpdate(colour: Colour?, sinceProcessStart: Duration, sinceLastStart: Duration) {
    val text: String = formatLine(8, formatSeconds(sinceLastStart), "")
    printer().println(MOVE_TO_START_OF_PREV_LINE + colour!!.colourize(text))
  }

  override fun onLineEnd(sinceProcessStart: Duration, sinceLastStart: Duration, lastLine: String?) {
    printer().println(formatLine(8, formatSeconds(sinceLastStart), lastLine))
  }

  override fun onFinally(sinceProcessStart: Duration, sinceLastStart: Duration?) {
    printer().println(formatLine(8, "Total:", formatSeconds(sinceProcessStart)))
  }
}
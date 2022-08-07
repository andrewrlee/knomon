package uk.co.optimisticpanda.jnomon.formatter

import uk.co.optimisticpanda.jnomon.ColourChooser
import java.time.Duration
import java.util.Optional

class EventListenerAdapter(
  private val colourChooser: ColourChooser?,
  private val eventListener: EventListener?,
  private val realTime: Boolean
) {
    private var lastLine: String? = null
    fun onBeforeAll() {
        if (realTime) {
            eventListener!!.onBeforeAll()
        }
    }

    fun onLineStart(sinceProcessStart: Duration, sinceLastStart: Duration, line: String?) {
        if (realTime) {
            val colour = colourChooser!!.colourForDuration(sinceLastStart)
            eventListener!!.onLineStart(colour, sinceProcessStart, sinceLastStart, line)
        } else {
            eventListener!!.onLineEnd(sinceProcessStart, sinceLastStart, lastLine())
        }
        lastLine = line
    }

    fun onUpdate(sinceProcessStart: Duration, sinceLastStart: Duration) {
        if (realTime) {
            val colour = colourChooser!!.colourForDuration(sinceLastStart)
            eventListener!!.onUpdate(colour, sinceProcessStart, sinceLastStart)
        }
    }

    fun onFinally(sinceProcessStart: Duration, sinceLastStart: Duration) {
        if (!realTime) {
            eventListener!!.onLineEnd(sinceProcessStart, sinceLastStart, lastLine())
        }
        eventListener!!.onFinally(sinceProcessStart, sinceLastStart)
    }

    fun lastLine(): String {
        return Optional.ofNullable(lastLine).orElse("")
    }
}
package uk.co.optimisticpanda.jnomon

sealed class Event {

  data class TickEvent(val tick: Long) : Event()

  data class LineEvent(val line: String) : Event()

  object QuitEvent : Event()

}
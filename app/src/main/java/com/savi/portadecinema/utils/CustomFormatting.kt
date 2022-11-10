package com.savi.portadecinema.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CustomFormatting {
    companion object {
        fun format(date: LocalDate, pattern: String) : String =
            date.format(DateTimeFormatter.ofPattern(pattern))

        fun formatDuration(minutes: Int) : String {
            val hours = minutes / 60
            val minutesLeft = minutes - (hours * 60)
            return "$hours h $minutesLeft min"
        }
    }
}

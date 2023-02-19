package com.github.nyanfantasia.shizurunotes.data

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

open class EventSchedule(
    val id: Int,
    val name: String,
    val type: EventType,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime
) {
    open val title: String = when (type) {
        EventType.Hatsune -> type.description + "：" + name
        else -> type.description
    }

    val durationString: String
        get() {
            val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            return startTime.format(pattern) + "  ~  " + endTime.format(pattern)
        }
}
package app.pankaj.utils

import org.koin.core.component.KoinComponent
import java.time.Instant
import java.util.*


object DateUtils : KoinComponent {

    fun toIsoString(date: Date?): String? = if (null == date) null else Instant.ofEpochMilli(date.time).toIsoString()
}

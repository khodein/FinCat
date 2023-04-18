package com.sergei.pokhodai.expensemanagement.utils

import android.content.Context
import android.os.Parcelable
import com.sergei.pokhodai.expensemanagement.utils.enums.Language
import com.sergei.pokhodai.expensemanagement.R
import kotlinx.parcelize.Parcelize
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

@Parcelize
@Suppress("FunctionName")
class LocalDateFormatter(
    val localDateTime: LocalDateTime
) : Parcelable, Comparable<LocalDateFormatter> {

    fun format(pattern: String, locale: Locale = Locale(Language.EU.calendarLocale)): String {
        val formatter = DateTimeFormatter.ofPattern(pattern).withLocale(locale)
        return  localDateTime.format(formatter)
    }

    fun isItThisYear() = now().localDateTime.year == localDateTime.year

    fun dd_MM_yyyy(language: Language) = format("dd.MM.yyyy", Locale(language.calendarLocale))
    fun MMMM_yyyy(language: Language) = format("MMMM yyyy",  Locale(language.calendarLocale))
    fun yyyy(language: Language) = format("yyyy",  Locale(language.calendarLocale))
    fun MMMM(language: Language) = format("MMMM",  Locale(language.calendarLocale))
    fun dd_MMMM_yyyy(language: Language) = format("dd MMMM yyyy",  Locale(language.calendarLocale))
    fun toIsoFormatWithoutTZ() = format(ISO_FORMAT)

    fun timeInMillis() = localDateTime.atZone(ZoneOffset.UTC).toInstant().toEpochMilli()
    fun toIsoUTC(): String = localDateTime.atZone(ZoneId.systemDefault()).toInstant()
        .atOffset(ZoneOffset.ofHours(0))
        .format(DateTimeFormatter.ofPattern(ISO_FORMAT))

    inline fun update(block: LocalDateTime.() -> LocalDateTime): LocalDateFormatter {
        return LocalDateFormatter(localDateTime.block())
    }

    fun withLocalTime(other: LocalDateFormatter): LocalDateFormatter {
        return LocalDateFormatter(
            localDateTime.withHour(other.localDateTime.hour).withMinute(other.localDateTime.minute)
        )
    }

    fun between(other: LocalDateFormatter): Duration =
        Duration.between(localDateTime, other.localDateTime)

    override fun compareTo(other: LocalDateFormatter): Int {
        return timeInMillis().compareTo(other.timeInMillis())
    }

    override fun equals(other: Any?): Boolean =
        (other is LocalDateFormatter) && this.timeInMillis() == other.timeInMillis()

    fun equalsDate(other: LocalDateFormatter): Boolean =
        localDateTime.year == other.localDateTime.year
                && localDateTime.dayOfYear == other.localDateTime.dayOfYear

    override fun hashCode(): Int {
        return localDateTime.hashCode()
    }

    companion object {
        const val ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

        fun now() = LocalDateFormatter(LocalDateTime.now())
        fun nowUtc() = LocalDateFormatter(LocalDateTime.now(ZoneOffset.UTC))
        fun nowWithZoneOffset(offsetInHours: Int): LocalDateFormatter {
            val utc = LocalDateTime.now(ZoneOffset.ofHours(offsetInHours))
            return LocalDateFormatter(utc)
        }

        fun today() = LocalDateFormatter(LocalDateTime.now().toLocalDate().atStartOfDay())
        fun from(millis: Long) = LocalDateFormatter(
            LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault())
        )

        fun parse(string: String?, pattern: String): LocalDateFormatter? {
            val formatter = DateTimeFormatter.ofPattern(pattern)
            val localDateTime: LocalDateTime =
                LocalDate.parse(string, formatter)?.atStartOfDay() ?: return null
            return LocalDateFormatter(localDateTime)
        }
    }
}

fun LocalDateFormatter.getMonthLocalization(context: Context): String {
    return context.getString(when (this.localDateTime.month) {
        Month.JANUARY -> R.string.month_january
        Month.FEBRUARY -> R.string.month_february
        Month.MARCH -> R.string.month_march
        Month.APRIL -> R.string.month_april
        Month.MAY -> R.string.month_may
        Month.JUNE -> R.string.month_june
        Month.JULY -> R.string.month_jule
        Month.AUGUST -> R.string.month_august
        Month.SEPTEMBER -> R.string.month_september
        Month.OCTOBER -> R.string.month_october
        Month.NOVEMBER -> R.string.month_november
        Month.DECEMBER -> R.string.month_december
    })
}
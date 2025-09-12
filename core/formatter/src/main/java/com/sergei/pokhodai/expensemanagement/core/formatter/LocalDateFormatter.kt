package com.sergei.pokhodai.expensemanagement.core.formatter

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.atTime
import kotlinx.datetime.daysUntil
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.minus
import kotlinx.datetime.monthsUntil
import kotlinx.datetime.periodUntil
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.until
import kotlinx.datetime.yearsUntil
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@JvmInline
value class LocalDateFormatter(
    val localDateTime: LocalDateTime
) {
    val month: Month
        get() = localDateTime.month

    val year: Int
        get() = localDateTime.year

    val dayOfMonth: Int
        get() = localDateTime.day

    val dayOfWeek: DayOfWeek
        get() = localDateTime.dayOfWeek

    val dayOfYear: Int
        get() = this.localDateTime.dayOfYear

    /**Суббота или Воскресенье*/
    val isWeekend: Boolean
        get() {
            val dayOfWeek = this.localDateTime.dayOfWeek
            return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY
        }

    /**Кол-во недель в месяце*/
    val weeksOfMonth: Long
        get() = monthDays / 7

    /**Кол-во дней в месяце*/
    val monthDays: Long
        get() {
            val year = localDateTime.year
            val month = localDateTime.month
            val start = LocalDate(year, month, 1)
            val end = start.plus(1, DateTimeUnit.Companion.MONTH)
            return start.until(end, DateTimeUnit.Companion.DAY)
        }

    fun dd(): String = getPattern("dd")

    fun MM() = getPattern("MM")

    fun yyyy(): String = getPattern("yyyy")

    fun yyyy_MM(): String = getPattern("yyyy_MM")

    fun yyyy_MM_dd() = getPattern(BASE_FORMAT)

    private fun getPattern(
        pattern: String
    ): String {
        return localDateTime
            .toJavaLocalDateTime()
            .format(DateTimeFormatter.ofPattern(pattern))
    }

    inline fun update(block: LocalDateTime.() -> LocalDateTime): LocalDateFormatter {
        return LocalDateFormatter(localDateTime.block())
    }

    /**Возвращает начало дня типо 2025-01-13T00:00*/
    @OptIn(ExperimentalTime::class)
    fun startOfTheDay(
        timeZone: TimeZone = getSystemDefault()
    ): LocalDateFormatter {
        val startOfTheDay = localDateTime
            .date
            .atStartOfDayIn(timeZone)
            .toLocalDateTime(timeZone)
        return LocalDateFormatter(startOfTheDay)
    }

    fun formatDateToLocalizedMonthYear(): String {
        val javaLocalDateTime = localDateTime.toJavaLocalDateTime()
        var monthName = javaLocalDateTime.month.getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault())
        monthName = monthName.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault())
            else it.toString()
        }
        val year = javaLocalDateTime.year
        return "$monthName, $year"
    }

    /**Возвращает конец дня типо 2025-01-12T23:59:59*/
    fun endOfTheDay(
        timeZone: TimeZone = getSystemDefault()
    ): LocalDateFormatter {
        return startOfTheDay(timeZone)
            .plusDays(1)
            .setOperation(
                quantity = 1000,
                unit = DateTimeUnit.Companion.MILLISECOND,
                timeZone = timeZone,
                operator = Operator.MINUS
            )
    }

    /**Возвращает завтрашний день прибавив ровно 24 часа*/
    fun tomorrow(
        timeZone: TimeZone = getSystemDefault(),
    ): LocalDateFormatter {
        return this.setOperation(
            quantity = 1,
            unit = DateTimeUnit.Companion.DAY,
            timeZone = timeZone,
            operator = Operator.PLUS
        )
    }

    /**Возвращает вчерашний день отнимет 24 часа*/
    fun yesterday(
        timeZone: TimeZone = getSystemDefault(),
    ): LocalDateFormatter {
        return this.setOperation(
            quantity = 1,
            unit = DateTimeUnit.Companion.DAY,
            timeZone = timeZone,
            operator = Operator.MINUS
        )
    }

    fun plusWeek(
        quantity: Int,
        timeZone: TimeZone = getSystemDefault(),
    ): LocalDateFormatter {
        return this.setOperation(
            quantity = quantity,
            unit = DateTimeUnit.Companion.WEEK,
            timeZone = timeZone,
            operator = Operator.PLUS
        )
    }

    fun plusHour(
        quantity: Int,
        timeZone: TimeZone = getSystemDefault(),
    ): LocalDateFormatter {
        return this.setOperation(
            quantity = quantity,
            unit = DateTimeUnit.Companion.HOUR,
            timeZone = timeZone,
            operator = Operator.PLUS
        )
    }

    /**Возвращает дату с прибаленным кол-вом дней*/
    fun plusDays(
        quantity: Int,
        timeZone: TimeZone = getSystemDefault(),
    ): LocalDateFormatter {
        return this.setOperation(
            quantity = quantity,
            unit = DateTimeUnit.Companion.DAY,
            timeZone = timeZone,
            operator = Operator.PLUS
        )
    }

    /**Возвращает дату с вычитом кол-вом дней*/
    @OptIn(ExperimentalTime::class)
    fun minusDays(
        quantity: Int,
        timeZone: TimeZone = getSystemDefault(),
    ): LocalDateFormatter {
        val minusDays = localDateTime
            .toInstant(timeZone)
            .minus(
                value = quantity,
                unit = DateTimeUnit.Companion.DAY,
                timeZone = timeZone
            ).toLocalDateTime(timeZone)

        return LocalDateFormatter(minusDays)
    }

    fun changeMonth(newMonth: Int): LocalDateFormatter {
        val localDateTime = LocalDateTime(
            year = localDateTime.year,
            month = newMonth,
            day = localDateTime.day,
            hour = localDateTime.hour,
            minute =localDateTime.minute,
            second = localDateTime.second,
            nanosecond = localDateTime.nanosecond
        )

        return LocalDateFormatter(localDateTime)
    }

    /**Возвращает дату с прибалением кол-вом месяцев*/
    fun plusMonths(
        quantity: Int,
        timeZone: TimeZone = getSystemDefault(),
    ): LocalDateFormatter {
        return this.setOperation(
            quantity = quantity,
            unit = DateTimeUnit.Companion.MONTH,
            timeZone = timeZone,
            operator = Operator.PLUS
        )
    }

    /**Возвращает дату с вычитом кол-вом месяцев*/
    fun minusMonths(
        quantity: Int,
        timeZone: TimeZone = getSystemDefault(),
    ): LocalDateFormatter {
        return this.setOperation(
            quantity = quantity,
            unit = DateTimeUnit.Companion.MONTH,
            timeZone = timeZone,
            operator = Operator.MINUS
        )
    }

    /**Возвращает дату с прибавлением кол-вом лет*/
    fun plusYears(
        quantity: Int,
        timeZone: TimeZone = getSystemDefault(),
    ): LocalDateFormatter {
        return this.setOperation(
            quantity = quantity,
            unit = DateTimeUnit.Companion.YEAR,
            timeZone = timeZone,
            operator = Operator.PLUS
        )
    }

    /**Возвращает дату с вычитом кол-вом лет*/
    fun minusYears(
        quantity: Int,
        timeZone: TimeZone = getSystemDefault()
    ): LocalDateFormatter {
        return this.setOperation(
            quantity = quantity,
            unit = DateTimeUnit.Companion.YEAR,
            timeZone = timeZone,
            operator = Operator.MINUS
        )
    }

    fun period(untilLocalDateFormatter: LocalDateFormatter): DatePeriod {
        val thisDate = localDateTime.date
        val untilDate = untilLocalDateFormatter.localDateTime.date
        return thisDate.periodUntil(untilDate)
    }

    fun isYesterday(): Boolean {
        val yesterday = today().yesterday()
        return this.yyyy_MM_dd() == yesterday.yyyy_MM_dd()
    }

    fun isToday(): Boolean {
        val today = today()
        return this.yyyy_MM_dd() == today.yyyy_MM_dd()
    }

    fun between(
        untilLocalDateFormatter: LocalDateFormatter,
        dateBased: DateTimeUnit.DateBased
    ): Long {
        val thisDate = localDateTime.date
        val untilDate = untilLocalDateFormatter.localDateTime.date
        val period = thisDate.until(untilDate, dateBased)
        return period
    }

    fun betweenDays(untilLocalDateFormatter: LocalDateFormatter): Int {
        return localDateTime.date.daysUntil(untilLocalDateFormatter.localDateTime.date)
    }

    fun betweenMonths(untilLocalDateFormatter: LocalDateFormatter): Int {
        return localDateTime.date.monthsUntil(untilLocalDateFormatter.localDateTime.date)
    }

    fun betweenYeats(untilLocalDateFormatter: LocalDateFormatter): Int {
        return localDateTime.date.yearsUntil(untilLocalDateFormatter.localDateTime.date)
    }

    @OptIn(ExperimentalTime::class)
    fun toMillis(): Long {
        return localDateTime.toInstant(TimeZone.Companion.currentSystemDefault()).toEpochMilliseconds()
    }

    private enum class Operator {
        PLUS,
        MINUS
    }

    companion object {
        private const val BASE_FORMAT = "yyyy-MM-dd"

        @OptIn(ExperimentalTime::class)
        private fun LocalDateFormatter.setOperation(
            quantity: Int,
            unit: DateTimeUnit,
            timeZone: TimeZone,
            operator: Operator,
        ): LocalDateFormatter {
            val instant = this@setOperation.localDateTime.toInstant(timeZone)
            val newInstant = when (operator) {
                Operator.PLUS -> {
                    instant.plus(
                        value = quantity,
                        unit = unit,
                        timeZone = timeZone
                    )
                }

                Operator.MINUS -> {
                    instant.minus(
                        value = quantity,
                        unit = unit,
                        timeZone = timeZone
                    )
                }
            }
            val localDateTime = newInstant.toLocalDateTime(timeZone)
            return LocalDateFormatter(localDateTime)
        }

        @OptIn(ExperimentalTime::class)
        private fun getInstant(): Instant {
            return Clock.System.now()
        }

        private fun getSystemDefault(): TimeZone {
            return TimeZone.Companion.currentSystemDefault()
        }

        @OptIn(FormatStringsInDatetimeFormats::class)
        fun baseFormatParse(date: String): LocalDateFormatter {
            val format = LocalDate.Companion.Format {
                byUnicodePattern(BASE_FORMAT)
            }
            val localDate = LocalDate.Companion.parse(input = date, format = format)
            return LocalDateFormatter(localDate.atTime(0, 0))
        }

        @OptIn(FormatStringsInDatetimeFormats::class)
        fun exchangeFormatParse(date: String): LocalDateFormatter {
            val format = LocalDate.Companion.Format {
                byUnicodePattern(BASE_FORMAT)
            }
            val localDate = LocalDate.Companion.parse(input = date.substringBefore("T"), format = format)

            return LocalDateFormatter(localDateTime = localDate.atTime(0, 0))
        }

        /**Возвращает дату сегодня в начале дня*/
        fun today(timeZone: TimeZone = getSystemDefault()): LocalDateFormatter {
            return nowInSystemDefault().endOfTheDay(timeZone)
        }

        /**Возвращает дату, по UTC формату*/
        @OptIn(ExperimentalTime::class)
        fun nowInUTC(): LocalDateFormatter {
            return LocalDateFormatter(getInstant().toLocalDateTime(TimeZone.Companion.UTC))
        }

        /**Возвращает дату, сейчас, по таймзоне телефона*/
        @OptIn(ExperimentalTime::class)
        fun nowInSystemDefault(): LocalDateFormatter {
            return LocalDateFormatter(getInstant().toLocalDateTime(getSystemDefault()))
        }

        @OptIn(ExperimentalTime::class)
        fun toLocalDateFormatter(millis: Long): LocalDateFormatter {
            val localDateTime = Instant.Companion.fromEpochMilliseconds(millis)
                .toLocalDateTime(TimeZone.Companion.currentSystemDefault())
            return LocalDateFormatter(localDateTime)
        }
    }
}
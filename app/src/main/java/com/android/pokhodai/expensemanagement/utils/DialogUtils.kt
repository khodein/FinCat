package com.android.pokhodai.expensemanagement.utils

import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker

inline fun FragmentManager.showDatePickerDialog(
    value: LocalDateFormatter,
    start: LocalDateFormatter = LocalDateFormatter.today().update { minusYears(1) },
    end: LocalDateFormatter = LocalDateFormatter.today(),
    crossinline listener: (LocalDateFormatter) -> Unit
) {
    val constraintsBuilder =
        CalendarConstraints.Builder()
            .setStart(start.timeInMillis())
            .setEnd(end.timeInMillis())
    val datePicker =
        MaterialDatePicker.Builder.datePicker()
            .setCalendarConstraints(constraintsBuilder.build())
            .setSelection(value.timeInMillis())
            .build()
    datePicker.addOnPositiveButtonClickListener { date ->
        listener(
            LocalDateFormatter.from(date).update {
                value.localDateTime.let { withHour(it.hour).withMinute(it.minute) }
            }
        )
    }
    datePicker.show(this, null)
}
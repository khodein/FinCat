package com.sergei.pokhodai.expensemanagement.uikit.field.watcher

import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.sergei.pokhodai.expensemanagement.uikit.field.TextFieldItem
import java.lang.ref.WeakReference
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormatSymbols
import java.util.Locale

internal class MoneyTextWatcher(
    editText: EditText,
    private val maxDigits: Int = 12,
    private val locale: Locale = Locale.getDefault(),
    private val state: TextFieldItem.State,
) : TextWatcher {
    private val editTextWeakReference: WeakReference<EditText> = WeakReference(editText)

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(editable: Editable) {
        val editText = editTextWeakReference.get() ?: return
        Handler(Looper.getMainLooper()).post {
            editText.removeTextChangedListener(this)
            val symbols = DecimalFormatSymbols.getInstance(locale)
            val decimalSeparator = symbols.decimalSeparator
            val groupingSeparator = symbols.groupingSeparator

            val raw = editable.toString()
            val originalSelection = editText.selectionStart.coerceIn(0, raw.length)

            val normalized = raw
                .replace(',', decimalSeparator)
                .replace('.', decimalSeparator)
            val kept = buildString {
                normalized.forEach { ch ->
                    if (ch.isDigit() || ch == decimalSeparator) append(ch)
                }
            }

            if (kept.isEmpty()) {
                val zero = BigDecimal.ZERO.setScale(2, RoundingMode.DOWN)
                state.onMoneyTextChanger?.invoke("", zero)
                state.value = ""
                editText.setText("")
                editText.setSelection(0)
                editText.addTextChangedListener(this)
                return@post
            }

            val logicalCursorIndex = normalized.take(originalSelection).count { it.isDigit() || it == decimalSeparator }

            val commaIndex = kept.indexOf(decimalSeparator)
            var integerDigits = if (commaIndex >= 0) kept.substring(0, commaIndex) else kept
            var fractionDigits = if (commaIndex >= 0 && commaIndex + 1 <= kept.lastIndex) kept.substring(commaIndex + 1) else ""

            if (integerDigits.isEmpty()) integerDigits = "0"

            if (integerDigits.length > maxDigits) {
                integerDigits = integerDigits.take(maxDigits)
            }
            if (fractionDigits.length > 2) {
                fractionDigits = fractionDigits.take(2)
            }

            integerDigits = integerDigits.trimStart('0').ifEmpty { "0" }

            val groupedInteger = integerDigits.reversed()
                .chunked(3)
                .joinToString(groupingSeparator.toString()) { it }
                .reversed()

            val formatted = if (commaIndex >= 0) {
                if (fractionDigits.isEmpty()) {
                    "$groupedInteger$decimalSeparator"
                } else {
                    "$groupedInteger$decimalSeparator$fractionDigits"
                }
            } else {
                groupedInteger
            }

            val money = try {
                val bd = if (commaIndex >= 0) {
                    BigDecimal("${integerDigits}.${fractionDigits.padEnd(2, '0')}")
                } else {
                    BigDecimal("${integerDigits}.00")
                }
                bd.setScale(2, RoundingMode.DOWN)
            } catch (e: Exception) {
                BigDecimal.ZERO.setScale(2, RoundingMode.DOWN)
            }

            var newSelection = 0
            var logicalCount = 0
            while (newSelection < formatted.length && logicalCount < logicalCursorIndex) {
                val ch = formatted[newSelection]
                if (ch.isDigit() || ch == decimalSeparator) logicalCount++
                newSelection++
            }
            newSelection = newSelection.coerceIn(0, formatted.length)

            state.onMoneyTextChanger?.invoke(formatted, money)
            state.value = formatted
            editText.setText(formatted)
            editText.setSelection(newSelection)
            editText.addTextChangedListener(this)
        }
    }
}
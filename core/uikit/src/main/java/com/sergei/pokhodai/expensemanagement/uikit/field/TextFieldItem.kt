package com.sergei.pokhodai.expensemanagement.uikit.field

import android.graphics.Color
import android.text.InputType
import android.view.inputmethod.EditorInfo
import com.sergei.pokhodai.expensemanagement.core.base.color.ColorValue
import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewRect
import com.sergei.pokhodai.expensemanagement.core.base.utils.ZERO
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import java.math.BigDecimal

class TextFieldItem {

    data class State(
        override val provideId: String,
        var value: String,
        val hint: String,
        val container: Container = Container(),
        val isMoney: Boolean = false,
        val inputType: FieldInputType = FieldInputType.TEXT,
        val imeOption: FieldImeOption = FieldImeOption.DONE,
        val line: Line = Line(),
        val isEnabled: Boolean = true,
        val error: String? = null,
        val onMoneyTextChanger: ((value: String, money: BigDecimal) -> Unit)? = null,
        val onAfterTextChanger: ((value: String) -> Unit)? = null
    ) : RecyclerState

    data class Line(
        val maxLines: Int = 1,
        val minLine: Int = 1,
        val lines: Int = 1,
        val isSingleLine: Boolean = true
    )

    data class Container(
        val paddings: ViewRect.Dp = ZERO,
        val backgroundColor: ColorValue = ColorValue.Color(Color.TRANSPARENT)
    )

    enum class FieldInputType(val type: Int) {
        TEXT(InputType.TYPE_CLASS_TEXT),
        DECIMAL(InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL),
        MULTILINE(InputType.TYPE_TEXT_FLAG_MULTI_LINE)
    }

    enum class FieldImeOption(val optionType: Int) {
        DONE(EditorInfo.IME_ACTION_DONE),
        ENTER(EditorInfo.IME_FLAG_NO_ENTER_ACTION),
    }
}
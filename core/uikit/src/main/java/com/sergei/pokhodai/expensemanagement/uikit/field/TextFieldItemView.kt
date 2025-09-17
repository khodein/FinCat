package com.sergei.pokhodai.expensemanagement.uikit.field

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.text.method.DigitsKeyListener
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.EditText
import android.widget.FrameLayout
import com.sergei.pokhodai.expensemanagement.core.base.utils.applyPadding
import com.sergei.pokhodai.expensemanagement.core.base.utils.setUniqueText
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerItemView
import com.sergei.pokhodai.expensemanagement.core.uikit.databinding.ViewFieldItemBinding
import com.sergei.pokhodai.expensemanagement.uikit.field.watcher.MoneyTextWatcher
import com.sergei.pokhodai.expensemanagement.uikit.field.watcher.SimpleTextWatcher
import java.lang.ref.WeakReference
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormatSymbols
import java.util.Locale

class TextFieldItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), RecyclerItemView<TextFieldItem.State> {
    private var state: TextFieldItem.State? = null
    private val binding = ViewFieldItemBinding.inflate(LayoutInflater.from(context), this)
    private var textWatcher: TextWatcher? = null

    init {
        layoutParams = LayoutParams(
            MATCH_PARENT,
            WRAP_CONTENT,
        )
    }

    override fun bindState(state: TextFieldItem.State) {
        this.state = state

        bindContainer(state.container)

        bindImeOption(state.imeOption)
        bindInputType(state.inputType)
        if (state.isMoney) {
            binding.fieldItemText.keyListener = DigitsKeyListener.getInstance("0123456789.,")
        }

        bindLine(state.line)

        bindError(state.error)

        binding.fieldItemText.setUniqueText(state.value)

        binding.fieldItemText.hint = state.hint

        binding.fieldItemContainer.isEnabled = state.isEnabled

        if (textWatcher == null) {
            textWatcher = if (state.isMoney) {
                MoneyTextWatcher(
                    editText = binding.fieldItemText,
                    state = state,
                )
            } else {
                SimpleTextWatcher(
                    state = state
                )
            }
            binding.fieldItemText.addTextChangedListener(textWatcher)
        }
    }

    private fun bindInputType(inputType: TextFieldItem.FieldInputType) {
        binding.fieldItemText.inputType = inputType.type
    }

    private fun bindImeOption(imeOption: TextFieldItem.FieldImeOption) {
        binding.fieldItemText.imeOptions = imeOption.optionType
    }

    private fun bindError(error: String?) {
        binding.fieldItemContainer.error = error
    }

    private fun bindContainer(container: TextFieldItem.Container) {
        applyPadding(container.paddings)
        setBackgroundColor(container.backgroundColor.getColor(context))
    }

    private fun bindLine(line: TextFieldItem.Line) = with(binding.fieldItemText) {
        maxLines = line.maxLines
        minLines = line.minLine
        setLines(line.lines)
        isSingleLine = line.isSingleLine
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        binding.fieldItemText.removeTextChangedListener(textWatcher)
        textWatcher = null
    }
}
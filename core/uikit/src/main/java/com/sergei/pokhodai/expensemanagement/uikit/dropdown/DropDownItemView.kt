package com.sergei.pokhodai.expensemanagement.uikit.dropdown

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import com.sergei.pokhodai.expensemanagement.core.base.utils.applyPadding
import com.sergei.pokhodai.expensemanagement.core.base.utils.hideSoftKeyboardPost
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerItemView
import com.sergei.pokhodai.expensemanagement.core.uikit.R
import com.sergei.pokhodai.expensemanagement.core.uikit.databinding.ViewDropDownItemBinding

class DropDownItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), RecyclerItemView<DropDownItem.State> {

    private var state: DropDownItem.State? = null
    private val binding = ViewDropDownItemBinding.inflate(LayoutInflater.from(context), this)

    init {
        layoutParams = LayoutParams(
            MATCH_PARENT,
            WRAP_CONTENT,
        )

        binding.dropDownItemText.setBackgroundResource(android.R.color.transparent)

        binding.dropDownItemText.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                hideSoftKeyboardPost()
                binding.dropDownItemText.post {
                    binding.dropDownItemText.showDropDown()
                }
            }
        }

        binding.dropDownItemText.setOnItemClickListener { parent, view, position, id ->
            val item = state?.items?.getOrNull(position)
            if (item != null) {
                binding.dropDownItemText.setText(item.value)
                setAdapter()
                binding.dropDownItemText.dismissDropDown()

                state?.value = item.value
                state?.onClickItem?.invoke(item)
            }
        }
    }

    override fun bindState(state: DropDownItem.State) {
        this.state = state
        bindLine(state.line)
        bindContainer(state.container)

        binding.dropDownItemText.hint = state.hint
        binding.dropDownItemText.setText(state.value)

        setAdapter()
    }

    private fun setAdapter() {
        val adapter = ArrayAdapter(
            context,
            R.layout.view_drop_item,
            state?.items?.map { it.value }.orEmpty()
        )
        binding.dropDownItemText.setAdapter(adapter)
    }

    private fun bindContainer(container: DropDownItem.Container) {
        setBackgroundColor(container.backgroundColor.getColor(context))
        applyPadding(container.paddings)
    }

    private fun bindLine(line: DropDownItem.Line) {
        binding.dropDownItemText.setLines(line.lines)
        binding.dropDownItemText.maxLines = line.maxLine
        binding.dropDownItemText.minLines = line.minLine
    }
}
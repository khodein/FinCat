package com.sergei.pokhodai.expensemanagement.uikit.select

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import com.sergei.pokhodai.expensemanagement.core.base.utils.applyPadding
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerItemView
import com.sergei.pokhodai.expensemanagement.core.uikit.databinding.ViewSelectItemBinding

class SelectItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), RecyclerItemView<SelectItem.State> {

    private val binding = ViewSelectItemBinding.inflate(LayoutInflater.from(context), this)
    private var state: SelectItem.State? = null

    init {
        layoutParams = LayoutParams(
            MATCH_PARENT,
            WRAP_CONTENT,
        )

        binding.selectItemText.setOnClickListener {
            state?.onClick?.invoke()
        }
    }

    override fun bindState(state: SelectItem.State) {
        this.state = state
        bindContainer(state.container)
        binding.selectItemText.setText(state.value)
        binding.selectItemText.hint = state.hind
    }

    private fun bindContainer(container: SelectItem.Container) {
        applyPadding(container.paddings)
        setBackgroundColor(container.backgroundColor.getColor(context))
    }
}
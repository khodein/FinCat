package com.sergei.pokhodai.expensemanagement.uikit.kind

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.constraintlayout.widget.ConstraintLayout
import com.sergei.pokhodai.expensemanagement.core.base.R
import com.sergei.pokhodai.expensemanagement.core.base.color.ColorValue
import com.sergei.pokhodai.expensemanagement.core.base.corner.RoundMode
import com.sergei.pokhodai.expensemanagement.core.base.corner.ViewCorner
import com.sergei.pokhodai.expensemanagement.core.base.image.ImageValue
import com.sergei.pokhodai.expensemanagement.core.base.utils.applyPadding
import com.sergei.pokhodai.expensemanagement.core.base.utils.bindStateOptional
import com.sergei.pokhodai.expensemanagement.core.base.utils.clearRipple
import com.sergei.pokhodai.expensemanagement.core.base.utils.dp
import com.sergei.pokhodai.expensemanagement.core.base.utils.load
import com.sergei.pokhodai.expensemanagement.core.base.utils.makeRippleDrawable
import com.sergei.pokhodai.expensemanagement.core.base.utils.resolveToLayoutParams
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerItemView
import com.sergei.pokhodai.expensemanagement.core.uikit.databinding.ViewCategoryKindItemBinding

class CategoryKindItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), RecyclerItemView<CategoryKindItem.State> {
    private val binding = ViewCategoryKindItemBinding.inflate(LayoutInflater.from(context), this)
    private var state: CategoryKindItem.State? = null

    init {
        layoutParams = LayoutParams(
            WRAP_CONTENT,
            WRAP_CONTENT,
        )

        setOnClickListener { state?.let { it.onClick?.invoke(it) } }
    }

    override fun bindState(state: CategoryKindItem.State) {
        this.state = state
        bindContainer(state.container)
        bindName(state.name)
        bindIcon(
            icon = state.icon,
            color = state.color
        )
        setRipple(state)
    }

    private fun setRipple(state: CategoryKindItem.State) {
        if (state.onClick != null) {
            makeRippleDrawable(rippleColorValue = ColorValue.Res(R.color.grey_100))
        } else {
            clearRipple()
        }
    }

    private fun bindIcon(
        icon: ImageValue?,
        color: ColorValue
    ) {
        ViewCorner.Simple(
            radius = 20.dp,
            roundMode = RoundMode.ALL
        ).resolve(
            view = binding.viewCategoryKindContainer,
            backgroundColorInt = color.getColor(context)
        )

        binding.viewCategoryKindIcon.load(icon)
    }

    private fun bindName(name: String?) = with(binding.viewCategoryKindName) {
        bindStateOptional(
            state = name,
            binder = { text = it }
        )
    }

    private fun bindContainer(container: CategoryKindItem.Container) {
        resolveToLayoutParams(
            width = container.width,
            height = container.height,
        )
        setBackgroundColor(container.backgroundColor.getColor(context))
        applyPadding(container.padding)
    }
}
package com.sergei.pokhodai.expensemanagement.uikit.tag

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.constraintlayout.widget.ConstraintLayout
import com.sergei.pokhodai.expensemanagement.core.base.color.ColorValue
import com.sergei.pokhodai.expensemanagement.core.base.corner.BorderType
import com.sergei.pokhodai.expensemanagement.core.base.corner.RoundMode
import com.sergei.pokhodai.expensemanagement.core.base.corner.ViewCorner
import com.sergei.pokhodai.expensemanagement.core.base.image.ImageColorFilterValue
import com.sergei.pokhodai.expensemanagement.core.base.image.ImageValue
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_8_8_8_8
import com.sergei.pokhodai.expensemanagement.core.base.utils.applyPadding
import com.sergei.pokhodai.expensemanagement.core.base.utils.bindStateOptional
import com.sergei.pokhodai.expensemanagement.core.base.utils.dp
import com.sergei.pokhodai.expensemanagement.core.base.utils.getColor
import com.sergei.pokhodai.expensemanagement.core.base.utils.load
import com.sergei.pokhodai.expensemanagement.core.base.utils.resolveToLayoutParams
import com.sergei.pokhodai.expensemanagement.core.base.utils.setColorFilter
import com.sergei.pokhodai.expensemanagement.core.base.utils.setOnDebounceClick
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerItemView
import com.sergei.pokhodai.expensemanagement.core.uikit.databinding.ViewTagItemBinding
import com.sergei.pokhodai.expensemanagement.core.base.R as baseR

class TagItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr), RecyclerItemView<TagItem.State> {

    private val binding = ViewTagItemBinding.inflate(LayoutInflater.from(context), this)

    private var state: TagItem.State? = null

    init {
        layoutParams = LayoutParams(
            WRAP_CONTENT,
            WRAP_CONTENT,
        )

        applyPadding(P_8_8_8_8)

        setOnDebounceClick {
            state?.let {
                if (it.isEnabled) {
                    it.onClick?.invoke(it)
                }
            }
        }
    }

    override fun bindState(state: TagItem.State) {
        this.state = state
        resolveToLayoutParams(state.container.width, state.container.height)

        bindIcon(state.icon)
        bindText(state.value)

        when {
            !state.isEnabled -> {
                binding.tagItemValue.setTextColor(getColor(baseR.color.grey_500))
                binding.tagItemIcon.setColorFilter(ImageColorFilterValue.Disabled())
                ViewCorner.Simple(
                    radius = RADIUS,
                    roundMode = RoundMode.ALL
                ).resolve(
                    view = this,
                    backgroundColorInt = getColor(baseR.color.grey_300)
                )
            }

            state.isActive -> {
                binding.tagItemValue.setTextColor(getColor(baseR.color.grey_900))
                binding.tagItemIcon.setColorFilter(
                    ImageColorFilterValue.Tint(
                        tintColor = ColorValue.Res(
                            baseR.color.blue_600
                        )
                    )
                )
                ViewCorner.Border(
                    radius = RADIUS,
                    roundMode = RoundMode.ALL,
                    borderType = BorderType.Simple(
                        strokeWidth = STROKE_WIDTH,
                        strokeColor = getColor(baseR.color.blue_600)
                    )
                ).resolve(
                    view = this,
                    backgroundColorInt = getColor(baseR.color.blue_50)
                )
            }

            else -> {
                binding.tagItemValue.setTextColor(getColor(baseR.color.grey_800))
                binding.tagItemIcon.setColorFilter(
                    ImageColorFilterValue.Tint(tintColor = ColorValue.Res(baseR.color.grey_600))
                )
                ViewCorner.Border(
                    radius = RADIUS,
                    roundMode = RoundMode.ALL,
                    borderType = BorderType.Simple(
                        strokeWidth = STROKE_WIDTH,
                        strokeColor = getColor(baseR.color.grey_400)
                    )
                ).resolve(
                    view = this,
                    backgroundColorInt = getColor(baseR.color.background)
                )
            }
        }
    }

    private fun bindText(value: String) {
        binding.tagItemValue.text = value
    }

    private fun bindIcon(
        value: ImageValue?,
    ) {
        val leftPadding = if (value == null) {
            0.dp
        } else {
            8.dp
        }
        binding.tagItemValue.applyPadding(left = leftPadding)
        binding.tagItemIcon.bindStateOptional(
            state = value,
            binder = {
                binding.tagItemIcon.load(it)
            }
        )
    }

    private companion object {
        val RADIUS = 8.dp
        val STROKE_WIDTH = 1.dp
    }
}
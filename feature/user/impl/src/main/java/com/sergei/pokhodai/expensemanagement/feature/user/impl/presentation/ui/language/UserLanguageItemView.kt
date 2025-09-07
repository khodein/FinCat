package com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.ui.language

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import com.sergei.pokhodai.expensemanagement.core.base.corner.BorderType
import com.sergei.pokhodai.expensemanagement.core.base.corner.RoundMode
import com.sergei.pokhodai.expensemanagement.core.base.R as baseR
import com.sergei.pokhodai.expensemanagement.core.base.corner.ViewCorner
import com.sergei.pokhodai.expensemanagement.core.base.utils.applyPadding
import com.sergei.pokhodai.expensemanagement.core.base.utils.dp
import com.sergei.pokhodai.expensemanagement.core.base.utils.getColor
import com.sergei.pokhodai.expensemanagement.core.base.utils.load
import com.sergei.pokhodai.expensemanagement.core.base.utils.setOnDebounceClick
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerItemView
import com.sergei.pokhodai.expensemanagement.feature.user.impl.databinding.ViewUserLanguageItemBinding

internal class UserLanguageItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), RecyclerItemView<UserLanguageItem.State> {

    private val binging = ViewUserLanguageItemBinding.inflate(LayoutInflater.from(context), this)
    private var state: UserLanguageItem.State? = null

    init {
        layoutParams = LayoutParams(
            MATCH_PARENT,
            WRAP_CONTENT,
        )

        setOnDebounceClick {
            state?.let { it.onClick?.invoke(it) }
        }
    }

    override fun bindState(state: UserLanguageItem.State) {
        this.state = state
        applyPadding(state.container.padding)
        binging.userLanguageName.text = state.name
        binging.userLanguageIcon.load(state.icon)

        if (state.isChecked) {
            ViewCorner.Border(
                radius = 8.dp,
                roundMode = RoundMode.ALL,
                borderType = BorderType.Simple(
                    strokeWidth = 1.dp,
                    strokeColor = getColor(baseR.color.blue_600)
                )
            ).resolve(
                view = binging.userLanguageContainer,
                backgroundColorInt = getColor(baseR.color.blue_50)
            )
        } else {
            ViewCorner.Default()
                .resolve(
                    view = binging.userLanguageContainer,
                    backgroundColorInt = getColor(baseR.color.background)
                )
        }
    }
}
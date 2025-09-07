package com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.ui.avatar

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.constraintlayout.widget.ConstraintLayout
import com.sergei.pokhodai.expensemanagement.core.base.R as baseR
import com.sergei.pokhodai.expensemanagement.core.base.corner.BorderType
import com.sergei.pokhodai.expensemanagement.core.base.corner.RoundMode
import com.sergei.pokhodai.expensemanagement.core.base.corner.ViewCorner
import com.sergei.pokhodai.expensemanagement.core.base.utils.applyPadding
import com.sergei.pokhodai.expensemanagement.core.base.utils.dp
import com.sergei.pokhodai.expensemanagement.core.base.utils.getColor
import com.sergei.pokhodai.expensemanagement.core.base.utils.load
import com.sergei.pokhodai.expensemanagement.core.base.utils.setOnDebounceClick
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerItemView
import com.sergei.pokhodai.expensemanagement.feature.user.impl.databinding.ViewUserAvatarItemBinding

internal class UserAvatarItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), RecyclerItemView<UserAvatarItem.State> {

    private val binding = ViewUserAvatarItemBinding.inflate(LayoutInflater.from(context), this)
    private var state: UserAvatarItem.State? = null

    init {
        layoutParams = LayoutParams(
            MATCH_PARENT,
            WRAP_CONTENT,
        )

        ViewCorner.Border(
            radius = 60.dp,
            roundMode = RoundMode.ALL,
            borderType = BorderType.Simple(
                strokeColor = getColor(baseR.color.grey_300),
                strokeWidth = 2.dp
            )
        ).resolve(
            view = binding.userAvatarContainer,
            backgroundColorInt = Color.TRANSPARENT
        )

        binding.userAvatarEdit.setOnDebounceClick {
            state?.onClickEdit?.invoke()
        }
    }

    override fun bindState(state: UserAvatarItem.State) {
        this.state = state
        applyPadding(state.container.paddings)
        binding.userAvatarEdit.text = state.editText
        binding.userAvatarImg.load(state.art)
    }
}
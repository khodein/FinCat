package com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.ui.avatar_list

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.constraintlayout.widget.ConstraintLayout
import com.sergei.pokhodai.expensemanagement.core.base.color.ColorValue
import com.sergei.pokhodai.expensemanagement.core.base.R as baseR
import com.sergei.pokhodai.expensemanagement.core.base.corner.BorderType
import com.sergei.pokhodai.expensemanagement.core.base.corner.RoundMode
import com.sergei.pokhodai.expensemanagement.core.base.corner.ViewCorner
import com.sergei.pokhodai.expensemanagement.core.base.utils.dp
import com.sergei.pokhodai.expensemanagement.core.base.utils.getColor
import com.sergei.pokhodai.expensemanagement.core.base.utils.load
import com.sergei.pokhodai.expensemanagement.core.base.utils.makeRippleDrawable
import com.sergei.pokhodai.expensemanagement.core.base.utils.setOnDebounceClick
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerItemView
import com.sergei.pokhodai.expensemanagement.feature.user.impl.databinding.ViewUserListAvatarItemBinding

internal class UserAvatarListItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), RecyclerItemView<UserAvatarListItem.State> {

    private val binging = ViewUserListAvatarItemBinding.inflate(LayoutInflater.from(context), this)
    private var state: UserAvatarListItem.State? = null

    init {
        layoutParams = LayoutParams(
            MATCH_PARENT,
            80.dp,
        )

        makeRippleDrawable(rippleColorValue = ColorValue.Res(baseR.color.blue_50))

        setOnDebounceClick {
            state?.let { it.onClick?.invoke(it) }
        }
    }

    override fun bindState(state: UserAvatarListItem.State) {
        this.state = state
        if (state.isChecked) {
            ViewCorner.Border(
                radius = 36.dp,
                roundMode = RoundMode.ALL,
                borderType = BorderType.Simple(
                    strokeColor = getColor(baseR.color.grey_300),
                    strokeWidth = 2.dp
                )
            ).resolve(
                view = binging.userListAvatarContainer,
                backgroundColorInt = Color.TRANSPARENT
            )
        } else {
            ViewCorner.Default().resolve(
                view = binging.userListAvatarContainer,
                backgroundColorInt = Color.TRANSPARENT
            )
        }
        binging.userListAvatarArt.load(state.art)
    }
}
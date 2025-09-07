package com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.ui.user

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.sergei.pokhodai.expensemanagement.core.base.color.ColorValue
import com.sergei.pokhodai.expensemanagement.core.base.R as baseR
import com.sergei.pokhodai.expensemanagement.core.base.corner.BorderType
import com.sergei.pokhodai.expensemanagement.core.base.corner.RoundMode
import com.sergei.pokhodai.expensemanagement.core.base.corner.ViewCorner
import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewDimension
import com.sergei.pokhodai.expensemanagement.core.base.utils.applyPadding
import com.sergei.pokhodai.expensemanagement.core.base.utils.bindStateOptional
import com.sergei.pokhodai.expensemanagement.core.base.utils.dp
import com.sergei.pokhodai.expensemanagement.core.base.utils.getColor
import com.sergei.pokhodai.expensemanagement.core.base.utils.load
import com.sergei.pokhodai.expensemanagement.core.base.utils.makeRippleDrawable
import com.sergei.pokhodai.expensemanagement.core.base.utils.setOnDebounceClick
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerItemView
import com.sergei.pokhodai.expensemanagement.feature.user.impl.databinding.ViewUserListItemBinding

internal class UserListItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), RecyclerItemView<UserListItem.State> {

    private val binding = ViewUserListItemBinding.inflate(LayoutInflater.from(context), this)
    private var state: UserListItem.State? = null

    init {
        layoutParams = LayoutParams(
            MATCH_PARENT,
            WRAP_CONTENT
        )

        binding.userListItemClick.setOnDebounceClick {
            state?.let { it.onClick?.invoke(it) }
        }

        binding.userListItemEdit.setOnDebounceClick {
            state?.let { it.onClickEdit?.invoke(it) }
        }

        val rippleCorner = ViewDimension.Dp(8)

        binding.userListItemEdit.makeRippleDrawable(
            rippleColorValue = ColorValue.Res(baseR.color.grey_300),
            topLeftCornerRadius = rippleCorner,
            topRightCornerRadius = rippleCorner,
            bottomLeftCornerRadius = rippleCorner,
            bottomRightCornerRadius = rippleCorner
        )

        binding.userListItemClick.makeRippleDrawable(
            rippleColorValue = ColorValue.Res(baseR.color.grey_300),
            topLeftCornerRadius = rippleCorner,
            topRightCornerRadius = rippleCorner,
            bottomLeftCornerRadius = rippleCorner,
            bottomRightCornerRadius = rippleCorner
        )

        binding.userListItemDelete.setOnDebounceClick {
            state?.let { it.onClickDelete?.invoke(it) }
        }

        ViewCorner.Border(
            radius = 25.dp,
            roundMode = RoundMode.ALL,
            borderType = BorderType.Simple(
                strokeColor = getColor(baseR.color.grey_300),
                strokeWidth = 1.dp
            )
        ).resolve(
            view = binding.userListItemAvatarContainer,
            backgroundColorInt = Color.TRANSPARENT
        )
    }

    override fun bindState(state: UserListItem.State) {
        this.state = state
        applyPadding(state.container.paddings)
        binding.userListItemName.text = state.name
        binding.userListItemCurrency.bindStateOptional(
            state = state.currency,
            binder = {
                binding.userListItemCurrency.text = it
            }
        )
        binding.userListItemEmail.bindStateOptional(
            state = state.email,
            binder = {
                binding.userListItemEmail.text = it
            }
        )
        binding.userListItemDelete.isVisible = state.onClickDelete != null
        binding.userListAvatarArt.load(state.art)
        if (state.isChecked) {
            ViewCorner.Border(
                radius = 8.dp,
                roundMode = RoundMode.ALL,
                borderType = BorderType.Simple(
                    strokeColor = getColor(baseR.color.blue_600),
                    strokeWidth = 1.dp
                )
            ).resolve(
                view = binding.userListItemContainerContent,
                backgroundColorInt = Color.TRANSPARENT
            )
        } else {
            ViewCorner.Default().resolve(
                view = binding.userListItemContainerContent,
                backgroundColorInt = Color.TRANSPARENT
            )
        }
    }
}
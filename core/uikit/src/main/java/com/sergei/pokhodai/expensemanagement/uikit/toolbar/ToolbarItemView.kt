package com.sergei.pokhodai.expensemanagement.uikit.toolbar

import android.content.Context
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.core.view.ViewCompat
import com.google.android.material.appbar.MaterialToolbar
import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewDimension
import com.sergei.pokhodai.expensemanagement.core.base.image.ImageValue
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_16_0_16_0
import com.sergei.pokhodai.expensemanagement.core.base.utils.ZERO
import com.sergei.pokhodai.expensemanagement.core.base.utils.applyPadding
import com.sergei.pokhodai.expensemanagement.core.base.utils.getColor
import com.sergei.pokhodai.expensemanagement.core.base.utils.getColorStateList
import com.sergei.pokhodai.expensemanagement.core.base.utils.getDrawable
import com.sergei.pokhodai.expensemanagement.core.base.utils.getToolBarHeight
import com.sergei.pokhodai.expensemanagement.core.base.utils.resolveToLayoutParams
import com.sergei.pokhodai.expensemanagement.core.uikit.R
import com.sergei.pokhodai.expensemanagement.core.base.R as baseR

class ToolbarItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialToolbar(context, attrs, defStyleAttr), ToolbarItem.View {
    private var state: ToolbarItem.State? = null
    private val toolbarHeight by lazy { context.getToolBarHeight() }
    private val toolbarWidth by lazy { ViewDimension.MatchParent.value }

    init {
        layoutParams = LayoutParams(
            MATCH_PARENT,
            WRAP_CONTENT,
        )

        setNavigationOnClickListener { state?.onClickNavigation?.invoke() }
    }

    override fun bindState(state: ToolbarItem.State) {
        this.state = state
        if (state.navigateIcon == null) {
            applyPadding(P_16_0_16_0)
        } else {
            applyPadding(ZERO)
        }

        resolveToLayoutParams(
            width = toolbarWidth,
            height = toolbarHeight,
        )
        setNavigateIcon(state.navigateIcon)
        setBackgroundColor(state.backgroundColor.getColor(context))
        setTitle(state.title)
        setMenu(state.isDelete)
    }

    private fun setMenu(isDelete: Boolean) {
        menu.clear()
        if (isDelete) {
            menu.add(
                Menu.NONE,
                generateViewId(),
                0,
                ""
            ).apply {
                setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
                setIcon(R.drawable.ic_delete)

                iconTintList = getColorStateList(baseR.color.red_600)
                setOnMenuItemClickListener {
                    state?.onClickDelete?.invoke()
                    true
                }
            }
        }
    }

    private fun setTitle(title: ToolbarItem.Title) {
        this.title = title.value
        this.setTitleTextColor(title.textColor.getColor(context))
        this.setTitleTextAppearance(context, title.styleRes)
    }

    private fun setNavigateIcon(icon: ToolbarItem.NavigateIcon?) {
        navigationIcon = when(icon?.icon) {
            is ImageValue.Res -> getDrawable(icon.icon.value)
            else -> null
        }
        setNavigationIconTint(icon?.color?.getColor(context) ?: getColor(android.R.color.transparent))
    }
}
package com.sergei.pokhodai.expensemanagement.core.base.dimension

import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.sergei.pokhodai.expensemanagement.core.base.utils.dp

sealed interface ViewDimension {
    val value: Int

    data class Px(override val value: Int) : ViewDimension

    data class Dp(val dpValue: Int) : ViewDimension {
        override val value: Int = dpValue.dp
    }

    object WrapContent : ViewDimension {
        override val value: Int = ViewGroup.LayoutParams.WRAP_CONTENT
    }

    object MatchParent : ViewDimension {
        override val value: Int = ViewGroup.LayoutParams.MATCH_PARENT
    }

    object MatchConstraint : ViewDimension {
        override val value: Int = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT
    }
}
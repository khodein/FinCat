package com.sergei.pokhodai.expensemanagement.utils

import android.view.ViewGroup
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.widget.ConstraintLayout

sealed interface ViewDimension {
    val value: Int

    data class Px(override val value: Int) : ViewDimension

    data class Dp(val dpValue: Int) : ViewDimension {
        override val value: Int = dpValue.dp.toPx
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
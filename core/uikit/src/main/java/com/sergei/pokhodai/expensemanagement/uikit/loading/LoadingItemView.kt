package com.sergei.pokhodai.expensemanagement.uikit.loading

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout.LayoutParams
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.sergei.pokhodai.expensemanagement.core.base.R as baseR
import com.sergei.pokhodai.expensemanagement.core.base.utils.dp
import com.sergei.pokhodai.expensemanagement.core.base.utils.getColor

class LoadingItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CircularProgressIndicator(context, attrs, defStyleAttr) {

    init {
        isIndeterminate = true
        setIndicatorColor(getColor(baseR.color.blue_400))
        trackColor = getColor(baseR.color.blue_50)
        trackThickness = 6.dp
    }
}
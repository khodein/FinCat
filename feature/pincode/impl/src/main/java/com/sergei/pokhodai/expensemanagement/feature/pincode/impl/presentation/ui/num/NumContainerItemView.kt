package com.sergei.pokhodai.expensemanagement.feature.pincode.impl.presentation.ui.num

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.constraintlayout.widget.ConstraintLayout
import com.sergei.pokhodai.expensemanagement.feature.pincode.impl.databinding.ViewNumContainerItemBinding

internal class NumContainerItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), NumContainerItem.View {

    private val binding = ViewNumContainerItemBinding.inflate(LayoutInflater.from(context), this)

    init {
        layoutParams = LayoutParams(
            MATCH_PARENT,
            WRAP_CONTENT
        )
    }

    override fun bindState(state: NumContainerItem.State) {

    }
}
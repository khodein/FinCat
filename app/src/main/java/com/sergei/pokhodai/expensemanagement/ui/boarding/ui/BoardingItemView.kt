package com.sergei.pokhodai.expensemanagement.ui.boarding.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.sergei.pokhodai.expensemanagement.databinding.ViewBoardingItemBinding

class BoardingItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), BoardingItem.View {

    private val binding = ViewBoardingItemBinding.inflate(LayoutInflater.from(context), this)

    init {
        layoutParams = LayoutParams(
            MATCH_PARENT,
            WRAP_CONTENT
        )

        orientation = VERTICAL
    }

    override fun bindState(state: BoardingItem.State) {
        binding.boardingItemImg.setImageResource(state.imageRes)
        binding.boardingItemTitle.text = state.title
        binding.boardingItemCaption.text = state.caption
    }
}
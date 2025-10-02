package com.sergei.pokhodai.expensemanagement.feature.faq.impl.presentation.question.state

import com.sergei.pokhodai.expensemanagement.uikit.button.ButtonItem

data class QuestionBottomState(
    val topButtonState: ButtonItem.State,
    val bottomButtonState: ButtonItem.State
)
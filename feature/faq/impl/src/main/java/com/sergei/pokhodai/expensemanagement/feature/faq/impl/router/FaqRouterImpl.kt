package com.sergei.pokhodai.expensemanagement.feature.faq.impl.router

import com.sergei.pokhodai.expensemanagement.core.router.Router
import com.sergei.pokhodai.expensemanagement.core.router.animation.NavAnimation
import com.sergei.pokhodai.expensemanagement.feature.faq.api.FaqRouter
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.presentation.contract.FaqContract
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.presentation.question.contract.QuestionContract

internal class FaqRouterImpl(
    private val router: Router,
) : FaqRouter {

    override fun goToFaq() {
        router.navigate(
            contract = FaqContract(),
            navAnimation = NavAnimation.FADE
        )
    }

    override fun goToQuestion() {
        router.navigate(
            contract = QuestionContract(),
            navAnimation = NavAnimation.FADE
        )
    }
}
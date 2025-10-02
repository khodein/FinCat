package com.sergei.pokhodai.expensemanagement.feature.faq.impl.router

import com.sergei.pokhodai.expensemanagement.core.router.destination.RouteDestination
import com.sergei.pokhodai.expensemanagement.core.router.provider.RouteProvider
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.presentation.FaqFragment
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.presentation.contract.FaqContract
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.presentation.question.QuestionFragment
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.presentation.question.contract.QuestionContract

internal class FaqRouterProviderImpl : RouteProvider {

    override fun getDestination(): List<RouteDestination> {
        return listOf(
            RouteDestination(
                type = RouteDestination.Type.FragmentType(
                    contract = FaqContract::class,
                    clazz = FaqFragment::class
                )
            ),
            RouteDestination(
                type = RouteDestination.Type.FragmentType(
                    contract = QuestionContract::class,
                    clazz = QuestionFragment::class
                )
            )
        )
    }
}
package com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl.router

import com.sergei.pokhodai.expensemanagement.core.router.destination.RouteDestination
import com.sergei.pokhodai.expensemanagement.core.router.provider.RouteProvider
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl.presentation.EventEditorFragment
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl.router.contract.EventEditorRouterContract

internal class EventEditorRouterProviderImpl : RouteProvider {

    override fun getDestination(): List<RouteDestination> {
        return listOf(
            RouteDestination(
                type = RouteDestination.Type.FragmentType(
                    clazz = EventEditorFragment::class,
                    contract = EventEditorRouterContract::class
                )
            )
        )
    }
}
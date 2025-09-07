package com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl.router

import com.sergei.pokhodai.expensemanagement.core.router.Router
import com.sergei.pokhodai.expensemanagement.core.router.animation.NavAnimation
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.api.router.EventEditorRouter
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl.router.contract.EventEditorRouterContract

internal class EventEditorRouterImpl(
    private val router: Router,
) : EventEditorRouter {

    override fun goToEventEditor(eventId: Long?) {
        router.navigate(
            contract = EventEditorRouterContract(eventId = eventId),
            navAnimation = NavAnimation.FADE
        )
    }
}
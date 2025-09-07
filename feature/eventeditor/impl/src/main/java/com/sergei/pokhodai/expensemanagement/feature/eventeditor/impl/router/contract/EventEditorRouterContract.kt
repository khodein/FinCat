package com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl.router.contract

import com.sergei.pokhodai.expensemanagement.core.router.contract.RouterContract
import kotlinx.serialization.Serializable

@Serializable
internal data class EventEditorRouterContract(
    val eventId: Long?
) : RouterContract
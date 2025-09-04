package com.sergei.pokhodai.expensemanagement.core.router.destination

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.sergei.pokhodai.expensemanagement.core.router.contract.RouterContract
import kotlin.reflect.KClass

class RouteDestination(
    val type: Type,
) {
    sealed interface Type {
        val contract: KClass<out RouterContract>

        data class FragmentType(
            val clazz: KClass<out Fragment>,
            override val contract: KClass<out RouterContract>,
        ) : Type

        data class DialogType(
            val clazz: KClass<out DialogFragment>,
            override val contract: KClass<out RouterContract>,
        ) : Type
    }
}
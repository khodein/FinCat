package com.android.pokhodai.expensemanagement.base.utils

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.android.pokhodai.expensemanagement.R

fun Fragment.getMainController() = requireActivity().findNavController(R.id.rootContainer)

inline fun <reified T> Fragment.getActivityService(block: T.() -> Unit) {
    if (requireActivity() is T)
        block(requireActivity() as T)
    else
        throw Exception("${requireActivity().javaClass} не реализует ${T::class.java}")
}

inline fun <reified T> Fragment.getApplicationService(block: T.() -> Unit) =
    requireContext().getApplicationService(block)

inline fun <reified T> Context.getApplicationService(block: T.() -> Unit) {
    if (applicationContext is T) {
        block(applicationContext as T)
    }
}
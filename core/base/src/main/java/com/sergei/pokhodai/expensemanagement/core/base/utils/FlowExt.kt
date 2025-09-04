package com.sergei.pokhodai.expensemanagement.core.base.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

inline fun <T> Flow<T>.observe(
    lifecycleOwner: LifecycleOwner,
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: (T) -> Unit
): Job {
    return lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(lifecycleState) {
            collect {
                block(it)
            }
        }
    }
}

inline fun <T> Flow<T>.observe(fragment: Fragment, crossinline block: (T) -> Unit): Job {
    return this.observe(
        lifecycleOwner = fragment.viewLifecycleOwner,
        block = block,
    )
}

inline fun <T> Flow<T>.observe(activity: FragmentActivity, crossinline block: (T) -> Unit): Job {
    return activity.lifecycleScope.launch {
        activity.repeatOnLifecycle(Lifecycle.State.STARTED) {
            collect { block(it) }
        }
    }
}
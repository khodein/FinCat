package com.sergei.pokhodai.expensemanagement.core.eventbus.api

interface EventBus {

    fun <T> subscribe(key: String, event: Class<T>, callback: (T) -> Unit)
    fun unsubscribe(key: String)
    fun <T> push(event: T, key: String)
}
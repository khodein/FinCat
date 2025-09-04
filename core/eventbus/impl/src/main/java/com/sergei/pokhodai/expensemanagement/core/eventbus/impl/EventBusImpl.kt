package com.sergei.pokhodai.expensemanagement.core.eventbus.impl

import com.sergei.pokhodai.expensemanagement.core.eventbus.api.EventBus

internal class EventBusImpl : EventBus {
    private val _listeners = mutableListOf<Listener<*>>()

    override fun <T> subscribe(key: String, event: Class<T>, callback: (T) -> Unit) {
        _listeners.add(Listener(key, event, callback))
    }

    override fun unsubscribe(key: String) {
        _listeners.removeAll { it.key == key }
    }

    override fun <T> push(event: T, key: String) {
        _listeners.forEach { listener ->
            try {
                if (listener.event.isInstance(event) && key == listener.key) {
                    (listener as? Listener<T>)?.callback(event)
                }
            } catch (ex: Exception) { }
        }
    }

    private class Listener<T>(
        val key: String,
        val event: Class<T>,
        val callback: (T) -> Unit
    )
}
package com.sergei.pokhodai.expensemanagement.core.support.impl.manager

import android.content.Context
import android.content.res.Configuration
import androidx.annotation.ColorRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.sergei.pokhodai.expensemanagement.core.support.api.manager.ResManager

internal class ResManagerImpl(private val context: Context) : ResManager {

    override fun getColor(@ColorRes colorResId: Int): Int {
        return ContextCompat.getColor(context, colorResId)
    }

    override fun getString(@StringRes stringResId: Int): String {
        return getLocaleStringResource(resourceId = stringResId)
    }

    override fun getString(@StringRes stringResId: Int, vararg args: Any?): String {
        return getLocaleStringResource(stringResId, *args)
    }

    override fun getQuantityString(@PluralsRes stringResId: Int, quantity: Int): String {
        return getQuantityLocaleStringResource(stringResId, quantity)
    }

    override fun getQuantityString(@PluralsRes stringResId: Int, quantity: Int, vararg args: Any?): String {
        return getQuantityLocaleStringResource(stringResId, quantity, *args)
    }

    private fun getLocaleStringResource(
        resourceId: Int,
    ): String {
        val config = Configuration(context.resources.configuration)
        config.setLocale(AppCompatDelegate.getApplicationLocales().get(0))
        return context.createConfigurationContext(config).getString(resourceId)
    }

    private fun getLocaleStringResource(
        resourceId: Int,
        vararg args: Any?
    ): String {
        val config = Configuration(context.resources.configuration)
        config.setLocale(AppCompatDelegate.getApplicationLocales().get(0))
        return context.createConfigurationContext(config).getString(resourceId, *args)
    }

    private fun getQuantityLocaleStringResource(
        resourceId: Int,
        quantity: Int
    ): String {
        val config = Configuration(context.resources.configuration)
        config.setLocale(AppCompatDelegate.getApplicationLocales().get(0))
        return context.createConfigurationContext(config).resources.getQuantityString(resourceId, quantity)
    }

    private fun getQuantityLocaleStringResource(
        resourceId: Int,
        quantity: Int,
        vararg args: Any?
    ): String {
        val config = Configuration(context.resources.configuration)
        config.setLocale(AppCompatDelegate.getApplicationLocales().get(0))
        return context.createConfigurationContext(config).resources.getQuantityString(resourceId, quantity)
    }
}
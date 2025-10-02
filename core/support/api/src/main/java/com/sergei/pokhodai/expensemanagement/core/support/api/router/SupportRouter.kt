package com.sergei.pokhodai.expensemanagement.core.support.api.router

import android.content.Context
import androidx.fragment.app.Fragment
import com.sergei.pokhodai.expensemanagement.core.support.api.model.alert.AlertRouterModel
import com.sergei.pokhodai.expensemanagement.core.support.api.model.calendar.CalendarRouterModel
import com.sergei.pokhodai.expensemanagement.core.support.api.model.color.ColorRouterModel

interface SupportRouter {
    fun setProvider(provider: Provider)
    fun showCalendar(model: CalendarRouterModel)
    fun showColorPicker(model: ColorRouterModel)
    fun showSnackBar(message: String)
    fun showAlert(model: AlertRouterModel)
    fun exitApp()
    fun hideKeyboard()
    interface Provider {
        fun getSupportRouterNavHostFragment(): Fragment
        fun getSupportActivityContext(): Context
    }
}
package com.sergei.pokhodai.expensemanagement.core.router.support

import android.content.Context
import androidx.fragment.app.Fragment
import com.sergei.pokhodai.expensemanagement.core.router.support.alert.AlertRouterModel
import com.sergei.pokhodai.expensemanagement.core.router.support.calendar.CalendarRouterModel
import com.sergei.pokhodai.expensemanagement.core.router.support.color.ColorRouterModel

interface SupportRouter {
    fun setProvider(provider: Provider)
    fun showCalendar(model: CalendarRouterModel)
    fun showColorPicker(model: ColorRouterModel)
    fun showSnackBar(message: String)
    fun showAlert(model: AlertRouterModel)
    fun hideKeyboard()
    interface Provider {
        fun getSupportRouterNavHostFragment(): Fragment
        fun getSupportActivityContext(): Context
    }
}
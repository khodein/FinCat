package com.sergei.pokhodai.expensemanagement.core.support.impl.router

import android.os.Handler
import android.os.Looper
import com.sergei.pokhodai.expensemanagement.core.base.utils.hideKeyboard
import com.sergei.pokhodai.expensemanagement.core.base.utils.showAlert
import com.sergei.pokhodai.expensemanagement.core.base.utils.showDatePickerDialog
import com.sergei.pokhodai.expensemanagement.core.support.api.router.SupportRouter
import com.sergei.pokhodai.expensemanagement.core.support.api.model.alert.AlertRouterModel
import com.sergei.pokhodai.expensemanagement.core.support.api.model.calendar.CalendarRouterModel
import com.sergei.pokhodai.expensemanagement.core.support.api.model.color.ColorRouterModel
import com.sergei.pokhodai.expensemanagement.core.support.impl.snackbar.onShowSnackBar
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import java.lang.ref.WeakReference

internal class SupportRouterImpl() : SupportRouter {
    private val handler = Handler(Looper.getMainLooper())
    private var _provider: WeakReference<SupportRouter.Provider>? = null
    private val provider: SupportRouter.Provider?
        get() = _provider?.get()

    override fun setProvider(provider: SupportRouter.Provider) {
        this._provider = WeakReference(provider)
    }

    override fun showCalendar(model: CalendarRouterModel) {
        val navHostFragment = provider?.getSupportRouterNavHostFragment() ?: return
        val showCalendarRunnable = Runnable {
            navHostFragment.childFragmentManager.showDatePickerDialog(
                value = model.value,
                start = model.start,
                end = model.end,
                listener = model.onClick
            )
        }
        if (Looper.myLooper() == Looper.getMainLooper()) {
            showCalendarRunnable.run()
        } else {
            handler.post(showCalendarRunnable)
        }
    }

    override fun showAlert(model: AlertRouterModel) {
        val navHostFragment = provider?.getSupportRouterNavHostFragment() ?: return
        val showCalendarRunnable = Runnable {
            navHostFragment.showAlert(
                text = model.text,
                negativeBtnText = model.negativeBtnText,
                positiveBtnText = model.positiveBtnText,
                listenerNegative = model.listenerNegative,
                listenerPositive = model.listenerPositive
            )
        }
        if (Looper.myLooper() == Looper.getMainLooper()) {
            showCalendarRunnable.run()
        } else {
            handler.post(showCalendarRunnable)
        }
    }

    override fun exitApp() {
        val navHostFragment = provider?.getSupportRouterNavHostFragment() ?: return
        navHostFragment.requireActivity().finishAndRemoveTask()
    }

    override fun showColorPicker(model: ColorRouterModel) {
        val activityContext = provider?.getSupportActivityContext() ?: return
        val showColorPickerRunnable = Runnable {
            ColorPickerDialog.Builder(activityContext)
                .setPositiveButton(
                    model.confirmText,
                    object : ColorEnvelopeListener {
                        override fun onColorSelected(envelope: ColorEnvelope?, fromUser: Boolean) {
                            envelope?.hexCode?.let {
                                model.onClickColor.invoke(it)
                            }
                        }
                    }
                )
                .setNegativeButton(model.cancelText) { dialogInterface, i -> dialogInterface.dismiss() }
                .attachAlphaSlideBar(true)
                .attachBrightnessSlideBar(true)
                .setBottomSpace(12)
                .show()
        }

        if (Looper.myLooper() == Looper.getMainLooper()) {
            showColorPickerRunnable.run()
        } else {
            handler.post(showColorPickerRunnable)
        }
    }

    override fun showSnackBar(message: String) {
        val rootView = provider?.getSupportRouterNavHostFragment()?.view ?: return
        val showSnackBarRunnable = Runnable {
            onShowSnackBar(
                text = message,
                anchorView = rootView
            )
        }
        if (Looper.myLooper() == Looper.getMainLooper()) {
            showSnackBarRunnable.run()
        } else {
            handler.post(showSnackBarRunnable)
        }
    }

    override fun hideKeyboard() {
        val hostFragment = provider?.getSupportRouterNavHostFragment() ?: return
        hostFragment.hideKeyboard()
    }
}
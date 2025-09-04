package com.sergei.pokhodai.expensemanagement.core.base.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sergei.pokhodai.expensemanagement.core.formatter.LocalDateFormatter
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun getBackPressedCallback(
    enabled: Boolean = true,
    crossinline action: OnBackPressedCallback.() -> Unit,
): OnBackPressedCallback {
    return object : OnBackPressedCallback(enabled) {
        override fun handleOnBackPressed() {
            action()
        }
    }
}

fun <VB : ViewBinding> Fragment.viewBinding(
    destroy: ((VB) -> Unit)? = null,
    init: (View) -> VB,
): Lazy<VB> {
    val delegate = ViewBindingDelegate(init, destroy, this)

    viewLifecycleOwnerLiveData.observe(this) {
        it.lifecycle.addObserver(delegate)
    }

    return delegate
}

inline fun FragmentManager.showDatePickerDialog(
    value: LocalDateFormatter,
    start: LocalDateFormatter = LocalDateFormatter.today().minusYears(2),
    end: LocalDateFormatter = LocalDateFormatter.today().plusYears(2),
    validator: CalendarConstraints.DateValidator? = null,
    crossinline listener: (LocalDateFormatter) -> Unit
) {
    val constraintsBuilder =
        CalendarConstraints.Builder()
            .setStart(start.toMillis())
            .setEnd(end.toMillis())
    validator?.let(constraintsBuilder::setValidator)
    val datePicker =
        MaterialDatePicker.Builder.datePicker()
            .setCalendarConstraints(constraintsBuilder.build())
            .setSelection(value.toMillis())
            .build()
    datePicker.addOnPositiveButtonClickListener { millis ->
        listener(LocalDateFormatter.toLocalDateFormatter(millis))
    }
    datePicker.show(this, null)
}

inline fun Fragment.showAlert(
    text: String?,
    positiveBtnText: String,
    negativeBtnText: String,
    crossinline listenerNegative: () -> Unit = {},
    crossinline listenerPositive: () -> Unit = {},
) {
    MaterialAlertDialogBuilder(requireContext())
        .setMessage(text)
        .setCancelable(false)
        .setNegativeButton(negativeBtnText) { dialog, which -> listenerNegative.invoke() }
        .setPositiveButton(positiveBtnText) { dialog, which -> listenerPositive.invoke() }
        .show()
}

fun <T> Fragment.autoClean(init: () -> T): ReadOnlyProperty<Fragment, T> = AutoClean(init)

private class AutoClean<T>(private val init: () -> T) : ReadOnlyProperty<Fragment, T>,
    LifecycleEventObserver {

    private var cached: T? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        return cached ?: init().also { newValue ->
            cached = newValue
            thisRef.viewLifecycleOwner.lifecycle.addObserver(this)
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            cached = null
            source.lifecycle.removeObserver(this)
        }
    }
}

private class ViewBindingDelegate<VB : ViewBinding>(
    private val init: (View) -> VB,
    private val destroy: ((VB) -> Unit)?,
    private val fragment: Fragment,
) : Lazy<VB>, LifecycleEventObserver {

    private var cached: VB? = null

    override val value: VB
        get() = cached ?: init(fragment.requireView()).also {
            if (fragment.isAdded) {
                cached = it
            }
        }

    override fun isInitialized(): Boolean = cached != null

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_CREATE -> {
                if (isInitialized()) {
                    return
                }
                cached = init(fragment.requireView())
            }

            Lifecycle.Event.ON_DESTROY -> {
                destroy?.invoke(checkNotNull(cached))
                cached = null
                source.lifecycle.removeObserver(this)
            }

            else -> Unit
        }
    }
}

fun AppCompatActivity.showKeyboard() = toggleKeyboard(true)
fun AppCompatActivity.hideKeyboard() = toggleKeyboard(false)
fun Fragment.showKeyboard() = toggleKeyboard(true)
fun Fragment.hideKeyboard() = toggleKeyboard(false)

private fun Fragment.toggleKeyboard(isShow: Boolean) {
    val activity = this.activity
    activity ?: return
    if (activity is AppCompatActivity) {
        activity.toggleKeyboard(isShow)
    }
}

private fun AppCompatActivity.toggleKeyboard(isShow: Boolean) {
    val currentFocusedView = this.currentFocus
    currentFocusedView ?: return
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    when (isShow) {
        true -> {
            inputMethodManager.showSoftInput(currentFocusedView, InputMethodManager.SHOW_IMPLICIT)
        }
        false -> {
            inputMethodManager.hideSoftInputFromWindow(
                currentFocusedView.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
            currentFocusedView.clearFocus()
        }
    }
}

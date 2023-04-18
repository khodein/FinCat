package com.sergei.pokhodai.expensemanagement.utils

import android.annotation.SuppressLint
import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import android.view.View
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.sergei.pokhodai.expensemanagement.utils.enums.Creater
import com.sergei.pokhodai.expensemanagement.R

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

@SuppressLint("RestrictedApi")
fun Context.showMenu(
    v: View,
    action: (Creater) -> Unit,
) {
    val popup = PopupMenu(this, v).apply {
        menuInflater.inflate(R.menu.wallet_attach_menu, menu)
        gravity = Gravity.END
    }

    val menuBuilder = popup.menu as MenuBuilder

    menuBuilder[0].setOnMenuItemClickListener {
        action.invoke(Creater.EDIT)
        true
    }

    val titleDelete = this.getString(R.string.delete)
    val titleDeleteSpan = SpannableString(titleDelete)
    titleDeleteSpan.setSpan(
        ForegroundColorSpan(this.getColor(R.color.red_600)),
        0,
        titleDelete.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    menuBuilder[1].title = titleDeleteSpan
    menuBuilder[1].setOnMenuItemClickListener {
        action.invoke(Creater.DELETE)
        true
    }

    popup.show()
}
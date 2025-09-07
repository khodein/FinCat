package com.sergei.pokhodai.expensemanagement.core.base.dialog

import android.app.Dialog
import android.os.Bundle
import android.widget.FrameLayout
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sergei.pokhodai.expensemanagement.core.base.R
import com.sergei.pokhodai.expensemanagement.core.base.color.ColorValue
import com.sergei.pokhodai.expensemanagement.core.base.corner.RoundMode
import com.sergei.pokhodai.expensemanagement.core.base.corner.ViewCorner
import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewDimension

open class BaseBottomSheetDialogFragment(
    @LayoutRes private val layoutResId: Int
) : BottomSheetDialogFragment(layoutResId) {

    protected open val backgroundColor = ColorValue.Res(R.color.background)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.attributes?.windowAnimations = R.style.BottomSheetDialogAnimation
        dialog.setOnShowListener {
            setSettingsDialog((it as BottomSheetDialog))
        }
        return dialog
    }

    @CallSuper
    protected open fun setSettingsDialog(dialog: BottomSheetDialog) {
        val sheetContainer =
            dialog.findViewById<FrameLayout?>(com.google.android.material.R.id.design_bottom_sheet) ?: return
        setCorner(sheetContainer)
        with(dialog) {
            behavior.isHideable = true
            behavior.significantVelocityThreshold = Int.MAX_VALUE
            behavior.skipCollapsed = false
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            isCancelable = true
        }
    }

    private fun setCorner(sheetContainer: FrameLayout) {
        ViewCorner.Simple(
            radius = RADIUS,
            roundMode = RoundMode.TOP
        ).resolve(
            view = sheetContainer,
            backgroundColorInt = backgroundColor.getColor(sheetContainer.context)
        )
    }

    private companion object {
        val RADIUS = ViewDimension.Dp(20).value
    }
}
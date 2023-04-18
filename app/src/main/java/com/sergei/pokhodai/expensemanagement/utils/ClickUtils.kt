package com.sergei.pokhodai.expensemanagement.utils

import android.util.Log
import android.view.View

object ClickUtils {

    private var lastClick = 0L
    private const val THROTTLE_TIMEOUT_IN_MILLS = 500L

    private fun saveTimeStampLastClick(clickTimeStamp: Long = System.currentTimeMillis()) {
        lastClick = clickTimeStamp
    }

    fun View.setOnThrottleClickListener(
        throttleTimeout: Long = THROTTLE_TIMEOUT_IN_MILLS,
        onClickListener: View.OnClickListener?
    ) {
        if (onClickListener == null) {
            setOnClickListener(null)
        } else {
            setOnClickListener {
                if (System.currentTimeMillis() < lastClick + throttleTimeout) {
                    Log.d("click throttled", "${lastClick - System.currentTimeMillis()} ms passed")
                    return@setOnClickListener
                }
                saveTimeStampLastClick()
                onClickListener.onClick(it)
            }
        }
    }
}
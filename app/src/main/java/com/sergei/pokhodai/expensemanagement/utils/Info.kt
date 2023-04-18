package com.sergei.pokhodai.expensemanagement.utils

import android.os.Build
import com.sergei.pokhodai.expensemanagement.BuildConfig

object Info {
    val BUILD = "${Build.VERSION.RELEASE}(${Build.VERSION.SDK_INT})"
    val BRAND_MODEL_PHONE = "${Build.BRAND}, ${Build.MODEL}"
    val VERSION_NAME = BuildConfig.VERSION_NAME
    val VERSION_CODE = BuildConfig.VERSION_CODE

    val APP = "BuildInfo: v$VERSION_NAME, Assembly: $VERSION_CODE\n" +
            "DeviceModel: $BRAND_MODEL_PHONE \n" +
            "Android Version: $BUILD"
}
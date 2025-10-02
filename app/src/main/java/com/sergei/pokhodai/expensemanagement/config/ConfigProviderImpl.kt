package com.sergei.pokhodai.expensemanagement.config

import com.sergei.pokhodai.expensemanagement.core.configprovider.ConfigProvider
import android.os.Build
import com.sergei.pokhodai.expensemanagement.BuildConfig

internal class ConfigProviderImpl : ConfigProvider {

    override fun getBuild(): String {
        return "${Build.VERSION.RELEASE}(${Build.VERSION.SDK_INT})"
    }

    override fun getBrand(): String {
        return "${Build.BRAND}, ${Build.MODEL}"
    }

    override fun getVersionName(): String {
        return BuildConfig.VERSION_NAME
    }

    override fun getVersionCode(): String {
        return BuildConfig.VERSION_CODE.toString()
    }

    override fun isDebug() = BuildConfig.DEBUG
}
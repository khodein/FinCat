package com.sergei.pokhodai.expensemanagement.core.configprovider

interface ConfigProvider {
    fun getBuild(): String
    fun getBrand(): String
    fun getVersionName(): String
    fun getVersionCode(): String
    fun isDebug(): Boolean
}
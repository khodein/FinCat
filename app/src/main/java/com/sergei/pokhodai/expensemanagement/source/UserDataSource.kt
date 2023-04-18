package com.sergei.pokhodai.expensemanagement.source

import android.content.SharedPreferences
import androidx.core.content.edit
import com.sergei.pokhodai.expensemanagement.base.source.PrefDelegateModel
import com.sergei.pokhodai.expensemanagement.data.models.User
import com.sergei.pokhodai.expensemanagement.utils.enums.Currency
import com.sergei.pokhodai.expensemanagement.utils.enums.Language

class UserDataSource(
    sharedPreferences: SharedPreferences
) : PrefDelegateModel(sharedPreferences) {

    var isFirstEntry by booleanPref()

    var user by objPref<User>()

    var currency by objPref<Currency>()

    var language by objPref<Language>()

    var pinCode by stringPref()

    fun logout() {
        prefs.edit { clear() }
        language = Language.EU
    }
}
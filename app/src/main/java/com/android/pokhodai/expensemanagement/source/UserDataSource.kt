package com.android.pokhodai.expensemanagement.source

import android.content.SharedPreferences
import androidx.core.content.edit
import com.android.pokhodai.expensemanagement.base.source.PrefDelegateModel
import com.android.pokhodai.expensemanagement.data.models.User
import com.android.pokhodai.expensemanagement.utils.enums.Currency
import com.android.pokhodai.expensemanagement.utils.enums.Language

class UserDataSource(
    sharedPreferences: SharedPreferences
) : PrefDelegateModel(sharedPreferences) {

    var user by objPref<User>()

    var currency by objPref<Currency>()

    var language by objPref<Language>()

    var pinCode by stringPref()

    fun logout() {
        prefs.edit { clear() }
    }
}
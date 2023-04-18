package com.sergei.pokhodai.expensemanagement.ui.user

import androidx.lifecycle.ViewModel
import com.sergei.pokhodai.expensemanagement.repositories.LanguageRepository
import com.sergei.pokhodai.expensemanagement.source.UserDataSource
import com.sergei.pokhodai.expensemanagement.utils.LocalDateFormatter
import com.sergei.pokhodai.expensemanagement.utils.ValidatorsUtils
import com.sergei.pokhodai.expensemanagement.utils.enums.Currency
import com.sergei.pokhodai.expensemanagement.utils.enums.Language
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userDataSource: UserDataSource,
    private val validatorsUtils: ValidatorsUtils,
    languageRepository: LanguageRepository,
): ViewModel() {

    private val _firstNameFlow = MutableStateFlow("")
    val firstNameFlow = _firstNameFlow.asStateFlow()

    private val _secondNameFlow = MutableStateFlow("")
    val secondNameFlow = _secondNameFlow.asStateFlow()

    private val _emailFlow = MutableStateFlow("")
    val emailFlow = _emailFlow.asStateFlow()

    private val _languageFlow = MutableStateFlow(languageRepository.getLanguage())
    val languageFlow = _languageFlow.asStateFlow()

    private val _currencyFlow = MutableStateFlow(userDataSource.currency ?: Currency.DOLLAR)
    val currencyFlow = _currencyFlow.asStateFlow()

    private val _birthFlow = MutableStateFlow(LocalDateFormatter.today())
    val birthFlow = _birthFlow.asStateFlow()

    val validate = combine(
        _firstNameFlow,
        _secondNameFlow,
        _emailFlow
    ) { first, second, email ->
        first.trim().isNotEmpty()
                && second.trim().isNotEmpty()
                && validatorsUtils.validateEmail(email)
    }

    fun onChangeFirstName(firstName: String) {
        _firstNameFlow.value = firstName
    }

    fun onChangeSecondName(secondName: String) {
        _secondNameFlow.value = secondName
    }

    fun onChangeEmail(email: String) {
        _emailFlow.value = email
    }

    fun onChangeLanguage() {
        _languageFlow.value = userDataSource.language ?: Language.EU
    }

    fun onChangeCurrency() {
        _currencyFlow.value = userDataSource.currency ?: Currency.DOLLAR
    }

    fun onChangeBirth(birth: LocalDateFormatter) {
        _birthFlow.value = birth
    }
}
package com.android.pokhodai.expensemanagement.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.pokhodai.expensemanagement.data.models.User
import com.android.pokhodai.expensemanagement.repositories.LanguageRepository
import com.android.pokhodai.expensemanagement.source.UserDataSource
import com.android.pokhodai.expensemanagement.utils.enums.Currency
import com.android.pokhodai.expensemanagement.utils.enums.Language
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userDataSource: UserDataSource,
    private val languageRepository: LanguageRepository,
): ViewModel() {

    private val _firstNameFlow = MutableStateFlow<String>("")

    private val _secondNameFlow = MutableStateFlow<String>("")

    private val _emailFlow = MutableStateFlow<String>("")

    private val _languageFlow = MutableStateFlow(languageRepository.getLanguage())
    val languageFlow = _languageFlow.asStateFlow()

    private val _currencyFlow = MutableStateFlow(userDataSource.currency ?: Currency.DOLLAR)
    val currencyFlow = _currencyFlow.asStateFlow()

    fun onClickSaveUserData(
        coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        viewModelScope.launch(coroutineDispatcher) {
            userDataSource.user = User(
                firstName = _firstNameFlow.value,
                secondName = _secondNameFlow.value,
                email = _emailFlow.value
            )
            userDataSource.currency = currencyFlow.value
        }
    }

    val validate = combine(
        _firstNameFlow,
        _secondNameFlow,
        _emailFlow
    ) { first, second, email ->
        first.trim().isNotEmpty() && second.trim().isNotEmpty() && email.trim().isNotEmpty()
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

    fun onChangeLanguage(language: Language) {
        languageRepository.setLanguage(language)
        _languageFlow.value = language
    }

    fun onChangeCurrency(currency: Currency) {
        _currencyFlow.value = currency
    }
}
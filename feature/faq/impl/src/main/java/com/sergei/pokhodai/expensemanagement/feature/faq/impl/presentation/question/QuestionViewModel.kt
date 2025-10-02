package com.sergei.pokhodai.expensemanagement.feature.faq.impl.presentation.question

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergei.pokhodai.expensemanagement.core.base.utils.isEmailValid
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.core.router.Router
import com.sergei.pokhodai.expensemanagement.core.support.api.router.SupportRouter
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.data.repository.QuestionRepository
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.domain.model.QuestionModel
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.presentation.question.mapper.QuestionMapper
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.presentation.question.state.QuestionBottomState
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.presentation.question.state.QuestionMailState
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.GetUserSelfUseCase
import com.sergei.pokhodai.expensemanagement.uikit.button.ButtonItem
import com.sergei.pokhodai.expensemanagement.uikit.toolbar.ToolbarItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class QuestionViewModel(
    private val questionMapper: QuestionMapper,
    private val questionRepository: QuestionRepository,
    private val getUserSelfUseCase: GetUserSelfUseCase,
    private val router: Router,
    private val supportRouter: SupportRouter,
) : ViewModel(), QuestionMapper.ItemListProvider {

    private var loadingJob: Job? = null
    private var editableJob: Job? = null
    private val _topFlow = MutableStateFlow<ToolbarItem.State?>(null)
    val topFlow = _topFlow.asStateFlow()

    private val _bottomFlow = MutableStateFlow<QuestionBottomState?>(null)
    val bottomFlow = _bottomFlow.asStateFlow()

    private val _itemsFlow = MutableStateFlow<List<RecyclerState>>(emptyList())
    val itemsFlow = _itemsFlow.asStateFlow()

    private val _emailDeveloperFlow = MutableStateFlow<QuestionMailState?>(null)
    val emailDeveloperFlow = _emailDeveloperFlow.asStateFlow()

    private var questionModel: QuestionModel = QuestionModel(
        name = null,
        email = null,
        title = null,
        message = ""
    )

    init {
        updateTop()
        updateSuccess()
        fetchData()
    }

    private fun fetchData() {
        updateBottom(true)
        loadingJob?.cancel()
        loadingJob = viewModelScope.launch {
            runCatching {
                getUserSelfUseCase.invoke()
            }.onSuccess { user ->
                questionModel = questionModel.copy(
                    name = user.name.ifEmpty { null },
                    email = user.email.ifEmpty { null }
                )
                updateSuccess()
            }.onFailure {
                updateBottom(false)
            }
        }
    }

    private fun sendMessage() {
        updateBottom(true)
        loadingJob?.cancel()
        loadingJob = viewModelScope.launch {
            runCatching {
                questionRepository.sendQuestion(questionModel)
            }.onSuccess {
                supportRouter.showSnackBar(questionMapper.getSuccessMessage())
                router.pop()
                updateBottom(false)
            }.onFailure {
                supportRouter.showSnackBar(questionMapper.getErrorMessage())
                updateBottom(false)
            }
        }
    }

    private fun updateTop() {
        _topFlow.value = questionMapper.getToolbarItemState(::onBackPressed)
    }

    private fun updateBottom(isLoading: Boolean) {
        _bottomFlow.value = questionMapper.getBottomState(
            isLoading = isLoading,
            onClickTop = ::onClickSendMessage,
            onClickBottom = ::onClickDeveloperMail
        )
    }

    private fun updateSuccess() {
        updateBottom(false)
        _itemsFlow.value = questionMapper.getItems(
            model = questionModel,
            provider = this
        )
    }

    override fun onChangeName(value: String) {
        onChangeDebounce {
            questionModel = questionModel.copy(
                name = value.ifEmpty { null }
            )
            updateSuccess()
        }
    }

    override fun onChangeEmail(value: String) {
        onChangeDebounce {
            questionModel = questionModel.copy(
                email = value.ifEmpty { null }
            )
            updateSuccess()
        }
    }

    override fun onChangeTitle(value: String) {
        onChangeDebounce {
            questionModel = questionModel.copy(
                title = value.ifEmpty { null }
            )
            updateSuccess()
        }
    }

    override fun onChangeDescription(value: String) {
        onChangeDebounce {
            questionModel = questionModel.copy(
                message = value
            )
            updateSuccess()
        }
    }

    private fun onChangeDebounce(
        onChange: () -> Unit
    ) {
        editableJob?.cancel()
        editableJob = viewModelScope.launch {
            delay(EDITABLE_DEBOUNCE)
            onChange.invoke()
        }
    }

    private fun onClickSendMessage(state: ButtonItem.State) {
        val name = questionModel.name?.trim()
        val email = questionModel.email?.trim()
        val title = questionModel.title?.trim()
        val message = questionModel.message.trim()

        when {
            email != null && !email.isEmailValid() -> {
                supportRouter.showSnackBar(questionMapper.getEmailError())
                return
            }

            name != null && name.isEmpty() -> {
                supportRouter.showSnackBar(questionMapper.getNameError())
                return
            }

            title != null && title.isEmpty() -> {
                supportRouter.showSnackBar(questionMapper.getTitleError())
                return
            }

            message.isEmpty() -> {
                supportRouter.showSnackBar(questionMapper.getMessageError())
                return
            }
        }

        sendMessage()
    }

    private fun onClickDeveloperMail(state: ButtonItem.State) {
        _emailDeveloperFlow.value = questionMapper.getEmailDeveloper(
            message = questionModel.message,
            title = questionModel.title
        )
        _emailDeveloperFlow.value = null
    }

    private fun onBackPressed() {
        router.pop()
    }

    private companion object {
        const val EDITABLE_DEBOUNCE = 150L
    }
}
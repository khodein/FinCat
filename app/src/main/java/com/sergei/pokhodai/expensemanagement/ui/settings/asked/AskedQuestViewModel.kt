package com.sergei.pokhodai.expensemanagement.ui.settings.asked

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergei.pokhodai.expensemanagement.repositories.AskQuestRepository
import com.sergei.pokhodai.expensemanagement.source.UserDataSource
import com.sergei.pokhodai.expensemanagement.utils.Info
import com.sergei.pokhodai.expensemanagement.utils.MutableUIStateFlow
import com.sergei.pokhodai.expensemanagement.utils.asUIStateFlow
import com.sergei.pokhodai.expensemanagement.utils.emitRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AskedQuestViewModel @Inject constructor(
    private val askQuestRepository: AskQuestRepository,
    private val userDataSource: UserDataSource,
) : ViewModel() {

    private val _askQuestState = MutableUIStateFlow<Unit>()
    val askQuestState = _askQuestState.asUIStateFlow()

    private val _topicFlow = MutableStateFlow("")
    val topicFlow = _topicFlow.asStateFlow()

    private val _descriptionFlow = MutableStateFlow("")
    val descriptionFlow = _descriptionFlow.asStateFlow()

    val validateFlow = combine(
        _topicFlow,
        _descriptionFlow
    ) { topic, descr ->
        topic.trim().isNotEmpty() && descr.trim().isNotEmpty()
    }

    fun onChangeTopic(topic: String) {
        _topicFlow.value = topic
    }

    fun onChangeDescription(descr: String) {
        _descriptionFlow.value = descr
    }

    fun onClickAskQuest(
        dispatcher: CoroutineDispatcher = Dispatchers.Default
    ) {
        viewModelScope.launch(
            dispatcher
        ) {
            val text =
                "${userDataSource.user?.fullName()}" +
                        "\n${userDataSource.user?.email}" +
                        "\n\n${topicFlow.value}" +
                        "\n\n${descriptionFlow.value}" +
                        "\n\n${Info.APP}"

            _askQuestState.emitRequest(
                askQuestRepository.sendMessage(
                    chatId = "-1001752492520",
                    text = text
                )
            ) { Unit }
        }
    }
}
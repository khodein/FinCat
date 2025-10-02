package com.sergei.pokhodai.expensemanagement.feature.faq.impl.presentation.question.mapper

import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewDimension
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_16_0_16_16
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_16_0_16_8
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_16_16_16_16
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_16_8_16_8
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.core.support.api.manager.ResManager
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.R
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.domain.model.QuestionModel
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.presentation.question.state.QuestionBottomState
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.presentation.question.state.QuestionMailState
import com.sergei.pokhodai.expensemanagement.uikit.button.ButtonItem
import com.sergei.pokhodai.expensemanagement.uikit.field.TextFieldItem
import com.sergei.pokhodai.expensemanagement.uikit.toolbar.ToolbarItem

internal class QuestionMapper(
    private val resManager: ResManager,
) {
    fun getToolbarItemState(
        onClickBack: () -> Unit
    ): ToolbarItem.State {
        return ToolbarItem.State(
            title = ToolbarItem.Title(
                value = resManager.getString(R.string.faq_question_title)
            ),
            onClickNavigation = onClickBack,
        )
    }

    fun getBottomState(
        isLoading: Boolean,
        onClickTop: (state: ButtonItem.State) -> Unit,
        onClickBottom: (state: ButtonItem.State) -> Unit
    ): QuestionBottomState {
        return QuestionBottomState(
            topButtonState = ButtonItem.State(
                provideId = "bottom_button_top_id",
                isLoading = isLoading,
                value = resManager.getString(R.string.faq_question_send),
                width = ViewDimension.MatchParent,
                fill = ButtonItem.Fill.Filled,
                height = ViewDimension.Dp(36),
                onClick = onClickTop,
                container = ButtonItem.Container(
                    paddings = P_16_8_16_8,
                )
            ),
            bottomButtonState = ButtonItem.State(
                provideId = "bottom_button_bottom_id",
                isLoading = isLoading,
                value = resManager.getString(R.string.faq_question_develop_email),
                width = ViewDimension.MatchParent,
                fill = ButtonItem.Fill.Outline(),
                height = ViewDimension.Dp(36),
                onClick = onClickBottom,
                container = ButtonItem.Container(
                    paddings = P_16_0_16_8,
                )
            ),
        )
    }

    fun getItems(
        model: QuestionModel,
        provider: ItemListProvider,
    ): List<RecyclerState> {
        return listOf(
            getNameTextFieldItemState(
                value = model.name.orEmpty(),
                onChangeName = provider::onChangeName
            ),
            getEmailTextFieldITemState(
                value = model.email.orEmpty(),
                onChangeEmail = provider::onChangeEmail
            ),
            getTitleTextFieldItemState(
                value = model.title.orEmpty(),
                onChangeName = provider::onChangeTitle
            ),
            getDescriptionTextFieldITemState(
                value = model.message,
                onChangeDescription = provider::onChangeDescription
            )
        )
    }

    private fun getNameTextFieldItemState(
        value: String,
        onChangeName: ((String) -> Unit)
    ): TextFieldItem.State {
        return TextFieldItem.State(
            provideId = "text_field_item_name_id",
            value = value,
            container = TextFieldItem.Container(
                paddings = P_16_16_16_16
            ),
            hint = resManager.getString(R.string.faq_question_name_hint),
            onAfterTextChanger = onChangeName,
        )
    }

    private fun getTitleTextFieldItemState(
        value: String,
        onChangeName: ((String) -> Unit)
    ): TextFieldItem.State {
        return TextFieldItem.State(
            provideId = "text_field_item_name_id",
            value = value,
            container = TextFieldItem.Container(
                paddings = P_16_0_16_16
            ),
            hint = resManager.getString(R.string.faq_question_title_hint),
            onAfterTextChanger = onChangeName,
        )
    }

    private fun getEmailTextFieldITemState(
        value: String,
        onChangeEmail: ((String) -> Unit)
    ): TextFieldItem.State {
        return TextFieldItem.State(
            provideId = "text_field_item_email_id",
            value = value,
            container = TextFieldItem.Container(
                paddings = P_16_0_16_16,
            ),
            hint = resManager.getString(R.string.faq_question_email_hint),
            onAfterTextChanger = onChangeEmail
        )
    }

    private fun getDescriptionTextFieldITemState(
        value: String,
        onChangeDescription: ((value: String) -> Unit)? = null,
    ): TextFieldItem.State {
        return TextFieldItem.State(
            provideId = "text_field_item_description_id",
            value = value,
            imeOption = TextFieldItem.FieldImeOption.ENTER,
            inputType = TextFieldItem.FieldInputType.MULTILINE,
            container = TextFieldItem.Container(
                paddings = P_16_0_16_16
            ),
            line = TextFieldItem.Line(
                minLine = 3,
                maxLines = 5,
                lines = 5,
                isSingleLine = false
            ),
            onAfterTextChanger = onChangeDescription,
            hint = resManager.getString(R.string.faq_question_message_hint),
        )
    }

    fun getEmailError(): String {
        return resManager.getString(R.string.faq_question_email_error)
    }

    fun getNameError(): String {
        return resManager.getString(R.string.faq_question_name_error)
    }

    fun getTitleError(): String {
        return resManager.getString(R.string.faq_question_title_error)
    }

    fun getMessageError(): String {
        return resManager.getString(R.string.faq_question_message_error)
    }

    fun getEmailDeveloper(
        title: String?,
        message: String,
    ): QuestionMailState {
        return QuestionMailState(
            email = SUPPORT_EMAIL,
            title = title,
            message = message
        )
    }

    fun getSuccessMessage(): String {
        return resManager.getString(R.string.faq_question_send_success)
    }

    fun getErrorMessage(): String {
        return resManager.getString(R.string.faq_question_send_error)
    }

    interface ItemListProvider {
        fun onChangeName(value: String)
        fun onChangeEmail(value: String)
        fun onChangeTitle(value: String)
        fun onChangeDescription(value: String)
    }

    private companion object {
        const val SUPPORT_EMAIL = "sergeipokhodai@yandex.ru"
    }
}
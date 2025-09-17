package com.sergei.pokhodai.expensemanagement.feature.faq.impl.data.repository

import com.sergei.pokhodai.expensemanagement.feature.faq.impl.domain.model.FaqModel

internal class FaqRepository {

    fun getFaqList(): List<FaqModel> {
        return FaqModel.entries
    }
}
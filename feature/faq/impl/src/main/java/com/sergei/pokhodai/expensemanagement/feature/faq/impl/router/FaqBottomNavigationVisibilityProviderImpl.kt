package com.sergei.pokhodai.expensemanagement.feature.faq.impl.router

import android.annotation.SuppressLint
import androidx.navigation.serialization.generateHashCode
import com.sergei.pokhodai.expensemanagement.core.router.provider.BottomNavigationVisibleProvider
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.presentation.contract.FaqContract
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.presentation.question.contract.QuestionContract

internal class FaqBottomNavigationVisibilityProviderImpl : BottomNavigationVisibleProvider {

    @SuppressLint("RestrictedApi")
    override fun getRouteListIsNotVisible(): List<Int> {
        return listOf(
            FaqContract.serializer().generateHashCode(),
            QuestionContract.serializer().generateHashCode()
        )
    }
}
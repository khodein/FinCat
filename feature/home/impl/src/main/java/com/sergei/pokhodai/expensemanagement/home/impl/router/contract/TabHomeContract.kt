package com.sergei.pokhodai.expensemanagement.home.impl.router.contract

import com.sergei.pokhodai.expensemanagement.core.router.contract.TabContract
import com.sergei.pokhodai.expensemanagement.feature.home.impl.R
import kotlinx.serialization.Serializable

@Serializable
internal data object TabHomeContract : TabContract {
    override val nameResId: Int = R.string.home_tab
    override val iconResId: Int = R.drawable.ic_home_selector
    override val startDestination = HomeContract::class
}
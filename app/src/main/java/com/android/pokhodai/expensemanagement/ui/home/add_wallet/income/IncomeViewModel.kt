package com.android.pokhodai.expensemanagement.ui.home.add_wallet.income

import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModel
import com.android.pokhodai.expensemanagement.R
import com.android.pokhodai.expensemanagement.ui.home.add_wallet.adapter.CategoriesAdapter
import com.android.pokhodai.expensemanagement.utils.ManagerUtils
import com.android.pokhodai.expensemanagement.utils.enums.Icons
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class IncomeViewModel @Inject constructor(
    managerUtils: ManagerUtils
) : ViewModel() {

    val incomeCategoriesList = listOf(
        CategoriesAdapter.Categories(
            name = managerUtils.getString(R.string.income_grocery),
            icon = Icons.GROCERIES
        ),
        CategoriesAdapter.Categories(
            name = managerUtils.getString(R.string.income_gifts),
            icon = Icons.GIFTS,
        ),
        CategoriesAdapter.Categories(
            name = managerUtils.getString(R.string.income_wages),
            icon = Icons.DONATE
        ),
        CategoriesAdapter.Categories(
            name = managerUtils.getString(R.string.income_interest),
            icon = Icons.INSTITUTE
        ),
        CategoriesAdapter.Categories(
            name = managerUtils.getString(R.string.income_savings),
            icon = Icons.SAVINGS
        ),
        CategoriesAdapter.Categories(
            name = managerUtils.getString(R.string.income_allowance),
            icon = Icons.ALLOWANCE
        )
    )
}
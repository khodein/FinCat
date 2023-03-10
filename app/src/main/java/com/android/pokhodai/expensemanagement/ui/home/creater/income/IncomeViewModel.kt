package com.android.pokhodai.expensemanagement.ui.home.creater.income

import androidx.lifecycle.ViewModel
import com.android.pokhodai.expensemanagement.R
import com.android.pokhodai.expensemanagement.ui.home.creater.adapter.CategoriesAdapter
import com.android.pokhodai.expensemanagement.utils.ManagerUtils
import com.android.pokhodai.expensemanagement.utils.enums.Icons
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IncomeViewModel @Inject constructor(
    managerUtils: ManagerUtils
) : ViewModel() {

    val incomeCategoriesList = listOf(
        CategoriesAdapter.Categories(
            name = managerUtils.getString(R.string.income_grocery),
            icon = Icons.GROCERIES,
            id = 0
        ),
        CategoriesAdapter.Categories(
            name = managerUtils.getString(R.string.income_gifts),
            icon = Icons.GIFTS,
            id = 1,
        ),
        CategoriesAdapter.Categories(
            name = managerUtils.getString(R.string.income_wages),
            icon = Icons.DONATE,
            id = 2
        ),
        CategoriesAdapter.Categories(
            name = managerUtils.getString(R.string.income_interest),
            icon = Icons.INSTITUTE,
            id = 3
        ),
        CategoriesAdapter.Categories(
            name = managerUtils.getString(R.string.income_savings),
            icon = Icons.SAVINGS,
            id = 4
        ),
        CategoriesAdapter.Categories(
            name = managerUtils.getString(R.string.income_allowance),
            icon = Icons.ALLOWANCE,
            id = 5
        )
    )
}
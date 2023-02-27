package com.android.pokhodai.expensemanagement.ui.home.add_wallet.income

import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModel
import com.android.pokhodai.expensemanagement.R
import com.android.pokhodai.expensemanagement.ui.home.add_wallet.adapter.CategoriesAdapter
import com.android.pokhodai.expensemanagement.utils.ManagerUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class IncomeViewModel @Inject constructor(
    managerUtils: ManagerUtils
) : ViewModel() {

    val incomeCategoriesList = listOf<CategoriesAdapter.Categories>(
        CategoriesAdapter.Categories(
            name = managerUtils.getString(R.string.income_grocery),
            resId = R.drawable.ic_money
        ),
        CategoriesAdapter.Categories(
            name = managerUtils.getString(R.string.income_gifts),
            resId = R.drawable.ic_gifts,
        ),
        CategoriesAdapter.Categories(
            name = managerUtils.getString(R.string.income_wages),
            resId = R.drawable.ic_donate
        ),
        CategoriesAdapter.Categories(
            name = managerUtils.getString(R.string.income_interest),
            resId = R.drawable.ic_institute
        ),
        CategoriesAdapter.Categories(
            name = managerUtils.getString(R.string.income_savings),
            resId = R.drawable.ic_savings
        ),
        CategoriesAdapter.Categories(
            name = managerUtils.getString(R.string.income_allowance),
            resId = R.drawable.ic_allowance
        )
    )
}
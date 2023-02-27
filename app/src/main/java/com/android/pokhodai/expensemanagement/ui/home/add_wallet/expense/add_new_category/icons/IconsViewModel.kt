package com.android.pokhodai.expensemanagement.ui.home.add_wallet.expense.add_new_category.icons

import androidx.lifecycle.ViewModel
import com.android.pokhodai.expensemanagement.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IconsViewModel @Inject constructor(): ViewModel() {

    val icons = listOf(
        R.drawable.ic_groceries,
        R.drawable.ic_cafe,
        R.drawable.ic_electronics,
        R.drawable.ic_gifts,
        R.drawable.ic_laundry,
        R.drawable.ic_party,
        R.drawable.ic_liquor,
        R.drawable.ic_fuel,
        R.drawable.ic_maintenance,
        R.drawable.ic_education,
        R.drawable.ic_self_development,
        R.drawable.ic_money,
        R.drawable.ic_health,
        R.drawable.ic_transportation,
        R.drawable.ic_restaurant,
        R.drawable.ic_sport,
        R.drawable.ic_savings_1,
        R.drawable.ic_institute,
        R.drawable.ic_donate
    )
}
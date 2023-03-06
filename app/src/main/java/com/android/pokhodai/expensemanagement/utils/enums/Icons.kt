package com.android.pokhodai.expensemanagement.utils.enums

import android.os.Parcelable
import com.android.pokhodai.expensemanagement.R
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Icons(val resId: Int): Parcelable {
    NONE(R.drawable.ic_add_new_category),
    GROCERIES(R.drawable.ic_groceries),
    CAFE(R.drawable.ic_cafe),
    ELECTRONICS(R.drawable.ic_electronics),
    GIFTS(R.drawable.ic_gifts),
    LAUNDRY(R.drawable.ic_laundry),
    PARTY(R.drawable.ic_party),
    LIQUOR(R.drawable.ic_liquor),
    FUEL(R.drawable.ic_fuel),
    MAINTENANCE(R.drawable.ic_maintenance),
    EDUCATION(R.drawable.ic_education),
    SELF_DEVELOPMENT(R.drawable.ic_self_development),
    MONEY(R.drawable.ic_money),
    HEALTH(R.drawable.ic_health),
    TRANSPORTATION(R.drawable.ic_transportation),
    RESTAURANT(R.drawable.ic_restaurant),
    SPORT(R.drawable.ic_sport),
    SAVINGS(R.drawable.ic_savings_1),
    INSTITUTE(R.drawable.ic_institute),
    ALLOWANCE(R.drawable.ic_allowance),
    DONATE(R.drawable.ic_donate)
}
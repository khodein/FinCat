package com.android.pokhodai.expensemanagement.utils.enums

import android.os.Parcelable
import com.android.pokhodai.expensemanagement.R
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Icons(val resId: Int, val colorId: Int): Parcelable {
    NONE(R.drawable.ic_add_new_category, R.color.grey_100),
    GROCERIES(R.drawable.ic_groceries, R.color.icons_C8E6C9),
    CAFE(R.drawable.ic_cafe, R.color.icons_FFECB3),
    ELECTRONICS(R.drawable.ic_electronics, R.color.icons_FFCDD2),
    GIFTS(R.drawable.ic_gifts, R.color.icons_E1BEE7),
    LAUNDRY(R.drawable.ic_laundry, R.color.icons_B3E5FC),
    PARTY(R.drawable.ic_party, R.color.icons_BBDEFB),
    LIQUOR(R.drawable.ic_liquor, R.color.icons_DCEDC8),
    FUEL(R.drawable.ic_fuel, R.color.icons_D7CCC8),
    MAINTENANCE(R.drawable.ic_maintenance, R.color.icons_B39DDB),
    EDUCATION(R.drawable.ic_education, R.color.icons_C8E6C9),
    SELF_DEVELOPMENT(R.drawable.ic_self_development, R.color.icons_CFD8DC),
    MONEY(R.drawable.ic_money, R.color.icons_FFCCBC),
    HEALTH(R.drawable.ic_health, R.color.icons_F8BBD0),
    TRANSPORTATION(R.drawable.ic_transportation, R.color.icons_B2EBF2),
    RESTAURANT(R.drawable.ic_restaurant, R.color.icons_C5CAE9),
    SPORT(R.drawable.ic_sport, R.color.icons_E6EE9C),
    SAVINGS(R.drawable.ic_savings_1, R.color.icons_FFECB3),
    INSTITUTE(R.drawable.ic_institute, R.color.icons_FFE0B2),
    ALLOWANCE(R.drawable.ic_allowance, R.color.icons_C8E6C9),
    DONATE(R.drawable.ic_donate, R.color.icons_FFF9C4)
}
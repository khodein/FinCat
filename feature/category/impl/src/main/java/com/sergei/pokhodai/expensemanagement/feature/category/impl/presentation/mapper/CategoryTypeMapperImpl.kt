package com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.mapper

import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.CategoryType
import com.sergei.pokhodai.expensemanagement.feature.category.api.mapper.CategoryTypeMapper
import com.sergei.pokhodai.expensemanagement.feature.category.impl.R

internal class CategoryTypeMapperImpl : CategoryTypeMapper {

    override fun getImageResId(
        type: CategoryType
    ): Int {
        return when (type) {
            CategoryType.CAFE -> R.drawable.ic_cafe
            CategoryType.FUEL -> R.drawable.ic_fuel
            CategoryType.LAUNDRY -> R.drawable.ic_laundry
            CategoryType.SAVINGS -> R.drawable.ic_savings
            CategoryType.DONATE -> R.drawable.ic_donate
            CategoryType.HEALTH -> R.drawable.ic_health
            CategoryType.LIQUOR -> R.drawable.ic_liquor
            CategoryType.GIFTS -> R.drawable.ic_gifts
            CategoryType.MONEY -> R.drawable.ic_money
            CategoryType.PARTY -> R.drawable.ic_party
            CategoryType.SPORT -> R.drawable.ic_sport
            CategoryType.EDUCATION -> R.drawable.ic_education
            CategoryType.GROCERIES -> R.drawable.ic_groceries
            CategoryType.INSTITUTE -> R.drawable.ic_institute
            CategoryType.RESTAURANT -> R.drawable.ic_restaurant
            CategoryType.ELECTRONICS -> R.drawable.ic_electronics
            CategoryType.MAINTENANCE -> R.drawable.ic_maintenance
            CategoryType.TRANSPORTATION -> R.drawable.ic_transportation
            CategoryType.SELF_DEVELOPMENT -> R.drawable.ic_self_development
        }
    }

    override fun getColorStr(type: CategoryType): String {
        return when (type) {
            CategoryType.CAFE -> "#C8E6C9"
            CategoryType.FUEL -> "#FFECB3"
            CategoryType.LAUNDRY -> "#FFCDD2"
            CategoryType.SAVINGS -> "#E1BEE7"
            CategoryType.DONATE -> "#B3E5FC"
            CategoryType.HEALTH -> "#BBDEFB"
            CategoryType.LIQUOR -> "#DCEDC8"
            CategoryType.GIFTS -> "#D7CCC8"
            CategoryType.MONEY -> "#B39DDB"
            CategoryType.PARTY -> "#CFD8DC"
            CategoryType.SPORT -> "#FFCCBC"
            CategoryType.EDUCATION -> "#F8BBD0"
            CategoryType.GROCERIES -> "#B2EBF2"
            CategoryType.INSTITUTE -> "#C5CAE9"
            CategoryType.RESTAURANT -> "#E6EE9C"
            CategoryType.ELECTRONICS -> "#FFE0B2"
            CategoryType.MAINTENANCE -> "#FFF9C4"
            CategoryType.TRANSPORTATION -> "#FFECB3"
            CategoryType.SELF_DEVELOPMENT -> "#B3E5FC"
        }
    }
}
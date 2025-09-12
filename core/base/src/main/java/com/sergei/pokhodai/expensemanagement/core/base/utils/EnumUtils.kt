package com.sergei.pokhodai.expensemanagement.core.base.utils

import kotlin.enums.EnumEntries

fun <E : Enum<E>> EnumEntries<E>.getEntry(name: String) = firstOrNull { it.name == name }
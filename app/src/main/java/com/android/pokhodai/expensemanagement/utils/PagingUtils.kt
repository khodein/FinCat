package com.android.pokhodai.expensemanagement.utils

import androidx.paging.PagingData
import androidx.paging.TerminalSeparatorType
import androidx.paging.insertSeparators

fun <T : Any> PagingData<T>.insertEmptyItem(
    item: T,
): PagingData<T> = insertSeparators { before, after ->
    if (before == null && after == null) item else null
}
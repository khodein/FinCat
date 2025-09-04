package com.sergei.pokhodai.expensemanagement.feature.report.api.model

import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.BudgetType

class ReportListModel(
    val sum: String,
    val budgetType: BudgetType,
    val reports: List<ReportModel>
)
package com.sergei.pokhodai.expensemanagement.feature.report.impl.router.contract

import com.sergei.pokhodai.expensemanagement.core.router.contract.TabContract
import com.sergei.pokhodai.expensemanagement.feature.report.impl.R
import kotlinx.serialization.Serializable

@Serializable
internal data object TabReportContract : TabContract {
    override val startDestination = ReportContract::class
    override val nameResId: Int = R.string.report_tab
    override val iconResId: Int = R.drawable.ic_report_selector
}
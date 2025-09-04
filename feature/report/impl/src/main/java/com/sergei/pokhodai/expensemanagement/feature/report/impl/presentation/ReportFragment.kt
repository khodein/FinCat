package com.sergei.pokhodai.expensemanagement.feature.report.impl.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.sergei.pokhodai.expensemanagement.core.base.utils.viewBinding
import com.sergei.pokhodai.expensemanagement.core.recycler.adapter.RecyclerAdapter
import com.sergei.pokhodai.expensemanagement.core.base.utils.autoClean
import com.sergei.pokhodai.expensemanagement.core.base.utils.bindStateOptional
import com.sergei.pokhodai.expensemanagement.core.base.utils.observe
import com.sergei.pokhodai.expensemanagement.feature.report.impl.R
import com.sergei.pokhodai.expensemanagement.feature.report.impl.databinding.FragmentReportBinding
import com.sergei.pokhodai.expensemanagement.feature.report.impl.presentation.decoration.ReportDecoration
import com.sergei.pokhodai.expensemanagement.uikit.button.ButtonItemView
import kotlinx.coroutines.flow.filterNotNull
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

internal class ReportFragment : Fragment(R.layout.fragment_report) {

    private val binding by viewBinding(init = FragmentReportBinding::bind)
    private val viewModel by viewModel<ReportViewModel>()
    private val adapter by autoClean(init = ::RecyclerAdapter)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onStart()
        setAdapter()
        setObservable()
    }

    private fun setObservable() = with(viewModel) {
        itemsFlow.observe(
            this@ReportFragment,
            adapter::submitList
        )

        bottomFlow.observe(this@ReportFragment) { state ->
            binding.reportBottom.bindStateOptional(
                state = state,
                binder = ButtonItemView::bindState
            )
        }

        requestFlow.observe(
            this@ReportFragment,
            binding.reportRequest::bindState
        )

        topFlow
            .filterNotNull()
            .observe(
                this@ReportFragment,
                binding.reportCalendar::bindState
            )


        shareFlow
            .filterNotNull()
            .observe(this@ReportFragment, ::onShareExcelFile)
    }

    private fun onShareExcelFile(file: File) {
        val uri = FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.provider",
            file
        )

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = APPLICATION_EXCEL_TYPE
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        requireActivity().startActivity(Intent.createChooser(shareIntent, "Share Excel File"))
    }

    private fun setAdapter() {
        binding.reportList.adapter = adapter
        binding.reportList.addItemDecoration(ReportDecoration())
        binding.reportList.itemAnimator = null
    }

    private companion object {
        const val APPLICATION_EXCEL_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
    }
}
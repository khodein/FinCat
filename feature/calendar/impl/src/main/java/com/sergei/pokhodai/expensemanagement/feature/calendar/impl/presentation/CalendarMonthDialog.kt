package com.sergei.pokhodai.expensemanagement.feature.calendar.impl.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.sergei.pokhodai.expensemanagement.core.base.dialog.BaseBottomSheetDialogFragment
import com.sergei.pokhodai.expensemanagement.core.base.utils.observe
import com.sergei.pokhodai.expensemanagement.core.base.utils.viewBinding
import com.sergei.pokhodai.expensemanagement.core.recycler.adapter.RecyclerAdapter
import com.sergei.pokhodai.expensemanagement.feature.calendar.impl.R
import com.sergei.pokhodai.expensemanagement.feature.calendar.impl.databinding.DialogCalendarMonthBinding
import kotlinx.coroutines.flow.filterNotNull
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class CalendarMonthDialog : BaseBottomSheetDialogFragment(R.layout.dialog_calendar_month) {

    private val viewModel by viewModel<CalendarMonthViewModel>()
    private val binding by viewBinding(init = DialogCalendarMonthBinding::bind)
    private var adapter: RecyclerAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setObservable()
    }

    private fun setAdapter() {
        adapter = RecyclerAdapter()
        binding.calendarMonthItems.adapter = adapter
        binding.calendarMonthItems.itemAnimator = null
    }

    private fun setObservable() = with(viewModel) {
        itemsFlow.observe(this@CalendarMonthDialog) { list ->
            adapter?.submitList(list)
        }

        bottomFlow
            .filterNotNull()
            .observe(
                this@CalendarMonthDialog,
                binding.calendarMonthBottom::bindState
            )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }
}
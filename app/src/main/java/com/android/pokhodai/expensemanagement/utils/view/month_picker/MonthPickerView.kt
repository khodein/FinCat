package com.android.pokhodai.expensemanagement.utils.view.month_picker

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import com.android.pokhodai.expensemanagement.R
import com.android.pokhodai.expensemanagement.base.ui.adapter.BaseListAdapter
import com.android.pokhodai.expensemanagement.databinding.ItemMonthBinding
import com.android.pokhodai.expensemanagement.databinding.ViewMonthPickerBinding
import com.android.pokhodai.expensemanagement.utils.LocalDateFormatter
import com.android.pokhodai.expensemanagement.utils.decorations.GridSpacingItemDecoration

class MonthPickerView : FrameLayout {

    private val binding = ViewMonthPickerBinding.inflate(LayoutInflater.from(context), this, true)
    private val adapter by lazy { MonthAdapter() }

    private val list = mutableListOf<MonthAdapter.Month>()

    private var onClickMonthActionListener: ((LocalDateFormatter) -> Unit)? = null

    fun setOnClickMonthActionListener(action: (LocalDateFormatter) -> Unit) {
        onClickMonthActionListener = action
    }

    private val today = LocalDateFormatter.today()

    private var yearNow: LocalDateFormatter = today
        set(value) {
            field = value
            binding.txtYearMonthPicker.text = value.yyyy()
            genMonths()
        }

    var actualMonth: LocalDateFormatter = today
        set(value) {
            field = value
            yearNow = value.update { withMonth(1) }
            if (list.isNotEmpty()) {
                val monthsList = list.map {
                    it.copy(check = it.date.MMMM_yyyy() == value.MMMM_yyyy())
                }
                adapter.submitList(monthsList)
            }
        }

    override fun onSaveInstanceState(): Parcelable {
        return bundleOf(
            SUPER_STATE to super.onSaveInstanceState(),
            ACTUAL_MONTH to actualMonth
        )
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        var restoreState = state
        if (restoreState is Bundle) {
            val bundle = restoreState
            actualMonth = bundle.getParcelable(ACTUAL_MONTH) ?: LocalDateFormatter.today()
            restoreState = bundle.getParcelable(SUPER_STATE)
        }
        super.onRestoreInstanceState(restoreState)
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        setAdapter()
        setTitle()
        setListeners()
    }

    private fun setTitle() {
        binding.txtYearMonthPicker.text = yearNow.yyyy()
    }

    private fun setListeners() = with(binding) {
        adapter.setOnClickMonthActionListener {
            actualMonth = it
            onClickMonthActionListener?.invoke(it)
        }

        btnLeftMonthPicker.setOnClickListener {
            yearNow = yearNow.update { minusYears(1) }
        }

        btnRightMonthPicker.setOnClickListener {
            yearNow = yearNow.update { plusYears(1) }
        }
    }

    private fun genMonths() {
        list.clear()
        var date = yearNow
        repeat(12) {
            list.add(
                MonthAdapter.Month(
                    date,
                    check = actualMonth.MMMM_yyyy() == date.MMMM_yyyy()
                )
            )
            date = date.update { plusMonths(1) }
        }

        adapter.submitList(list)
    }

    private fun setAdapter() {
        binding.run {
            rvMonthPicker.itemAnimator = null
            rvMonthPicker.adapter = adapter
            rvMonthPicker.addItemDecoration(GridSpacingItemDecoration(3, 30, false))
        }
    }

    private class MonthAdapter : BaseListAdapter<MonthAdapter.Month>() {

        private var onClickMonthActionListener: ((LocalDateFormatter) -> Unit)? = null

        fun setOnClickMonthActionListener(action: (LocalDateFormatter) -> Unit) {
            onClickMonthActionListener = action
        }

        override fun build() {
            baseViewHolder(Month::class, ItemMonthBinding::inflate) { item ->
                binding.run {
                    txtMonth.text = item.date.MMMM()

                    if (item.check) {
                        root.setCardBackgroundColor(root.context.getColor(R.color.blue_500))
                        txtMonth.setTextColor(root.context.getColor(R.color.white))
                        root.setStrokeColor(root.context.getColorStateList(R.color.blue_500))
                    } else {
                        root.setCardBackgroundColor(root.context.getColor(R.color.white))
                        txtMonth.setTextColor(root.context.getColor(R.color.black))
                        root.setStrokeColor(root.context.getColorStateList(R.color.grey_300))
                    }

                    root.setOnClickListener {
                        if (item.check) {
                            return@setOnClickListener
                        }
                        onClickMonthActionListener?.invoke(item.date)
                    }
                }
            }
        }

        data class Month(
            val date: LocalDateFormatter,
            val check: Boolean
        )
    }

    companion object {
        private const val SUPER_STATE = "SUPER_STATE"
        private const val ACTUAL_MONTH = "ACTUAL_DATE"
    }
}
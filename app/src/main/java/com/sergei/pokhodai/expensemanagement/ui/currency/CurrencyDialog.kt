package com.sergei.pokhodai.expensemanagement.ui.currency

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.sergei.pokhodai.expensemanagement.base.ui.fragments.BaseBottomSheetDialogFragment
import com.sergei.pokhodai.expensemanagement.R
import com.sergei.pokhodai.expensemanagement.databinding.DialogCurrencyBinding
import com.sergei.pokhodai.expensemanagement.utils.dp
import com.sergei.pokhodai.expensemanagement.utils.enums.Currency
import com.sergei.pokhodai.expensemanagement.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrencyDialog :
    BaseBottomSheetDialogFragment<DialogCurrencyBinding>(DialogCurrencyBinding::inflate) {

    private val viewModel by viewModels<CurrencyViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            incDollarCurrency.run {
                ivCurrency.setImageResource(R.drawable.ic_dollar)
                txtNameCurrency.text = getString(R.string.dollar)
            }
            incRubleCurrency.run {
                ivCurrency.setImageResource(R.drawable.ic_ruble)
                txtNameCurrency.text = getString(R.string.ruble)
            }
        }
    }

    override fun setListeners() = with(binding) {
        incDollarCurrency.root.setOnClickListener {
            viewModel.onChangeCurrency(Currency.DOLLAR)
        }

        incRubleCurrency.root.setOnClickListener {
            viewModel.onChangeCurrency(Currency.RUBLE)
        }

        btnCurrency.setOnClickListener {
            viewModel.onClickCurrency()
            setFragmentResult(CURRENCY, bundleOf())
            navigationController.popBackStack()
        }
    }

    override fun setObservable() = with(viewModel) {
        validateFlow.observe(viewLifecycleOwner) {
            binding.btnCurrency.isEnabled = it
        }

        currencyFlow.observe(viewLifecycleOwner) { currency ->
            val blue = requireContext().getColor(R.color.blue_600)
            val grey = requireContext().getColor(R.color.grey_500)
            val oneDP = 1.dp.toInt()
            val twoDP = 2.dp.toInt()
            binding.run {
                when(currency) {
                    Currency.DOLLAR -> {
                        incDollarCurrency.run {
                            txtNameCurrency.setTextColor(blue)
                            root.strokeWidth = twoDP
                            root.strokeColor = blue
                        }
                        incRubleCurrency.run {
                            txtNameCurrency.setTextColor(grey)
                            root.strokeWidth = oneDP
                            root.strokeColor = grey
                        }
                    }
                    Currency.RUBLE -> {
                        incDollarCurrency.run {
                            txtNameCurrency.setTextColor(grey)
                            root.strokeWidth = oneDP
                            root.strokeColor = grey
                        }
                        incRubleCurrency.run {
                            txtNameCurrency.setTextColor(blue)
                            root.strokeWidth = twoDP
                            root.strokeColor = blue
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val CURRENCY = "CURRENCY"
    }
}
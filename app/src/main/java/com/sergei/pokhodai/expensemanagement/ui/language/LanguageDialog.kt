package com.sergei.pokhodai.expensemanagement.ui.language

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.sergei.pokhodai.expensemanagement.base.ui.fragments.BaseBottomSheetDialogFragment
import com.sergei.pokhodai.expensemanagement.R
import com.sergei.pokhodai.expensemanagement.databinding.DialogLanguageBinding
import com.sergei.pokhodai.expensemanagement.utils.dp
import com.sergei.pokhodai.expensemanagement.utils.enums.Language
import com.sergei.pokhodai.expensemanagement.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LanguageDialog :
    BaseBottomSheetDialogFragment<DialogLanguageBinding>(DialogLanguageBinding::inflate) {

    private val viewModel by viewModels<LanguageViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            incRussianLanguage.run {
                ivLanguage.setImageResource(R.drawable.ic_russian)
                txtNameLanguage.text = getString(R.string.russian)
            }
            incEnglishLanguage.run {
                ivLanguage.setImageResource(R.drawable.ic_english)
                txtNameLanguage.text = getString(R.string.english)
            }
        }
    }

    override fun setObservable() = with(viewModel) {
        validateFlow.observe(viewLifecycleOwner) {
            binding.btnLanguage.isEnabled = it
        }

        languageFlow.observe(viewLifecycleOwner) { language ->
            val blue = requireContext().getColor(R.color.blue_600)
            val grey = requireContext().getColor(R.color.grey_500)
            val oneDP = 1.dp.toInt()
            val twoDP = 2.dp.toInt()
            binding.run {
                when (language) {
                    Language.EU -> {
                        incEnglishLanguage.run {
                            txtNameLanguage.setTextColor(blue)
                            root.strokeWidth = twoDP
                            root.strokeColor = blue
                        }

                        incRussianLanguage.run {
                            txtNameLanguage.setTextColor(grey)
                            root.strokeColor = grey
                            root.strokeWidth = oneDP
                        }
                    }
                    Language.RU -> {
                        incEnglishLanguage.run {
                            root.strokeColor = grey
                            root.strokeWidth = oneDP
                            txtNameLanguage.setTextColor(grey)
                        }

                        incRussianLanguage.run {
                            root.strokeColor = blue
                            root.strokeWidth = twoDP
                            txtNameLanguage.setTextColor(blue)
                        }
                    }
                }
            }
        }
    }

    override fun setListeners() = with(binding) {
        btnLanguage.setOnClickListener {
            viewModel.onClickLanguage()
            setFragmentResult(LANGUAGE, bundleOf())
            navigationController.popBackStack()
        }

        incEnglishLanguage.root.setOnClickListener {
            viewModel.onChangeLanguage(Language.EU)
        }

        incRussianLanguage.root.setOnClickListener {
            viewModel.onChangeLanguage(Language.RU)
        }
    }

    companion object {
        const val LANGUAGE = "LANGUAGE"
    }
}
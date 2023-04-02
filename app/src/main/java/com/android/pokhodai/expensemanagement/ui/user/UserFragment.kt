package com.android.pokhodai.expensemanagement.ui.user

import android.annotation.SuppressLint
import android.util.Log
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.clearFragmentResultListener
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.android.pokhodai.expensemanagement.MainViewModel
import com.android.pokhodai.expensemanagement.base.ui.fragments.BaseFragment
import com.android.pokhodai.expensemanagement.data.models.User
import com.android.pokhodai.expensemanagement.databinding.FragmentUserBinding
import com.android.pokhodai.expensemanagement.ui.currency.CurrencyDialog
import com.android.pokhodai.expensemanagement.ui.language.LanguageDialog
import com.android.pokhodai.expensemanagement.ui.settings.SettingsViewModel
import com.android.pokhodai.expensemanagement.utils.ClickUtils.setOnThrottleClickListener
import com.android.pokhodai.expensemanagement.utils.navigateSafe
import com.android.pokhodai.expensemanagement.utils.observe
import com.android.pokhodai.expensemanagement.utils.setUniqueText
import com.android.pokhodai.expensemanagement.utils.showDatePickerDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : BaseFragment<FragmentUserBinding>(FragmentUserBinding::inflate) {

    private val viewModel by viewModels<UserViewModel>()
    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun onStart() {
        super.onStart()
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    override fun setListeners() = with(binding) {
        txtFirstNameUser.doAfterTextChanged {
            viewModel.onChangeFirstName(it.toString())
        }

        txtSecondNameUser.doAfterTextChanged {
            viewModel.onChangeSecondName(it.toString())
        }

        txtEmailUser.doAfterTextChanged {
            viewModel.onChangeEmail(it.toString())
        }

        btnCreaterUser.setOnClickListener {
            onChangeUser()
        }

        txtEmailUser.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (binding.btnCreaterUser.isEnabled) {
                    onChangeUser()
                } else {
                    windowInsetsController.hide(WindowInsetsCompat.Type.ime())
                }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        chipCurrency.setOnClickListener {
            navigationController.navigateSafe(UserFragmentDirections.actionUserFragmentToCurrencyDialog())
        }

        chipLanguage.setOnClickListener {
            navigationController.navigateSafe(UserFragmentDirections.actionUserFragmentToLanguageDialog())
        }

        chipBirth.setOnClickListener {
            childFragmentManager.showDatePickerDialog(
                viewModel.birthFlow.value
            ) {
                viewModel.onChangeBirth(it)
            }
        }

        setFragmentResultListener(LanguageDialog.LANGUAGE) { _, _ ->
            viewModel.onChangeLanguage()
        }

        setFragmentResultListener(CurrencyDialog.CURRENCY) { _, _ ->
            viewModel.onChangeCurrency()
        }
    }

    private fun onChangeUser() {
        windowInsetsController.hide(WindowInsetsCompat.Type.ime())
        mainViewModel.onChangeUser(
            User(
                firstName = viewModel.firstNameFlow.value,
                secondName = viewModel.secondNameFlow.value,
                email = viewModel.emailFlow.value,
                birth = viewModel.birthFlow.value
            )
        )
    }

    override fun onBackPressed() {
        requireActivity().finishAndRemoveTask()
    }

    @SuppressLint("SetTextI18n")
    override fun setObservable() = with(viewModel) {
        firstNameFlow.observe(viewLifecycleOwner) {
            binding.txtFirstNameUser.setUniqueText(it)
        }

        secondNameFlow.observe(viewLifecycleOwner) {
            binding.txtSecondNameUser.setUniqueText(it)
        }

        emailFlow.observe(viewLifecycleOwner) {
            binding.txtEmailUser.setUniqueText(it)
        }

        languageFlow.observe(viewLifecycleOwner) {
            binding.chipLanguage.text = "${it.name} - ${getString(it.nameResIs)}"
        }

        currencyFlow.observe(viewLifecycleOwner) {
            binding.chipCurrency.text = "${getString(it.nameResId)} ${getString(it.resId)}"
        }

        birthFlow.observe(viewLifecycleOwner) {
            binding.chipBirth.text = it.dd_MM_yyyy()
        }

        validate.observe(viewLifecycleOwner) {
            binding.btnCreaterUser.isEnabled = it
        }
    }
}
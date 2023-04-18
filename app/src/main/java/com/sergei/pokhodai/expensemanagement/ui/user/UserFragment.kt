package com.sergei.pokhodai.expensemanagement.ui.user

import android.annotation.SuppressLint
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.sergei.pokhodai.expensemanagement.base.ui.fragments.BaseFragment
import com.sergei.pokhodai.expensemanagement.data.models.User
import com.sergei.pokhodai.expensemanagement.repositories.LanguageRepository
import com.sergei.pokhodai.expensemanagement.ui.currency.CurrencyDialog
import com.sergei.pokhodai.expensemanagement.ui.language.LanguageDialog
import com.sergei.pokhodai.expensemanagement.utils.ClickUtils.setOnThrottleClickListener
import com.sergei.pokhodai.expensemanagement.utils.navigateSafe
import com.sergei.pokhodai.expensemanagement.utils.observe
import com.sergei.pokhodai.expensemanagement.utils.setUniqueText
import com.sergei.pokhodai.expensemanagement.utils.showDatePickerDialog
import com.sergei.pokhodai.expensemanagement.MainViewModel
import com.sergei.pokhodai.expensemanagement.databinding.FragmentUserBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UserFragment : BaseFragment<FragmentUserBinding>(FragmentUserBinding::inflate) {

    private val viewModel by viewModels<UserViewModel>()
    private val mainViewModel by activityViewModels<MainViewModel>()

    @Inject
    lateinit var languageRepository: LanguageRepository

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

        btnCreaterUser.setOnThrottleClickListener {
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
            windowInsetsController.hide(WindowInsetsCompat.Type.ime())
            navigationController.navigateSafe(UserFragmentDirections.actionUserFragmentToCurrencyDialog())
        }

        chipLanguage.setOnClickListener {
            windowInsetsController.hide(WindowInsetsCompat.Type.ime())
            navigationController.navigateSafe(UserFragmentDirections.actionUserFragmentToLanguageDialog())
        }

        chipBirth.setOnClickListener {
            windowInsetsController.hide(WindowInsetsCompat.Type.ime())
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
        mainViewModel.onClickExitApp()
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
            binding.chipBirth.text = it.dd_MM_yyyy(languageRepository.getLanguage())
        }

        validate.observe(viewLifecycleOwner) {
            binding.btnCreaterUser.isEnabled = it
        }
    }
}
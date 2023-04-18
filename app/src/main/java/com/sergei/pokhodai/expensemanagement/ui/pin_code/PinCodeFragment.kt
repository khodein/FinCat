package com.sergei.pokhodai.expensemanagement.ui.pin_code

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.os.*
import android.view.HapticFeedbackConstants
import android.view.View
import android.widget.ImageView
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.*
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.sergei.pokhodai.expensemanagement.base.ui.fragments.BaseFragment
import com.sergei.pokhodai.expensemanagement.utils.observe
import com.google.android.material.textview.MaterialTextView
import com.sergei.pokhodai.expensemanagement.MainViewModel
import com.sergei.pokhodai.expensemanagement.R
import com.sergei.pokhodai.expensemanagement.databinding.FragmentPinCodeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PinCodeFragment : BaseFragment<FragmentPinCodeBinding>(FragmentPinCodeBinding::inflate) {

    private val viewModel by viewModels<PinCodeViewModel>()
    private val mainViewModel by activityViewModels<MainViewModel>()

    private val biometricPrompt by lazy {
        BiometricPrompt(
            this,
            ContextCompat.getMainExecutor(requireContext()),
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    onSuccessCodeState()
                    mainViewModel.onEnterApp()
                }
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!viewModel.isInstallMode) {
            openBiometricPromptDialog()
        }
    }

    override fun setListeners() = with(binding) {
        fun MaterialTextView.numberClickListener() {
            setOnClickListener {
                it.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                viewModel.onClickNumberButton(text.first())
            }
        }

        txtOnePinCode.numberClickListener()
        txtTwoPinCode.numberClickListener()
        txtThreePinCode.numberClickListener()
        txtFourPinCode.numberClickListener()
        txtFivePinCode.numberClickListener()
        txtSixPinCode.numberClickListener()
        txtSevenPinCode.numberClickListener()
        txtEightPinCode.numberClickListener()
        txtNinePinCode.numberClickListener()
        txtZeroPinCode.numberClickListener()

        txtExitPinCode.setOnClickListener {
            it.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            requireActivity().finishAndRemoveTask()
        }

        btnFingerPinCode.setOnClickListener {
            it.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            openBiometricPromptDialog()
        }

        btnBackspacePinCode.setOnClickListener {
            it.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            viewModel.onClickBackspaceButton()
        }

        btnNextPinCode.setOnClickListener {
            mainViewModel.onEnterApp()
        }
    }

    override fun setObservable() = with(viewModel) {
        digitState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is PinCodeViewModel.DigitState.AddDigit -> updateCodeItem(state.index, true)
                is PinCodeViewModel.DigitState.RemoveDigit -> updateCodeItem(state.index, false)
            }
        }

        title.observe(viewLifecycleOwner) { stringRes ->
            binding.txtTitlePinCode.text = getString(stringRes)
        }

        authState.observe(viewLifecycleOwner) { state ->
            when (state) {
                PinCodeViewModel.PassCodeState.EmptyState -> onEmptyCodeState()
                PinCodeViewModel.PassCodeState.SuccessState -> onSuccessCodeState()
                PinCodeViewModel.PassCodeState.InstallState -> onInstallCodeState()
                PinCodeViewModel.PassCodeState.InstallErrorState -> onErrorCodeState()
                is PinCodeViewModel.PassCodeState.ErrorState -> onErrorCodeState()
                is PinCodeViewModel.PassCodeState.InputState -> onInputCodeState(state.code)
            }
        }
    }

    private fun onEmptyCodeState() = with(binding) {
        for (index in llColumnDotsPinCode.size downTo 1) {
            updateCodeItem(index - 1, false)
        }
    }

    private fun onSuccessCodeState() = with(binding) {
        for (index in llColumnDotsPinCode.indices) {
            updateCodeItem(index, true)
        }

        btnNextPinCode.isVisible = viewModel.code.length == PinCodeViewModel.CODE_LENGTH
    }

    private fun onInstallCodeState() = with(binding) {
        btnFingerPinCode.isVisible = false
    }

    private fun onInputCodeState(code: String) {
        code.indices.forEach {
            updateCodeItem(it, true)
        }
    }

    private fun updateCodeItem(index: Int, isAdded: Boolean) = with(binding) {
        (binding.llColumnDotsPinCode[index] as? ImageView)?.setColorFilter(
            if (isAdded) getColor(requireContext(), R.color.blue_600)
            else getColor(requireContext(), R.color.grey_400)
        )

        binding.btnBackspacePinCode.isVisible = index > 0 || isAdded
        binding.btnFingerPinCode.isVisible =
            index == 0 && !isAdded && !viewModel.isInstallMode && btnFingerPinCode.isEnabled
    }

    private fun onErrorCodeState() =
        with(binding) {
            llColumnDotsPinCode.forEach { view ->
                if (view !is ImageView) return@forEach

                val gradientAnimator = ValueAnimator.ofObject(
                    ArgbEvaluator(),
                    getColor(requireContext(), R.color.blue_600),
                    getColor(requireContext(), R.color.red_600)
                ).apply {
                    addUpdateListener {
                        view.setColorFilter(it.animatedValue as Int)
                    }

                    duration = ERROR_DURATION
                }

                val scaleFromAnimator = ValueAnimator.ofFloat(1f, 1.5f).apply {
                    addUpdateListener {
                        val scale = it.animatedValue as Float

                        view.scaleX = scale
                        view.scaleY = scale
                    }
                    duration = ERROR_DURATION
                }

                val scaleToAnimator = scaleFromAnimator.clone().apply {
                    setFloatValues(1.5f, 1f)
                }

                AnimatorSet().apply {
                    play(gradientAnimator).with(scaleFromAnimator)
                    play(scaleToAnimator).after(scaleFromAnimator)
                    doOnEnd {
                        viewModel.onErrorPassCode()
                    }
                }.start()

                vibrate()
            }
        }

    private fun vibrate() {
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            (requireContext().getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager).defaultVibrator
        } else {
            requireContext().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }

        val canVibrate: Boolean = vibrator.hasVibrator()
        val milliseconds = ERROR_DURATION * 2

        if (canVibrate) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        milliseconds,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else {
                vibrator.vibrate(milliseconds)
            }
        }
    }

    private fun openBiometricPromptDialog() {
        val canAuthenticate = BiometricManager.from(requireContext())
            .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)

        val isCanAuthenticate = canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS

        binding.btnFingerPinCode.isEnabled = isCanAuthenticate
        binding.btnFingerPinCode.isVisible = isCanAuthenticate

        if (isCanAuthenticate) {
            val promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle(getString(R.string.pin_code_bio_title))
                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_WEAK)
                .setNegativeButtonText(getString(R.string.cancel))
                .build()

            biometricPrompt.authenticate(promptInfo)
        }
    }

    companion object {
        private const val ERROR_DURATION = 200L
    }
}
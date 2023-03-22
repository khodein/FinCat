package com.android.pokhodai.expensemanagement.base.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.annotation.CallSuper
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import androidx.viewbinding.ViewBinding
import com.android.pokhodai.expensemanagement.NavigationViewModel

abstract class BaseFragment<VB : ViewBinding>(
    private val vbInflate: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : Fragment() {

    protected open val isBnvVisible = true

    protected val navViewModel by activityViewModels<NavigationViewModel>()

    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding!!

    protected open val navigationController by lazy { findNavController() }

    protected val windowInsetsController by lazy {
        WindowInsetsControllerCompat(
            requireActivity().window,
            binding.root
        )
    }

    protected open fun onCreateView() {}
    protected open fun initToolbar() {}
    protected open fun setListeners() {}
    protected open fun setObservable() {}
    protected open fun setAdapter() {}

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val transition =
            TransitionInflater
                .from(requireContext())
                .inflateTransition(android.R.transition.move)
                .setDuration(250L)

        sharedElementEnterTransition = transition
        sharedElementReturnTransition = transition
        _binding = vbInflate(inflater, container, false)
        return binding.root
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initOnBackPressedDispatcher()
        setBnvVisible()
        setListeners()
        setObservable()
        setAdapter()
    }

    protected open fun initOnBackPressedDispatcher() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            onBackPressed()
        }
    }

    private fun setBnvVisible() {
        navViewModel.onChangeBnvVisible(isBnvVisible)
    }

    protected open fun onBackPressed() {
        navigationController.popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
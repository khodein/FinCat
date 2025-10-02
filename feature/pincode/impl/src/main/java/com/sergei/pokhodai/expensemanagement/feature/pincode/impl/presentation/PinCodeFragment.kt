package com.sergei.pokhodai.expensemanagement.feature.pincode.impl.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.sergei.pokhodai.expensemanagement.core.base.utils.viewBinding
import com.sergei.pokhodai.expensemanagement.core.recycler.adapter.RecyclerAdapter
import com.sergei.pokhodai.expensemanagement.feature.pincode.impl.R
import com.sergei.pokhodai.expensemanagement.feature.pincode.impl.databinding.FragmentPinCodeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class PinCodeFragment : Fragment(R.layout.fragment_pin_code) {

    private val binding by viewBinding(init = FragmentPinCodeBinding::bind)
    private val viewModel by viewModel<PinCodeViewModel>()
    private var adapter: RecyclerAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setAdapter() {

    }
}
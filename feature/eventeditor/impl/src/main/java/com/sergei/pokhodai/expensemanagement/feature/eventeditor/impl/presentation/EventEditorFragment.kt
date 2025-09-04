package com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import com.sergei.pokhodai.expensemanagement.core.base.utils.autoClean
import com.sergei.pokhodai.expensemanagement.core.base.utils.viewBinding
import com.sergei.pokhodai.expensemanagement.core.recycler.adapter.RecyclerAdapter
import com.sergei.pokhodai.expensemanagement.core.base.utils.applyPadding
import com.sergei.pokhodai.expensemanagement.core.base.utils.observe
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl.R
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl.databinding.FragmentEventEditorBinding
import kotlinx.coroutines.flow.filterNotNull
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class EventEditorFragment : Fragment(R.layout.fragment_event_editor) {
    private val binding by viewBinding(init = FragmentEventEditorBinding::bind)
    private val viewModel by viewModel<EventEditorViewModel>()
    private val adapter by autoClean(init = ::RecyclerAdapter)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setObservable()
    }

    private fun setAdapter() {
        binding.editorEventList.adapter = adapter
        binding.editorEventList.itemAnimator = null
    }

    private fun setObservable() = with(viewModel) {
        toolbarFlow
            .filterNotNull()
            .observe(
                this@EventEditorFragment,
                binding.editorEventToolbar::bindState
            )

        itemFlow.observe(
            this@EventEditorFragment,
            adapter::submitList
        )

        buttonFlow
            .filterNotNull()
            .observe(this@EventEditorFragment) { state ->
                binding.editorEventButton.run {
                    bindState(state)
                    doOnLayout {
                        binding.editorEventList.applyPadding(bottom = it.height)
                    }
                }
            }
    }
}
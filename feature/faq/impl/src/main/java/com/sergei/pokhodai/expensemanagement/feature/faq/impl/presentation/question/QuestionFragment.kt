package com.sergei.pokhodai.expensemanagement.feature.faq.impl.presentation.question

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.core.view.doOnLayout
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import com.sergei.pokhodai.expensemanagement.core.base.utils.observe
import com.sergei.pokhodai.expensemanagement.core.base.utils.viewBinding
import com.sergei.pokhodai.expensemanagement.core.recycler.adapter.RecyclerAdapter
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.R
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.databinding.FragmentQuestionBinding
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.presentation.question.state.QuestionMailState
import kotlinx.coroutines.flow.filterNotNull
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class QuestionFragment : Fragment(R.layout.fragment_question) {
    private val binding by viewBinding(init = FragmentQuestionBinding::bind)
    private val viewModel by viewModel<QuestionViewModel>()
    private var adapter: RecyclerAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setObservable()
    }

    private fun setObservable() = with(viewModel) {
        itemsFlow.observe(this@QuestionFragment) { list ->
            adapter?.submitList(list)
        }

        topFlow
            .filterNotNull()
            .observe(this@QuestionFragment, binding.questionTop::bindState)

        bottomFlow
            .filterNotNull()
            .observe(this@QuestionFragment) { state ->
                binding.questionBottomTop.bindState(state.topButtonState)
                binding.questionBottomBottom.bindState(state.bottomButtonState)
                binding.questionBottom.doOnLayout { view ->
                    binding.questionItems.updatePadding(bottom = view.height)
                }
            }

        emailDeveloperFlow
            .filterNotNull()
            .observe(
                this@QuestionFragment,
                ::goToEmailClient
            )
    }

    private fun goToEmailClient(
        questionMailState: QuestionMailState
    ) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = "mailto:".toUri()
            putExtra(Intent.EXTRA_EMAIL, arrayOf(questionMailState.email))
            putExtra(Intent.EXTRA_SUBJECT, questionMailState.title)
            putExtra(Intent.EXTRA_TEXT, questionMailState.message)
        }
        val chooserIntent = Intent.createChooser(intent, questionMailState.title).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        requireActivity().startActivity(chooserIntent)
    }

    private fun setAdapter() {
        adapter = RecyclerAdapter()
        binding.questionItems.adapter = adapter
        binding.questionItems.itemAnimator = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }
}
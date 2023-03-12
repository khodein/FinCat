package com.android.pokhodai.expensemanagement.ui.settings.manager

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import com.android.pokhodai.expensemanagement.base.ui.fragments.BaseFragment
import com.android.pokhodai.expensemanagement.databinding.FragmentManagerCategoriesBinding
import com.android.pokhodai.expensemanagement.ui.settings.manager.adapter.ManagerCategoriesAdapter
import com.android.pokhodai.expensemanagement.utils.observe
import com.android.pokhodai.expensemanagement.utils.touch_helpers.ItemTouchHelperCallback
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ManagerCategoriesFragment :
    BaseFragment<FragmentManagerCategoriesBinding>(FragmentManagerCategoriesBinding::inflate) {

    override val isBnvVisible: Boolean = false

    @Inject
    lateinit var adapter: ManagerCategoriesAdapter

    @Inject
    lateinit var helperCallback: ItemTouchHelperCallback

    private val viewModel by viewModels<ManagerCategoriesViewModel>()

    override fun setListeners() = with(binding) {
        srlManagerCategories.setOnRefreshListener {
            viewModel.onSwipeRefresh()
        }

        tbManagerCategories.setNavigationOnClickListener {
            onBackPressed()
        }

        helperCallback.setOnItemDismissActionListener {position ->
            viewModel.onDeleteExpense(position)
            adapter.notifyItemRemoved(position)
        }

        helperCallback.setOnMoveActionListener { oldPosition, newPosition ->
            viewModel.onSwapAndUpdateExpenses(oldPosition, newPosition)
            adapter.notifyItemMoved(oldPosition, newPosition)
        }
    }

    override fun setAdapter() = with(binding) {
        rvManagerCategories.adapter = adapter
        ItemTouchHelper(helperCallback).run {
            attachToRecyclerView(rvManagerCategories)
        }
    }

    override fun setObservable() = with(viewModel) {
        refreshFlow.observe(viewLifecycleOwner) {
            binding.srlManagerCategories.isRefreshing = it
        }

        expensesFlow.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}
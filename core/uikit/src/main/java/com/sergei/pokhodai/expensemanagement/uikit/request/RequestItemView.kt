package com.sergei.pokhodai.expensemanagement.uikit.request

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewDimension
import com.sergei.pokhodai.expensemanagement.core.base.utils.applyPadding
import com.sergei.pokhodai.expensemanagement.core.base.utils.dp
import com.sergei.pokhodai.expensemanagement.core.base.utils.getColor
import com.sergei.pokhodai.expensemanagement.core.base.utils.resolveToLayoutParams
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerItemView
import com.sergei.pokhodai.expensemanagement.core.uikit.R
import com.sergei.pokhodai.expensemanagement.core.base.R as baseR
import com.sergei.pokhodai.expensemanagement.core.uikit.databinding.ViewRequestErrorEmptyBinding
import com.sergei.pokhodai.expensemanagement.uikit.button.ButtonItem
import com.sergei.pokhodai.expensemanagement.uikit.loading.LoadingItemView

class RequestItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), RecyclerItemView<RequestItem.State> {

    private var state: RequestItem.State? = null
    private val viewsMap: HashMap<String, View> = hashMapOf()
    private var emptyBinding: ViewRequestErrorEmptyBinding? = null
    private var errorBinding: ViewRequestErrorEmptyBinding? = null

    private val errorButtonState by lazy {
        ButtonItem.State(
            provideId = "request_error_id",
            radius = ViewDimension.Dp(12),
            height = ViewDimension.Dp(40),
            value = context.getString(R.string.reload),
            fill = ButtonItem.Fill.Outline(),
            onClick = ::onClickReload
        )
    }

    private fun onClickReload(state: ButtonItem.State) {
        this.state?.let {
            if (it is RequestItem.State.Error) {
                it.onClickReload?.invoke()
            }
        }
    }

    init {
        layoutParams = LayoutParams(
            MATCH_PARENT,
            MATCH_PARENT,
        )
    }

    override fun bindState(state: RequestItem.State) {
        this.state = state

        bindContainer(state.container)

        removeAllViews()
        when (state) {
            is RequestItem.State.Loading -> {
                val view = viewsMap.getOrPut(state.provideId) {
                    getLoadingView()
                }
                view
            }

            is RequestItem.State.Empty -> {
                val view = viewsMap.getOrPut(state.provideId) {
                    getEmptyView()
                }
                emptyBinding?.run {
                    requestErrorEmptyMessage.text = state.message
                    requestErrorEmptyBtn.isVisible = false
                }
                view
            }

            is RequestItem.State.Error -> {
                val view = viewsMap.getOrPut(state.provideId) {
                    getErrorView()
                }
                errorBinding?.run {
                    requestErrorEmptyMessage.text = state.message
                }
                view
            }

            is RequestItem.State.Idle -> null
        }
            ?.let(::addView)
    }

    private fun getEmptyView(): View {
        val binding = ViewRequestErrorEmptyBinding.inflate(LayoutInflater.from(context))
        emptyBinding = binding
        binding.requestErrorEmptyBtn.isVisible = false
        return binding.root.apply {
            layoutParams = LayoutParams(
                MATCH_PARENT,
                WRAP_CONTENT,
            ).apply {
                gravity = Gravity.CENTER
            }
        }
    }

    private fun getErrorView(): View {
        val binding = ViewRequestErrorEmptyBinding.inflate(LayoutInflater.from(context)).apply {
            requestErrorEmptyBtn.bindState(errorButtonState)
        }
        errorBinding = binding
        return binding.root.apply {
            layoutParams = LayoutParams(
                MATCH_PARENT,
                WRAP_CONTENT
            ).apply {
                gravity = Gravity.CENTER
            }
        }
    }

    private fun getLoadingView(): View {
        return LoadingItemView(context).apply {
            layoutParams = LayoutParams(
                WRAP_CONTENT,
                WRAP_CONTENT,
            ).apply {
                gravity = Gravity.CENTER
            }
        }
    }

    private fun bindContainer(container: RequestItem.Container) {
        resolveToLayoutParams(
            width = container.width,
            height = container.height,
        )
        setBackgroundColor(container.backgroundColor.getColor(context))
        applyPadding(container.paddings)
    }
}
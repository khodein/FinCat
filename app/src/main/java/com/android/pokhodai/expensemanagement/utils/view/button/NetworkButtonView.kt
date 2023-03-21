package com.android.pokhodai.expensemanagement.utils.view.button

import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.os.bundleOf
import androidx.core.view.doOnLayout
import com.android.pokhodai.expensemanagement.R
import com.android.pokhodai.expensemanagement.databinding.ViewNetworkButtonBinding
import com.android.pokhodai.expensemanagement.utils.ClickUtils.setOnThrottleClickListener
import com.android.pokhodai.expensemanagement.utils.UIState
import com.android.pokhodai.expensemanagement.utils.dp

class NetworkButtonView : FrameLayout {

    private val binding =
        ViewNetworkButtonBinding.inflate(LayoutInflater.from(context), this, true)

    private var progressWidth: Int = 0
    private var btnWidth: Int = 0
    private var normalCorners = 40.dp
    private var loadingCorners = 40.dp
    private val doneDrawable = R.drawable.ic_send
    private val errorDrawable = R.drawable.ic_cant_send
    private var onEndAnimateDoneListener: (() -> Unit)? = null
    private var onEndAnimateErrorListener: (() -> Unit)? = null
    private var onThrottleClickListener: ((View) -> Unit)? = null

    var state: State? = null
        set(value) {
            field = value
            resizeByState()
        }

    var text: String = ""
        set(value) {
            field = value
            initText()
        }

    fun <T> setUIState(uiState: UIState<T>) {
        when (uiState) {
            is UIState.UILoading -> state = State.LOADING
            is UIState.UIError -> state = State.ERROR
            is UIState.UIPreSuccess -> state = State.DONE
            else -> Unit
        }
    }

    override fun setOnClickListener(l: OnClickListener?) {
        onThrottleClickListener = if (l != null) l::onClick else null
    }

    fun setOnThrottleClickListener(action: (View) -> Unit) {
        onThrottleClickListener = action
    }

    private fun setListeners() = with(binding) {
        btnCustom.setOnThrottleClickListener {
            if (state == State.DONE || state == State.ERROR || state == State.LOADING) {
                return@setOnThrottleClickListener
            }
            onThrottleClickListener?.invoke(it)
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        return bundleOf(
            SUPER_STATE to super.onSaveInstanceState(),
            TEXT to text
        )
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        var restoreState = state
        if (restoreState is Bundle) {
            val bundle = restoreState
            text = bundle.getString(TEXT) ?: ""
            restoreState = bundle.getParcelable(SUPER_STATE)
        }
        super.onRestoreInstanceState(restoreState)
    }

    fun setOnEndAnimateDoneListener(action: () -> Unit) {
        onEndAnimateDoneListener = action
    }

    fun setOnEndAnimateErrorListener(action: () -> Unit) {
        onEndAnimateErrorListener = action
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val a =
            context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.NetworkButtonView,
                defStyleAttr,
                0
            )

        text = a.getString(R.styleable.NetworkButtonView_android_text) ?: ""

        isEnabled = a.getBoolean(R.styleable.NetworkButtonView_android_enabled, true)

        setListeners()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        binding.btnCustom.isEnabled = isEnabled
    }

    init {
        binding.cpiCustom.doOnLayout {
            progressWidth = it.width
        }

        binding.btnCustom.doOnLayout {
            btnWidth = it.width
        }
    }

    private fun initText() = with(binding) {
        if (state == State.NORMAL || state == null) btnCustom.text = text
    }

    private fun resizeByState() {
        when (state) {
            State.NORMAL -> setResizeButton(progressWidth, btnWidth)
            State.LOADING -> setResizeButton(btnWidth, progressWidth)
            State.DONE -> setDoneButton()
            State.ERROR -> setErrorButton()
            else -> Unit
        }
    }

    private fun setErrorButton() = with(binding) {
        ivCustom.setImageResource(errorDrawable)
        cpiCustom.alpha = 0f
        ivCustom.animate().alpha(1f).withEndAction {
            ivCustom.animate().alpha(0f).setDuration(350).withEndAction {
                state = State.NORMAL
                onEndAnimateErrorListener?.invoke()
            }
        }
    }

    private fun setDoneButton() = with(binding) {
        ivCustom.setImageResource(doneDrawable)
        cpiCustom.alpha = 0f
        ivCustom.animate().alpha(1f).withEndAction {
            ivCustom.animate().alpha(0f).setDuration(350).withEndAction {
                state = State.NORMAL
                onEndAnimateDoneListener?.invoke()
            }
        }
    }

    private fun setResizeButton(currentWidth: Int, newWidth: Int) = with(binding) {
        val params = btnCustom.layoutParams
        ValueAnimator.ofInt(currentWidth, newWidth).setDuration(350).apply {
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener { animation: ValueAnimator ->
                val value = animation.animatedValue as Int
                params.width = value
                btnCustom.layoutParams = params
                btnCustom.requestLayout()
            }
            doOnStart {
                doOnResizeStart()
            }
            doOnEnd {
                doOnResizeEnd()
            }
            start()
        }
    }

    private fun doOnResizeEnd() = with(binding) {
        when (state) {
            State.NORMAL -> {
                btnCustom.text = text
                state = null
            }
            State.LOADING -> {
                cpiCustom.animate().alpha(1f)
            }
            else -> Unit
        }
    }

    private fun doOnResizeStart() = with(binding) {
        when (state) {
            State.NORMAL -> {
                btnCustom.cornerRadius = normalCorners.toInt()
                cpiCustom.alpha = 0f
            }
            State.LOADING -> {
                btnCustom.text = ""
                btnCustom.cornerRadius = loadingCorners.toInt()
            }
            else -> Unit
        }
    }

    enum class State {
        NORMAL,
        LOADING,
        ERROR,
        DONE
    }

    companion object {
        private const val SUPER_STATE = "SUPER_STATE"
        private const val TEXT = "TEXT"
    }
}
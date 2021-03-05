package ru.meseen.dev.androidacademy.data.retrofit

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.textview.MaterialTextView
import ru.meseen.dev.androidacademy.R

class Banner(val view: MaterialTextView) : MaterialTextBanner {
    
    private var isShow = false
    private var state: ConnectionState = ConnectionState.Connected.Network("Connected")

    companion object {
        const val TAG = "Banner"
    }

    private fun show(state: ConnectionState) {
        Log.d(TAG, "show: ")
        if (!isShow) {
            customize(state)
            StatusBanner.show(view = view)
            isShow = true
        } else {
            customize(state)
        }
    }

    private fun dismiss(state: ConnectionState) {
        Log.d(TAG, "dismiss: ")

        if (isShow) {
            customize(state)
            StatusBanner.dismiss(view = view)
            isShow = false
        } else {
            customize(state)
        }
    }

    private fun customize(state: ConnectionState) {
        val connectedDrawableId = R.drawable.ic_round_signal_wifi_4_bar_24
        val disconnectedDrawableId =
            R.drawable.ic_round_signal_wifi_statusbar_connected_no_internet_4_24

        val connectedColor =
            ResourcesCompat.getDrawable(view.context.resources, R.color.colorPrimary, null)
        val disconnectedColor =
            ResourcesCompat.getDrawable(view.context.resources, R.color.colorAccent, null)
        
        when (state) {

            is ConnectionState.Error.Network -> {
                bind(state.error, disconnectedDrawableId, disconnectedColor)
            }
            is ConnectionState.Connected.Network -> {
                bind(state.status, connectedDrawableId, connectedColor)
            }

            is ConnectionState.Error.Download -> {
                bind(state.error, disconnectedDrawableId, disconnectedColor)
            }
            is ConnectionState.Connected.Download -> {
                bind(state.status, connectedDrawableId, connectedColor)
            }
        }
    }

    private fun bind(text: String, @DrawableRes drawableId: Int, backgroundColor: Drawable?) {
        Log.d(TAG, "bind: $text")
        view.text = text
        view.setCompoundDrawablesWithIntrinsicBounds(
            drawableId,
            0,
            0,
            0
        )
        view.background = backgroundColor
    }

    override fun submitState(newState: ConnectionState) {
        if ((state is ConnectionState.Connected.Network || state is ConnectionState.Connected.Download)
            && (newState is ConnectionState.Error.Network || newState is ConnectionState.Error.Download)
        ) {
            state = newState
            show(newState)
        } else if (state is ConnectionState.Error.Download && newState is ConnectionState.Connected.Download) {
            state = newState
            dismiss(newState)
        } else if (state is ConnectionState.Error.Network && newState is ConnectionState.Connected.Network) {
            state = newState
            dismiss(newState)
        } else if (state is ConnectionState.Error.Network && newState is ConnectionState.Error.Download) {
            state = newState
            show(newState)
        }

    }
}

interface MaterialTextBanner {
    fun submitState(newState: ConnectionState)
}
object StatusBanner {

    private const val DURATION = 300L

    fun show(view: MaterialTextView) {
        ValueAnimator.ofFloat(
            view.measuredHeight.toFloat(),
            view.context.resources.getDimension(R.dimen.status_height)
        ).apply {
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    view.visibility = View.VISIBLE
                }

            })
            addUpdateListener {
                val height = (it.animatedValue as Float).toInt()
                val params = view.layoutParams
                params.height = height
                view.layoutParams = params

            }
            repeatCount = 0
            repeatMode = ValueAnimator.RESTART
            interpolator = AccelerateDecelerateInterpolator()
            duration = DURATION
        }.start()
    }

    fun dismiss(view: MaterialTextView) {
        ValueAnimator.ofFloat(
            view.measuredHeight.toFloat(),
            0f
        ).apply {
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    view.visibility = View.GONE
                }

            })
            addUpdateListener {
                val height = (it.animatedValue as Float).toInt()
                val params = view.layoutParams
                params.height = height
                view.layoutParams = params

            }
            repeatCount = 0
            repeatMode = ValueAnimator.RESTART
            interpolator = AccelerateDecelerateInterpolator()
            duration = DURATION
            startDelay = 1000
        }.start()

    }

}
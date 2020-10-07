package com.fastnews.extension

import android.animation.Animator
import android.view.ViewPropertyAnimator

fun ViewPropertyAnimator.animationEndCallback(end: () -> Unit) {
    this.setDuration(500)
        .setListener( object : Animator.AnimatorListener {
        override fun onAnimationStart(animator: Animator) {/*Not Caled*/}

        override fun onAnimationEnd(animator: Animator) { end() }

        override fun onAnimationCancel(animator: Animator) {/*Not Caled*/}

        override fun onAnimationRepeat(animator: Animator) {/*Not Caled*/}
    })
}
package com.android.shawnclisby.thetodolist.util

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat.getSystemService
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce


fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.lowBounceStiffnessTranslationY(position: Float) {
    SpringAnimation(this, DynamicAnimation.TRANSLATION_Y, position).apply {
        spring.dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
        spring.stiffness = SpringForce.STIFFNESS_VERY_LOW
        start()
    }
}

fun View.animateByTranslationYAlphaShow(byPosition: Float) {
    this.animate().translationYBy(byPosition).alpha(1f).start()
}

fun View.animateByTranslationYAlphaHide(byPosition: Float) {
    this.animate().translationYBy(byPosition).alpha(0f).start()
}

fun Int.toDps(context: Context): Int {
    val resources = context.resources
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        resources.displayMetrics
    ).toInt()
}

fun Float.toDps(context: Context): Float {
    val resources = context.resources
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        resources.displayMetrics
    )
}

fun EditText.showKeyboard(context: Context) {
    val imm: InputMethodManager? =
        getSystemService(context, InputMethodManager::class.java)
    imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun EditText.hideKeyboard(context: Context) {
    val imm: InputMethodManager? =
        getSystemService(context, InputMethodManager::class.java)
    imm?.hideSoftInputFromWindow(this.windowToken, 0)
}
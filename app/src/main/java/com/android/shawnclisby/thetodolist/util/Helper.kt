package com.android.shawnclisby.thetodolist.util

import android.content.Context
import android.util.TypedValue
import android.view.View

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun Int.toDps(context: Context): Int {
    val resources = context.resources
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        resources.displayMetrics
    ).toInt()
}
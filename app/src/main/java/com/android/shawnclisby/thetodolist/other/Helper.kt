package com.android.shawnclisby.thetodolist.other

import android.content.Context
import android.util.TypedValue

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
package com.android.shawnclisby.thetodolist.data.models

sealed class Priority {
    object NONE : Priority()
    object LOW : Priority()
    object MEDIUM : Priority()
    object HIGH : Priority()
    object HIGHEST : Priority()
}

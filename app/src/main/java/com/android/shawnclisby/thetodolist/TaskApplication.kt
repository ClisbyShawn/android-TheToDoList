package com.android.shawnclisby.thetodolist

import android.app.Application
import com.android.shawnclisby.androidauth.network.AuthHTTP
import com.android.shawnclisby.androidauth.network.TokenEntry
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TaskApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        TokenEntry.init(this, "someFileName", "someToken")
        AuthHTTP.init("http://www.someurl.com/somepath/")
    }
}
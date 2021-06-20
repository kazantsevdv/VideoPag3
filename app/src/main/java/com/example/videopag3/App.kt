package com.example.videopag3

import android.app.Application
import com.example.videopag3.di.AppComponent
import com.example.videopag3.di.AppModule
import com.example.videopag3.di.DaggerAppComponent

class App : Application() {
    private var _appComponent: AppComponent? = null

    val appComponent: AppComponent
        get() = checkNotNull(_appComponent)

    override fun onCreate() {
        super.onCreate()
        instance = this
        _appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    companion object {
        lateinit var instance: App
        val component get() = instance.appComponent
    }

}
package com.example.videopag3.di

import com.example.videopag3.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val app: App) {


    @Singleton
    @Provides
    fun app(): App {
        return app
    }

}
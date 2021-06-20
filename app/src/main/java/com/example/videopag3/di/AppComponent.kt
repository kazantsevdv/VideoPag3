package com.example.videopag3.di

import com.example.videopag3.view.VideoItemFragment
import com.example.videopag3.view.VideosFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AppModule::class,
        ApiModule::class,
        RepoModule::class,
        GlideModule::class
    ]
)
interface AppComponent {
    fun inject(fragment: VideosFragment)
    fun inject(fragment: VideoItemFragment)

}
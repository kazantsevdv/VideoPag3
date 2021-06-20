package com.example.videopag3.di

import android.widget.ImageView
import com.example.videopag3.repo.image.ImageLoader
import com.example.videopag3.repo.image.IImageLoader
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class GlideModule {
    @Named("imgUrl")
    @Provides
    fun imgUrl() = "https://image.tmdb.org/t/p/w500"

    @Singleton
    @Provides
    fun provideImageLoader(@Named("imgUrl") imgURL: String): IImageLoader<ImageView> =
        ImageLoader(imgURL)

}
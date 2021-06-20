package com.example.videopag3.di

import com.example.videopag3.repo.IVideosRepo
import com.example.videopag3.repo.VideosRepoImp
import com.example.videopag3.repo.api.IDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule {

    @Singleton
    @Provides
    fun provideVideosRepo(
        api: IDataSource,
        ): IVideosRepo = VideosRepoImp(api)

}
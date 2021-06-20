package com.example.videopag3.repo

import com.example.videopag3.repo.api.IDataSource

class VideosRepoImp(
    private val api: IDataSource,

    ) : IVideosRepo {


    override suspend fun getVideos(page: Int) = api.getMoves(page)

    override suspend fun getVideo(id: Int) = api.getMove(id)


}

package com.example.videopag3.repo.api

import com.example.videopag3.repo.model.Discover
import com.example.videopag3.repo.model.Video
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IDataSource {

    @GET("discover/movie")
    suspend fun getMoves(@Query("page") page: Int): Response<Discover>

    @GET("movie/{movie_id}")
    suspend fun getMove(@Path("movie_id") movie_id: Int): Response<Video>

}

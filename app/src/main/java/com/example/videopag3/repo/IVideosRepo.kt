package com.example.videopag3.repo

import com.example.videopag3.repo.model.Discover
import com.example.videopag3.repo.model.Video
import retrofit2.Response

interface IVideosRepo {
  suspend  fun getVideos(page : Int): Response<Discover>
  suspend  fun getVideo(id:Int): Response<Video>

}
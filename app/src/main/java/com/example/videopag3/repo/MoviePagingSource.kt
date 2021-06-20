package com.example.videopag3.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.videopag3.repo.model.VideosItem
import dagger.assisted.AssistedInject
import retrofit2.HttpException

class MoviePagingSource @AssistedInject constructor(
    val api: IVideosRepo,
) : PagingSource<Int, VideosItem>() {
    override fun getRefreshKey(state: PagingState<Int, VideosItem>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VideosItem> {
        try {

            val nextPage = params.key ?: 1
            val response = api.getVideos(nextPage)

            if (response.isSuccessful) {
                return LoadResult.Page(
                    data = response.body()!!.results,
                    prevKey = if (nextPage == 1) null else nextPage - 1,
                    nextKey = if (nextPage == response.body()!!.total_pages) null else response.body()!!.page + 1
                )
            } else {
                return LoadResult.Error(HttpException(response))
            }


        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}
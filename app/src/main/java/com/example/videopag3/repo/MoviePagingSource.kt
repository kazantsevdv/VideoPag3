package com.example.videopag3.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.videopag3.repo.model.VideosItem
import retrofit2.HttpException

class MoviePagingSource(
    private val api: IVideosRepo,
) : PagingSource<Int, VideosItem>() {
    override fun getRefreshKey(state: PagingState<Int, VideosItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)

        }


    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VideosItem> {
        try {

            val nextPage = params.key ?: 1
            val response = api.getVideos(nextPage)

            return if (response.isSuccessful) {
                LoadResult.Page(
                    data = response.body()!!.results,
                    prevKey = if (nextPage == 1) null else response.body()!!.page - 1,
                    nextKey = if (nextPage > response.body()!!.total_pages) null else response.body()!!.page + 1
                )
            } else {
                LoadResult.Error(HttpException(response))
            }


        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}
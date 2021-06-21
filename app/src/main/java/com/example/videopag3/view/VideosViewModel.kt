package com.example.videopag3.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.videopag3.repo.IVideosRepo
import com.example.videopag3.repo.MoviePagingSource
import com.example.videopag3.repo.model.VideosItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Provider


class VideosViewModel @Inject constructor(private val repository: IVideosRepo) : ViewModel() {
    val movies: Flow<PagingData<VideosItem>> = Pager(PagingConfig(pageSize = 20)) {
        MoviePagingSource(repository)
    }.flow


   @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val viewModerProvider: Provider<VideosViewModel>
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            require(modelClass == VideosViewModel::class.java)
            return viewModerProvider.get() as T
        }
    }
}

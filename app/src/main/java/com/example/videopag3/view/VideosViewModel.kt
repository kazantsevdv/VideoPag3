package com.example.videopag3.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.videopag3.repo.IVideosRepo
import com.example.videopag3.repo.MoviePagingSource
import com.example.videopag3.repo.model.VideosItem
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Provider


class VideosViewModel @Inject constructor(private val repository: IVideosRepo) : ViewModel() {
    val movies: Flow<PagingData<VideosItem>> = Pager(PagingConfig(pageSize = 20)) {
       MoviePagingSource(repository)
    }.flow.cachedIn(viewModelScope)



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

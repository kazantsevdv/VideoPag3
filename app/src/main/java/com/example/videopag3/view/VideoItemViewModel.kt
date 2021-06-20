package com.example.videopag3.view

import androidx.lifecycle.*
import com.example.videopag3.model.AppState
import com.example.videopag3.repo.IVideosRepo
import com.example.videopag3.repo.model.Video
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider


class VideoItemViewModel @Inject constructor(private val repository: IVideosRepo) : ViewModel() {
    private var _data = MutableLiveData<AppState<Video>>()
    var data: LiveData<AppState<Video>> = _data



    fun getVideoInfo(id:Int) {
        _data.value = AppState.Loading(null)

        viewModelScope.launch {
            try {

                val data = repository.getVideo(id)
                    _data.value = AppState.Success(data.body()!!)

            } catch (exception: Exception) {
                _data.value = AppState.Error(exception)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val viewModerProvider: Provider<VideoItemViewModel>
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            require(modelClass == VideoItemViewModel::class.java)
            return viewModerProvider.get() as T
        }
    }
}

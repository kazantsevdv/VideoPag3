package com.example.videopag3.model

sealed class AppState<out T> {
    data class Success<out T>(val data: T) : AppState<T>()
    data class Error(val error: Throwable) : AppState<Nothing>()
    data class Loading(val progress: Int?) : AppState<Nothing>()
}
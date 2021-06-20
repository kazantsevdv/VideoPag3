package com.example.videopag3.repo.image

interface IImageLoader<T> {
    fun loadInto(url: String, container: T)
}
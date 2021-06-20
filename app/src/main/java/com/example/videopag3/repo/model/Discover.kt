package com.example.videopag3.repo.model


data class Discover(
    val page: Int,
    val results: List<VideosItem>,
    val total_pages: Int
)
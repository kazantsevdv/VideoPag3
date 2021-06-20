package com.example.videopag3.repo.model

data class Video (
    val adult: Boolean,
    val backdrop_path: String,
    val budget: Long,
    val homepage: String,
    val id: Long,
    val imdb_id: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val production_companies: List<ProductionCompany>,
    val release_date: String,
    val revenue: Long,
    val runtime: Long,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,

    val vote_average: Double

    )
data class ProductionCompany (
    val id: Long,
    val logo_path: String? = null,
    val name: String,
    val origin_country: String
)
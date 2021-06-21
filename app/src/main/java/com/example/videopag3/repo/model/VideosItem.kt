package com.example.videopag3.repo.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize



data class VideosItem(
    val id: Int,
    val backdrop_path: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val release_date: String,
    val vote_average: Double

)
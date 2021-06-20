package com.example.videopag3.repo.image

import android.widget.ImageView
import com.bumptech.glide.Glide


class ImageLoader(private val baseURL: String) : IImageLoader<ImageView> {

    override fun loadInto(url: String, container: ImageView) {

        Glide.with(container.context)
            .asBitmap()
            .load(baseURL + url)
            .into(container)
    }

}
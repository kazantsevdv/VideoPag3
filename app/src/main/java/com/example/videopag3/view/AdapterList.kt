package com.example.videopag3.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.videopag3.databinding.ItemVideoBinding
import com.example.videopag3.repo.image.IImageLoader
import com.example.videopag3.repo.model.VideosItem

class AdapterList(
    private var onListItemClickListener: OnListItemClickListener,
    private var imageLoader: IImageLoader<ImageView>
) : PagingDataAdapter<VideosItem, AdapterList.RecyclerItemViewHolder>(DataDiffCallback) {

    private lateinit var bindingItem: ItemVideoBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        bindingItem = ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerItemViewHolder(bindingItem)
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class RecyclerItemViewHolder(val binding: ItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: VideosItem?) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                data?.let {
                    with(binding) {
                        root.setOnClickListener { onListItemClickListener.onItemClick(data) }
                        tvName.text = data.original_title
                        imageLoader.loadInto(data.poster_path ?: "", ivImage)
                        tvRelease.text = data.release_date
                    }
                }
            }

        }
    }


    private object DataDiffCallback : DiffUtil.ItemCallback<VideosItem>() {

        override fun areItemsTheSame(oldItem: VideosItem, newItem: VideosItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: VideosItem, newItem: VideosItem): Boolean {
            return oldItem.id == newItem.id
        }

    }
}

interface OnListItemClickListener {
    fun onItemClick(data: VideosItem)
}
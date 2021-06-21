package com.example.videopag3.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.videopag3.databinding.ItemLoadStateBinding
import com.google.android.material.snackbar.Snackbar


class LoaderStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<LoaderStateAdapter.ItemViewHolder>() {

    private lateinit var bindingItem: ItemLoadStateBinding

    override fun onBindViewHolder(holder: ItemViewHolder, loadState: LoadState) {

        bindingItem.loadStateRetry.isVisible = loadState is LoadState.Error

        bindingItem.loadStateRetry.setOnClickListener {
            retry.invoke()
        }
        if (loadState is LoadState.Error) {
            Snackbar.make(
                bindingItem.root,
                loadState.error.localizedMessage ?: "",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ItemViewHolder {
        bindingItem =
            ItemLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(bindingItem)

    }


    inner class ItemViewHolder(loadStateViewBinding: ItemLoadStateBinding) :
        RecyclerView.ViewHolder(loadStateViewBinding.root)
}
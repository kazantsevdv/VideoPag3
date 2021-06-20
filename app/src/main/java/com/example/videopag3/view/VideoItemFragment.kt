package com.example.videopag3.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.videopag3.App
import com.example.videopag3.databinding.FragmentVideoInfoBinding
import com.example.videopag3.model.AppState
import com.example.videopag3.repo.image.IImageLoader
import com.example.videopag3.repo.model.Video
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject
import javax.inject.Provider


class VideoItemFragment : Fragment() {
    @Inject
    lateinit var viewModeProvider: Provider<VideoItemViewModel.Factory>
    private val viewModel: VideoItemViewModel by viewModels { viewModeProvider.get() }
    @Inject
    lateinit var imageLoader: IImageLoader<ImageView>


    private var _viewBinding: FragmentVideoInfoBinding? = null
    private val viewBinding get() = checkNotNull(_viewBinding)

    private val id: String? by lazy { arguments?.getString(ARG_PARAM1) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        App.component.inject(this)
        _viewBinding = FragmentVideoInfoBinding.inflate(inflater, container, false)
        return _viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        id?.let { viewModel.getVideoInfo(it.toInt()) }
    }


    private fun setupObservers() {
        viewModel.data.observe(viewLifecycleOwner, {
            it?.let { result ->
                when (result) {
                    is AppState.Success<Video> -> {
                        showSuccess(result.data)
                    }
                    is AppState.Error -> {
                        showError(result)
                    }
                    is AppState.Loading -> {
                        showLoading()
                    }
                }
            }
        })
    }

    private fun setupUI(data: Video) {
        viewBinding.apply {
            imageLoader.loadInto(data.backdrop_path,imBag)
            imageLoader.loadInto(data.poster_path,imPoster)
            title.text=data.title
            overview.text=data.overview
        }
    }

    private fun showSuccess(data: Video?) {
        if (data != null) {
            viewBinding.apply {
                progressBar.visibility = View.GONE
                this.data.visibility = View.VISIBLE
                tvNoData.visibility = View.GONE
            }
            setupUI(data)
        } else {
            viewBinding.apply {
                progressBar.visibility = View.GONE
                this.data.visibility = View.GONE
                tvNoData.visibility = View.VISIBLE
            }
        }
    }


    private fun showError(result: AppState.Error) {
        viewBinding.apply {
            progressBar.visibility = View.GONE
            this.data.visibility = View.GONE
            tvNoData.visibility = View.VISIBLE
            Snackbar.make(viewBinding.root, result.error.localizedMessage, Snackbar.LENGTH_LONG)
                .show()
        }

    }

    private fun showLoading() {
        viewBinding?.apply {
            progressBar.visibility = View.VISIBLE
            tvNoData.visibility = View.GONE
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }

    companion object {
        const val ARG_PARAM1 = "ARG_PARAM1"

        @JvmStatic
        fun newInstance(id: String) =
            VideoItemFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, id)
                }
            }
    }


}
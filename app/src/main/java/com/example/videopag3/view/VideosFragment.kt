package com.example.videopag3.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.videopag3.App
import com.example.videopag3.R
import com.example.videopag3.databinding.FragmentVideosBinding
import com.example.videopag3.repo.image.IImageLoader
import com.example.videopag3.repo.model.VideosItem
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject
import javax.inject.Provider


class VideosFragment : Fragment() {
    @Inject
    lateinit var viewModeProvider: Provider<VideosViewModel.Factory>

    @Inject
    lateinit var imageLoader: IImageLoader<ImageView>


    private val viewModel: VideosViewModel by viewModels { viewModeProvider.get() }
    private val navigation by lazy { findNavController() }

    private var _viewBinding: FragmentVideosBinding? = null
    private val viewBinding get() = checkNotNull(_viewBinding)

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        AdapterList(onListItemClickListener, imageLoader)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        App.component.inject(this)
        _viewBinding = FragmentVideosBinding.inflate(inflater, container, false)
        return _viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()

        loadData()

    }


    private fun loadData() {

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.movies.collectLatest{
                adapter.submitData(it)
            }
        }
    }

    private fun setupUI() {


        viewBinding.data.adapter = adapter
            .withLoadStateHeaderAndFooter(
                header = LoaderStateAdapter({ adapter.retry() }, {
                    Snackbar.make(
                        viewBinding.root,
                        it,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }),
                footer = LoaderStateAdapter({ adapter.retry() }, {
                    Snackbar.make(
                        viewBinding.root,
                        it,
                        Snackbar.LENGTH_SHORT
                    ).show()
                })

            )

        viewBinding.swipeRefresh.setOnRefreshListener { adapter.refresh() }

        loadStateRefresh()

        viewBinding.retry.setOnClickListener { adapter.refresh() }
    }

    private fun loadStateRefresh() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collectLatest { loadStates ->
                val refresh = loadStates.refresh
                viewBinding.swipeRefresh.isRefreshing = loadStates.refresh is LoadState.Loading
                viewBinding.retry.isVisible = refresh is LoadState.Error
                if (refresh is LoadState.Error) {

                    Snackbar.make(
                        viewBinding.root,
                        refresh.error.localizedMessage ?: "",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private val onListItemClickListener: OnListItemClickListener =
        object : OnListItemClickListener {
            override fun onItemClick(data: VideosItem) {
                val bundle = Bundle()
                bundle.putInt(VideoItemFragment.ARG_PARAM1, data.id)
                navigation.navigate(R.id.action_videosFragment_to_videoItemFragment, bundle)
            }
        }

    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }
}
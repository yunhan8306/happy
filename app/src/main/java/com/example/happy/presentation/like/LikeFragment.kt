package com.example.happy.presentation.like

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.happy.common.base.BaseFragment
import com.example.happy.common.config.AppConfig
import com.example.happy.common.util.ScrollLinearLayoutManager
import com.example.happy.common.util.safeLaunch
import com.example.happy.common.util.showToast
import com.example.happy.common.util.visible
import com.example.happy.databinding.FragmentLikeBinding
import com.example.happy.model.AppSideEffect
import com.example.happy.model.CollectionData
import com.example.happy.presentation.detail.DetailActivity
import com.example.happy.presentation.list.SearchListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class LikeFragment @Inject constructor() : BaseFragment<FragmentLikeBinding>() {

    private val viewModel: LikeListViewModel by viewModels()

    private val linearLayoutManager by lazy { ScrollLinearLayoutManager(requireActivity()) }

    private val likeListAdapter by lazy {
        SearchListAdapter(
            context = requireActivity(),
            owner = this,
            onClickCollection = ::showDetailActivity
        )
    }

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLikeBinding.inflate(inflater, container, false)

    override fun initFragment(savedInstanceState: Bundle?) {
        setUI()
        collectViewModel()
        collectAppConfig()
        addListeners()
    }

    private fun setUI() = with(binding) {
        recyclerViewCollection.apply {
            layoutManager = linearLayoutManager
            adapter = likeListAdapter
        }
    }

    private fun collectViewModel() = with(viewModel) {
        lifecycleScope.safeLaunch {
            likeList.collectLatest {
                likeListAdapter.submit(it)
            }
        }
    }

    private fun collectAppConfig() = with(AppConfig) {
        lifecycleScope.safeLaunch {
            appSideEffect.collectLatest {
                when (it) {
                    is AppSideEffect.GoToNavLike -> {
                        lifecycleScope.safeLaunch {
                            binding.recyclerViewCollection.scrollToPosition(0)
                        }
                    }
                }
            }
        }
    }

    private fun addListeners() = with(binding) {
        btnTop.setFirstClickEvent {
            lifecycleScope.safeLaunch {
                recyclerViewCollection.smoothScrollToPosition(0)
            }
        }

        recyclerViewCollection.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val currentItemIdx = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                val lastItemIdx = likeListAdapter.itemCount - 1

                if (likeListAdapter.itemCount > 0 &&
                    currentItemIdx == lastItemIdx &&
                    !recyclerView.canScrollVertically(1) &&
                    recyclerView.scrollState != RecyclerView.SCROLL_STATE_IDLE
                ) {
                    requireActivity().showToast("더 이상 데이터가 없습니다")
                }

                val topBtnVisible = recyclerView.computeVerticalScrollOffset() > 40
                if(btnTop.isVisible != topBtnVisible) btnTop.visible(topBtnVisible)
            }
        })
    }

    private fun showDetailActivity(data: CollectionData) {
        requireActivity().apply {
            Intent(this, DetailActivity::class.java).apply {
                putExtra("data", data)
                launcher.launch(this)
            }
        }
    }
}
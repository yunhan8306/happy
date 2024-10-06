package com.example.happy.presentation.list

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.happy.common.base.BaseActivity
import com.example.happy.common.util.LifecycleOwnerWrapper
import com.example.happy.common.util.ScrollLinearLayoutManager
import com.example.happy.common.util.safeLaunch
import com.example.happy.common.util.showToast
import com.example.happy.common.util.visible
import com.example.happy.databinding.ActivityListBinding
import com.example.happy.model.CollectionData
import com.example.happy.model.SearchListStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchListActivity: BaseActivity<ActivityListBinding>(), LifecycleOwnerWrapper {

    private val viewModel: SearchListViewModel by viewModels()

    private val linearLayoutManager by lazy { ScrollLinearLayoutManager(this) }

    private val searchListAdapter by lazy {
        SearchListAdapter(
            context = this,
            owner = this,
            onClickCollection = ::showDetailActivity
        )
    }

    override fun createBinding() = ActivityListBinding.inflate(layoutInflater)

    override fun initActivity(savedInstanceState: Bundle?) {
        setUI()
        collectViewModel()
        addListeners()
    }

    private fun setUI() = with(binding) {
        recyclerViewCollection.apply {
            layoutManager = linearLayoutManager
            adapter = searchListAdapter
        }
    }

    private fun collectViewModel() = with(viewModel) {
        lifecycleScope.safeLaunch {
            searchListStatus.collectLatest { status ->
                when(status) {
                    is SearchListStatus.Success -> {
                        searchListAdapter.submit(status.list) {
                            lifecycleScope.safeLaunch {
                                searchListAdapter.refresh = false
                            }
                        }
                    }
                    is SearchListStatus.Fail -> {
                        showToast("실패")
                    }
                    is SearchListStatus.EmptyData -> {
                        showToast("데이터가 없습니다")
                    }
                    is SearchListStatus.Loading -> {
                        showToast("로딩중")
                    }
                }
            }
        }
    }

    private fun addListeners() = with(binding) {
        btnTop.setFirstClickEvent {
            recyclerViewCollection.scrollToPosition(0)
        }

        recyclerViewCollection.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val currentItemIdx = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                val lastItemIdx = searchListAdapter.itemCount - 1

                if (searchListAdapter.itemCount > 0 &&
                    currentItemIdx == lastItemIdx &&
                    !recyclerView.canScrollVertically(1) &&
                    recyclerView.scrollState != RecyclerView.SCROLL_STATE_IDLE
                ) {
                    loadMore()
                }

                val topBtnVisible = recyclerView.computeVerticalScrollOffset() > 40
                if(btnTop.isVisible != topBtnVisible) btnTop.visible(topBtnVisible)
            }
        })
    }

    private fun loadMore() {
        viewModel.getSearchList()
    }

    private fun showDetailActivity(data: CollectionData) {
        showToast("상세뷰")
    }
}
package com.example.happy.presentation.list

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.happy.common.base.BaseActivity
import com.example.happy.common.util.LifecycleOwnerWrapper
import com.example.happy.common.util.ScrollLinearLayoutManager
import com.example.happy.common.util.showToast
import com.example.happy.common.util.visible
import com.example.happy.databinding.ActivityListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchListActivity: BaseActivity<ActivityListBinding>(), LifecycleOwnerWrapper {

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

    private fun collectViewModel() {
        val testList = listOf(
            CollectionData(1),
            CollectionData(2),
            CollectionData(3),
            CollectionData(4),
            CollectionData(5),
            CollectionData(6),
            CollectionData(7),
            CollectionData(8),
            CollectionData(9),
            CollectionData(10),
            CollectionData(11),
            CollectionData(12),
            CollectionData(13),
            CollectionData(14),
            CollectionData(15),
        )
        searchListAdapter.submit(testList) {
            lifecycleScope.launch {
                searchListAdapter.refresh = false
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

                // 스크롤이 끝에 도달했는지 확인
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
        showToast("로드 시도 중")
    }

    private fun showDetailActivity(data: CollectionData) {
        showToast("상세뷰")
    }
}
package com.example.happy.presentation.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.happy.common.base.BaseActivity
import com.example.happy.common.config.AppConfig
import com.example.happy.common.util.LifecycleOwnerWrapper
import com.example.happy.common.util.ScrollLinearLayoutManager
import com.example.happy.common.util.launchBottomSheetDialogFragment
import com.example.happy.common.util.safeLaunch
import com.example.happy.common.util.showToast
import com.example.happy.common.util.visible
import com.example.happy.databinding.ActivityListBinding
import com.example.happy.model.AppSideEffect
import com.example.happy.model.CollectionData
import com.example.happy.model.FilterData
import com.example.happy.model.SearchListStatus
import com.example.happy.model.getCategoryList
import com.example.happy.model.getSortingList
import com.example.happy.presentation.detail.DetailActivity
import com.example.happy.presentation.filter.BottomSheetFilterDialog
import com.example.happy.presentation.navigation.NavigationType
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
        collectAppConfig()
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
                    is SearchListStatus.End -> {
                        showToast("더 이상 데이터가 없습니다")
                    }
                }
            }
        }

        lifecycleScope.safeLaunch {
            totalFilterData.collectLatest {
                binding.btnCategory.text = it.first.name
                binding.btnSorting.text = it.second.name
            }
        }
    }

    private fun collectAppConfig() = with(AppConfig) {
        lifecycleScope.safeLaunch {
            appSideEffect.collectLatest {
                when(it) {
                    is AppSideEffect.GoToNavLike -> {
                        finish()
                    }
                }
            }
        }
    }

    private fun addListeners() = with(binding) {
        btnTop.setFirstClickEvent {
            lifecycleScope.safeLaunch {
                hideKeyBoard()
                recyclerViewCollection.smoothScrollToPosition(0)
            }
        }

        btnSearch.setFirstClickEvent {
            lifecycleScope.safeLaunch {
                searchListAdapter.refresh = true
                viewModel.refresh()
                hideKeyBoard()
                viewModel.getSearchList(editTextInput.text.toString())
            }
        }

        btnCategory.setFirstClickEvent {
            showFilter("category", viewModel.totalFilterData.value.first.getCategoryList())
        }

        btnSorting.setFirstClickEvent {
            showFilter("sorting", viewModel.totalFilterData.value.second.getSortingList())
        }

        btnReset.setFirstClickEvent {
            viewModel.resetFilter()
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

        editTextInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                btnSearch.visible(editTextInput.text.length >= 2)
            }
        })
    }

    private fun loadMore() {
        viewModel.getSearchList()
    }

    private fun showDetailActivity(data: CollectionData) {
        Intent(this, DetailActivity::class.java).apply {
            putExtra("data", data)
            launcher.launch(this)
        }
    }

    private fun hideKeyBoard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.editTextInput.windowToken, 0)
    }

    private fun showFilter(filterType: String, filterList: List<FilterData>) {
        lifecycleScope.safeLaunch {
            val filterDialog = BottomSheetFilterDialog().apply {
                arguments = bundleOf("filterList" to filterList)
            }
            launchBottomSheetDialogFragment(
                dialogFragment = filterDialog,
                dismissResult = {
                    lifecycleScope.safeLaunch {
                        selectFilter(filterType, it)
                        filterDialog.dismissAllowingStateLoss()
                    }
                }
            )
        }
    }

    private fun selectFilter(filterType: String, data: FilterData?) {
        lifecycleScope.safeLaunch {
            searchListAdapter.refresh = true
            viewModel.setFilter(filterType, data)
            binding.recyclerViewCollection.scrollToPosition(0)
        }
    }
}
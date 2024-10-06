package com.example.happy.presentation.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.happy.common.base.BaseFragment
import com.example.happy.common.util.visible
import com.example.happy.databinding.FragmentNavigationBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NavigationFragment @Inject constructor() : BaseFragment<FragmentNavigationBinding>() {

    private val viewModel by activityViewModels<NavigationViewModel>()

    private val navigationBtnOnList by lazy {
        listOf(binding.textSearchSelect, binding.textLikeSelect)
    }

    private val navigationBtnOffList by lazy {
        listOf(binding.textSearchUnSelect, binding.textLikeUnSelect)
    }

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentNavigationBinding.inflate(inflater, container, false)

    override fun initFragment(savedInstanceState: Bundle?) {
        addListeners()
        collectViewModel()
    }

    private fun addListeners() = with(binding) {
        clSearch.setFirstClickEvent(200) {
            viewModel.selectNavigation(NavigationType.Search)
        }
        clLike.setFirstClickEvent(200) {
            viewModel.selectNavigation(NavigationType.Like)
        }
    }

    private fun collectViewModel() = with(viewModel) {
        navigationState.onResult { setNavigationBtn(it) }
    }

    private fun setNavigationBtn(navigationType: NavigationType) = with(binding) {
        navigationBtnOffList.forEach { it.visible(true) }
        navigationBtnOnList.forEach { it.visible(false) }

        when(navigationType) {
            is NavigationType.Search -> {
                textSearchUnSelect.visible(false)
                textSearchSelect.visible(true)
            }
            is NavigationType.Like -> {
                textLikeUnSelect.visible(false)
                textLikeSelect.visible(true)
            }
        }
    }
}
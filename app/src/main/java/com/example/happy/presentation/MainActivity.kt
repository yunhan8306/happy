package com.example.happy.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.example.happy.R
import com.example.happy.common.base.BaseActivity
import com.example.happy.databinding.ActivityMainBinding
import com.example.happy.common.util.LifecycleOwnerWrapper
import com.example.happy.common.util.addFragment
import com.example.happy.common.util.hideFragment
import com.example.happy.common.util.showFragment
import com.example.happy.presentation.like.LikeFragment
import com.example.happy.presentation.navigation.NavigationFragment
import com.example.happy.presentation.navigation.NavigationType
import com.example.happy.presentation.navigation.NavigationViewModel
import com.example.happy.presentation.search.SearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(), LifecycleOwnerWrapper {

    private val navigationViewModel by viewModels<NavigationViewModel>()

    private var navigationFragment: NavigationFragment? = null
    private var searchFragment: SearchFragment? = null
    private var likeFragment: LikeFragment? = null

    private var onTopFragment : Fragment? = null

    override fun createBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initActivity(savedInstanceState: Bundle?) {
        collectViewModel()
        addNavigationFragment()
    }

    private fun collectViewModel() = with(navigationViewModel) {
        navigationState.onResult { navigationType ->
            selectOnTopFragment(navigationType)
        }
    }

    private fun addNavigationFragment() {
        navigationFragment = NavigationFragment()
        addFragment(R.id.containerNavigation, navigationFragment)
    }

    private fun selectOnTopFragment(navigationType: NavigationType) {
        hideFragment(onTopFragment)

        when(navigationType) {
            is NavigationType.Search -> {
                if(searchFragment == null) {
                    searchFragment = SearchFragment()
                    addFragment(R.id.containerMain, searchFragment)
                } else {
                    showFragment(searchFragment)
                }
            }
            is NavigationType.Like -> {
                if(likeFragment == null) {
                    likeFragment = LikeFragment()
                    addFragment(R.id.containerMain, likeFragment)
                } else {
                    showFragment(likeFragment)
                }
            }
        }

        onTopFragment = getNavigationFragment(navigationType)
    }

    private fun getNavigationFragment(navigationType: NavigationType) =
        when(navigationType) {
            is NavigationType.Search -> searchFragment
            is NavigationType.Like -> likeFragment
        }
}
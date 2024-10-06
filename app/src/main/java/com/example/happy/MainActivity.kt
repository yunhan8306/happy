package com.example.happy

import android.os.Bundle
import com.example.happy.common.base.BaseActivity
import com.example.happy.databinding.ActivityMainBinding
import com.example.happy.common.util.LifecycleOwnerWrapper
import com.example.happy.common.util.addFragment
import com.example.happy.presentation.navigation.NavigationFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(), LifecycleOwnerWrapper {

    private var navigationFragment: NavigationFragment? = null

    override fun createBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initActivity(savedInstanceState: Bundle?) {
        collectViewModel()
        addNavigationFragment()
    }

    private fun collectViewModel() {

    }

    private fun addNavigationFragment() {
        navigationFragment = NavigationFragment()
        addFragment(R.id.containerNavigation, navigationFragment)
    }
}
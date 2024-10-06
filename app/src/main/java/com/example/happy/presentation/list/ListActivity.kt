package com.example.happy.presentation.list

import android.os.Bundle
import com.example.happy.common.base.BaseActivity
import com.example.happy.common.util.LifecycleOwnerWrapper
import com.example.happy.databinding.ActivityListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListActivity: BaseActivity<ActivityListBinding>(), LifecycleOwnerWrapper {

    override fun createBinding() = ActivityListBinding.inflate(layoutInflater)

    override fun initActivity(savedInstanceState: Bundle?) {
        collectViewModel()
    }

    private fun collectViewModel() {

    }
}
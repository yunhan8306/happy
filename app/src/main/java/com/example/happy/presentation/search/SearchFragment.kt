package com.example.happy.presentation.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.happy.common.base.BaseFragment
import com.example.happy.databinding.FragmentSearchBinding
import com.example.happy.presentation.list.SearchListActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment @Inject constructor() : BaseFragment<FragmentSearchBinding>() {

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSearchBinding.inflate(inflater, container, false)

    override fun initFragment(savedInstanceState: Bundle?) {
        addListeners()
    }

    private fun addListeners() = with(binding) {
        btnSearch.setFirstClickEvent(200) {
            startListActivity()
        }
    }

    private fun startListActivity() {
        Intent(requireActivity(), SearchListActivity::class.java).apply {
            launcher.launch(this)
        }
    }
}
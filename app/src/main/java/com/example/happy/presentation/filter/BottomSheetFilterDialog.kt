package com.example.happy.presentation.filter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.happy.common.base.BaseBottomSheetDialogFragment
import com.example.happy.common.util.ScrollLinearLayoutManager
import com.example.happy.common.util.safeParcelableList
import com.example.happy.databinding.BottomFragmentFilterBinding
import com.example.happy.model.FilterData

class BottomSheetFilterDialog: BaseBottomSheetDialogFragment<BottomFragmentFilterBinding, FilterData>() {

    private val linearLayoutManager by lazy { ScrollLinearLayoutManager(requireActivity()) }

    private val filterAdapter by lazy {
        FilterAdapter(
            context = requireActivity(),
            owner = this,
            onClickFilter = ::selectFilter
        )
    }

    private val filterList by lazy { arguments?.safeParcelableList<FilterData>("filterList")?.toList() ?: emptyList() }

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = BottomFragmentFilterBinding.inflate(layoutInflater, container, false)

    override fun initDialogFragment(savedInstanceState: Bundle?) {
        if(filterList.isEmpty()) dismissAllowingStateLoss()
        setUi()
    }

    private fun setUi() = with(binding) {
        recyclerViewFilter.apply {
            layoutManager = linearLayoutManager
            adapter = filterAdapter
        }

        filterAdapter.submit(filterList)
    }

    private fun selectFilter(data: FilterData) {
        dismissResult?.invoke(data)
    }
}
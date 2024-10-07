package com.example.happy.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import com.example.happy.common.util.LifecycleOwnerWrapper
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialogFragment<Binding : ViewDataBinding, Result> : BottomSheetDialogFragment(), LifecycleOwnerWrapper {

    private var _binding: Binding? = null
    protected val binding get() = _binding!!

    var resultData: Result? = null
    var dismissResult: ((result: Result?) -> Unit)? = null

    protected abstract fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): Binding

    override fun initLifecycleOwner(): LifecycleOwner = viewLifecycleOwner

    protected open fun initDialogFragment(savedInstanceState: Bundle?) = Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = createFragmentBinding(inflater, container).apply {
        lifecycleOwner = viewLifecycleOwner
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDialogFragment(savedInstanceState)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}

package com.example.happy.common.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.example.happy.common.util.LifecycleOwnerWrapper

abstract class BaseFragment<Binding : ViewDataBinding> : Fragment(), LifecycleOwnerWrapper {

    private var _binding: Binding? = null
    protected val binding get() = _binding!!

    private val registerKey = this::class.java.simpleName

    private val contract = object : ActivityResultContract<Intent, ActivityResult>() {
        override fun createIntent(context: Context, input: Intent) = input
        override fun parseResult(resultCode: Int, intent: Intent?) = ActivityResult(resultCode, intent)
    }

    protected val launcher by lazy {
        requireActivity().activityResultRegistry.register(registerKey, contract) { activityResult ->
            if (activityResult.resultCode == Activity.RESULT_OK) {
                activityResult(activityResult)
            }
        }
    }

    protected var activityResult: (ActivityResult) -> Unit = {}

    protected abstract fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): Binding

    protected open fun initFragment(savedInstanceState: Bundle?) = Unit

    protected open fun initBinding() = Unit

    override fun initLifecycleOwner(): LifecycleOwner = viewLifecycleOwner

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = createFragmentBinding(inflater, container).apply {
        lifecycleOwner = viewLifecycleOwner
        _binding = this
        initBinding()
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFragment(savedInstanceState)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
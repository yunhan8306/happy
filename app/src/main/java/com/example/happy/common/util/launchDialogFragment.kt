package com.example.happy.common.util

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.happy.common.base.BaseBottomSheetDialogFragment
import kotlinx.coroutines.Job

fun <Binding : ViewDataBinding, Result> AppCompatActivity.launchBottomSheetDialogFragment(
    dialogFragment: BaseBottomSheetDialogFragment<Binding, Result>,
    fragmentManager: FragmentManager = supportFragmentManager,
    dismissResult: ((Result?) -> Unit)? = null,
): Job = showBottomSheetDialogFragment(
    dialogFragment = dialogFragment,
    fragmentManager = fragmentManager,
    dismissResult = dismissResult,
)

private fun <VB : ViewDataBinding, Result> LifecycleOwner.showBottomSheetDialogFragment(
    dialogFragment: BaseBottomSheetDialogFragment<VB, Result>,
    fragmentManager: FragmentManager,
    tag: String? = null,
    dismissResult: ((Result?) -> Unit)? = null,
): Job = lifecycleScope.launchWhenStarted {
    dialogFragment.dismissResult = dismissResult
    dialogFragment.show(fragmentManager, tag)
}
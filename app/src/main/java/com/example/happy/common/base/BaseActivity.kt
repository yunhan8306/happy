package com.example.happy.common.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import com.example.happy.common.util.LifecycleOwnerWrapper

abstract class BaseActivity<Binding : ViewDataBinding> : AppCompatActivity(),
    LifecycleOwnerWrapper {

    protected val binding: Binding by lazy { createBinding() }

    private val registerKey = this::class.java.simpleName

    private val contract = object : ActivityResultContract<Intent, ActivityResult>() {
        override fun createIntent(context: Context, input: Intent) = input
        override fun parseResult(resultCode: Int, intent: Intent?) = ActivityResult(resultCode, intent)
    }

    protected val launcher by lazy {
        activityResultRegistry.register(registerKey, contract) { activityResult ->
            if (activityResult.resultCode == Activity.RESULT_OK) {
                activityResult(activityResult)
            }
        }
    }

    protected var activityResult: (ActivityResult) -> Unit = {}

    protected abstract fun createBinding(): Binding

    protected open fun initActivity(savedInstanceState: Bundle?) = Unit

    override fun initLifecycleOwner(): LifecycleOwner = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        initActivity(savedInstanceState)
    }
}
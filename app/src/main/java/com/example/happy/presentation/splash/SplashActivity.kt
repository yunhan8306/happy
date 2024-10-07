package com.example.happy.presentation.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.happy.presentation.MainActivity
import com.example.happy.common.base.BaseActivity
import com.example.happy.common.util.LifecycleOwnerWrapper
import com.example.happy.common.util.safeLaunch
import com.example.happy.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity: BaseActivity<ActivitySplashBinding>(), LifecycleOwnerWrapper {

    private val viewModel: SplashViewModel by viewModels()

    override fun createBinding() = ActivitySplashBinding.inflate(layoutInflater)

    override fun initActivity(savedInstanceState: Bundle?) {
        collectViewModel()
    }

    @SuppressLint("SetTextI18n")
    private fun collectViewModel() = with(viewModel) {
        lifecycleScope.safeLaunch {
            splashTime.collectLatest {
                binding.txtTime.text = "${it}ms"
            }
        }

        lifecycleScope.safeLaunch {
            isSplashFinished.collectLatest {
                if(it) {
                    Intent(this@SplashActivity, MainActivity::class.java).apply {
                        startActivity(this)
                        finish()
                    }
                }
            }
        }
    }
}
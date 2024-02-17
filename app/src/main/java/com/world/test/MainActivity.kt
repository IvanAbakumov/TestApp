package com.world.test


import com.world.test.common.core.activity.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
   override fun layoutRes() = R.layout.activity_main
}
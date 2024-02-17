package com.world.test.common.core.fragment

import android.os.Bundle
import android.view.View
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<Binding : ViewBinding> : BaseCoreFragment<Binding>() {

    abstract fun initUI(savedInstanceState: Bundle?)
    abstract fun layoutBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI(savedInstanceState)
        layoutBinding()
    }
}

package com.world.test.common.core.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseCoreFragment<Binding : ViewBinding> : Fragment() {

    protected abstract val layoutRes: Int
    protected abstract val binding: Binding
    open val TAG: String by lazy { simpleName() }

    private fun simpleName(): String = this.javaClass.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = if (layoutRes <= 0) null else inflater.inflate(layoutRes, container, false)

}

package com.world.test.common.core.activity

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

abstract class BaseCoreActivity : AppCompatActivity() {

    protected fun <T> LiveData<T>.observe(action: (T) -> Unit) {
        this.observe(this@BaseCoreActivity, Observer {
            action(it)
        })
    }
}
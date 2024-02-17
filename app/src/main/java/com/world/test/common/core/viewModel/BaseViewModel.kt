package com.world.test.common.core.viewModel

import androidx.lifecycle.ViewModel
import com.world.domain.common.ApiErrorException
import com.world.domain.common.BaseException
import com.world.domain.common.UnauthorizedException

open class BaseViewModel : ViewModel() {

    fun mapError(ex: BaseException): ApiErrorException? {
        when(ex) {
            is UnauthorizedException -> {}
            is ApiErrorException -> return ex
            else -> {}
        }
        return null
    }
}
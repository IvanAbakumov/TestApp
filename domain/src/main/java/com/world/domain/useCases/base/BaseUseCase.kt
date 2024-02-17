package com.world.domain.useCases.base

import android.util.Log
import com.world.domain.common.safeLaunch
import com.world.domain.common.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseUseCase<RESULT, PARAMS> : UseCase<RESULT, PARAMS> {

    fun execute(
        uiDispatcher: CoroutineScope,
        result: ResultCallbacks<RESULT>,
        params: PARAMS? = null
    ) {
        uiDispatcher.safeLaunch {
            withContext(Dispatchers.Main) {
                result.onLoading?.invoke(true)
                try {
                    val resultOfWork = remoteWork(params)
                    result.onSuccess?.invoke(resultOfWork)
                    result.onLoading?.invoke(false)
                } catch (e: Throwable) {
                    Log.e("Error", e.localizedMessage ?: "")
                    when (e) {
                        is UnauthorizedException -> result.onError?.invoke(e)
                       // is ApiErrorException -> result.onError?.invoke(e)
                        is NotValidFlow -> result.onError?.invoke(e)
                        is NullPointerException -> result.onError?.invoke(e)
                        //is ConnectivityError -> result.onError?.invoke(e)
                        is UnknownException -> result.onError?.invoke(e)
                        else -> result.onConnectionError?.invoke(e)
                    }
                    result.onLoading?.invoke(false)
                }
            }
        }
    }

}

class ResultCallbacks<T>(
    val onSuccess: ((T) -> Unit)? = null,
    val onLoading: ((Boolean) -> Unit)? = null,
    val onError: ((BaseException) -> Unit)? = null,
    val onConnectionError: ((Throwable) -> Unit)? = null
)

interface UseCase<RESULT, PARAMS> {
    suspend fun remoteWork(params: PARAMS?): RESULT
}
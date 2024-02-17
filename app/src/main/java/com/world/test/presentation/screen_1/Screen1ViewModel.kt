package com.world.test.presentation.screen_1

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.viewModelScope
import com.world.domain.models.HistoryItemModel
import com.world.domain.useCases.base.ResultCallbacks
import com.world.domain.useCases.database.DBGetAllItemsUseCase
import com.world.test.common.core.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Screen1ViewModel @Inject constructor(
    private val dbGetAllItemsUseCase: DBGetAllItemsUseCase,
): BaseViewModel(){

    private val _listItems = MutableStateFlow<List<HistoryItemModel>?>(null)
    val listItems: StateFlow<List<HistoryItemModel>?> = _listItems

    private val _progressBarVisibility = MutableStateFlow(true)
    val progressBarVisibility: StateFlow<Boolean> = _progressBarVisibility

    private val _emptyScreenVisibility = MutableStateFlow(false)
    val emptyScreenVisibility: StateFlow<Boolean> = _emptyScreenVisibility

    private val _number = MutableStateFlow("")
    val number = _number

    fun setNumber(value: CharSequence) = viewModelScope.launch {
        _number.emit(value.toString())
    }

    fun getEnterNumber() = _number.value

    fun clearEnterNumber() = viewModelScope.launch { _number.emit("") }

    fun getItemsFromDatabase() {
        dbGetAllItemsUseCase.execute(
            uiDispatcher = viewModelScope,
            params = DBGetAllItemsUseCase.Params(),
            result = ResultCallbacks(
                onSuccess = {
                    viewModelScope.launch {
                        _listItems.emit(it)
                        _emptyScreenVisibility.emit(it?.size == 0)
                        _progressBarVisibility.emit(false)
                    }
                },
                onError = {
                    mapError(it)
                    viewModelScope.launch {
                        _progressBarVisibility.emit(false)
                    }
                }
            )
        )
    }

    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
    }

    fun checkInternetConnection(context: Context): Boolean = isInternetAvailable(context)

}
package com.world.test.presentation.screen_2

import androidx.lifecycle.viewModelScope
import com.world.domain.models.FactNumberModel
import com.world.domain.models.HistoryItemModel
import com.world.domain.useCases.base.ResultCallbacks
import com.world.domain.useCases.database.DBInsertItemUseCase
import com.world.domain.useCases.network.GetFactByNumberUseCase
import com.world.domain.useCases.network.GetFactRandomUseCase
import com.world.test.common.core.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class Screen2ViewModel @Inject constructor(
    private val getFactRandomUseCase: GetFactRandomUseCase,
    private val getFactByNumberUseCase: GetFactByNumberUseCase,
    private val dbInsertItemUseCase: DBInsertItemUseCase,
): BaseViewModel(){

    private val _insertItem = MutableStateFlow<Boolean?>(null)
    val insertItem: StateFlow<Boolean?> = _insertItem

    private val _factNumberModel = MutableStateFlow<FactNumberModel?>(null)
    val factNumberModel = _factNumberModel

    private val _progressBarVisibility = MutableStateFlow(false)
    val progressBarVisibility: StateFlow<Boolean> = _progressBarVisibility

    fun getFact(number: String?){
        viewModelScope.launch {
            _progressBarVisibility.emit(true)
        }
        number?.let { getFactByNumber(it) } ?: run { getFactRandom() }
    }

    fun setFactNumberModel(localItem: HistoryItemModel?){
        viewModelScope.launch {
            _progressBarVisibility.emit(false)
            _factNumberModel.emit(localItem?.let { FactNumberModel.mapTo(it) })
        }
    }

    private fun getFactRandom() {
        getFactRandomUseCase.execute(
            uiDispatcher = viewModelScope,
            params = GetFactRandomUseCase.Params(),
            result = ResultCallbacks(
                onSuccess = {
                    viewModelScope.launch {
                        _factNumberModel.emit(it)
                        _progressBarVisibility.emit(false)
                        if(it != null) insertHistoryItem(FactNumberModel.mapToDomain(it))
                    }
                },
                onError = {
                    mapError(it)
                    viewModelScope.launch {
                        _progressBarVisibility.emit(false)
                    }
                },
                onLoading = {}
            )
        )
    }

    private fun getFactByNumber(number: String) {
        getFactByNumberUseCase.execute(
            uiDispatcher = viewModelScope,
            params = GetFactByNumberUseCase.Params(number),
            result = ResultCallbacks(
                onSuccess = {
                    viewModelScope.launch {
                        _factNumberModel.emit(it)
                        _progressBarVisibility.emit(false)
                        if(it != null) insertHistoryItem(FactNumberModel.mapToDomain(it))
                    }
                },
                onError = {
                    mapError(it)
                    viewModelScope.launch {
                        _progressBarVisibility.emit(false)
                    }
                },
                onLoading = {}
            )
        )
    }

    private fun insertHistoryItem(item: HistoryItemModel) {
        dbInsertItemUseCase.execute(
            uiDispatcher = viewModelScope,
            params = DBInsertItemUseCase.Params(item),
            result = ResultCallbacks(
                onSuccess = {
                    viewModelScope.launch {
                        _insertItem.emit(true)
                    }
                },
                onError = {
                    mapError(it)
                }
            )
        )
    }
}
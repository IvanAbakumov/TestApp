package com.world.domain.useCases.database

import com.world.domain.models.HistoryItemModel
import com.world.domain.repository.DatabaseRepository
import com.world.domain.useCases.base.BaseUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DBInsertItemUseCase @Inject constructor(
    private val repository: DatabaseRepository
): BaseUseCase<Boolean?, DBInsertItemUseCase.Params>() {


    override suspend fun remoteWork(params: Params?): Boolean? {
        return params?.let {
            withContext(Dispatchers.IO) {
                repository.insertHistoryItem(params.historyItemModel)
            }
        }
    }

    class Params(
        val historyItemModel: HistoryItemModel
    )
}
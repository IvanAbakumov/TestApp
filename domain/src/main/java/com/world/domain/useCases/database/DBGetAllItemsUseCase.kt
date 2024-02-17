package com.world.domain.useCases.database

import com.world.domain.models.HistoryItemModel
import com.world.domain.repository.DatabaseRepository
import com.world.domain.useCases.base.BaseUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DBGetAllItemsUseCase @Inject constructor(
    private val repository: DatabaseRepository
): BaseUseCase<List<HistoryItemModel>?, DBGetAllItemsUseCase.Params>() {


    override suspend fun remoteWork(params: Params?): List<HistoryItemModel>? {
        return params?.let {
            withContext(Dispatchers.IO) {
                repository.getAll()
            }
        }
    }

    class Params()
}
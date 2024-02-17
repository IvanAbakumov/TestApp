package com.world.domain.repository

import com.world.domain.models.HistoryItemModel

interface DatabaseRepository {
    suspend fun getAll(): List<HistoryItemModel>
    suspend fun insertHistoryItem(model: HistoryItemModel): Boolean
}
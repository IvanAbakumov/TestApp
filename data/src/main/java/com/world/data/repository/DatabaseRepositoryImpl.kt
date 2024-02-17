package com.world.data.repository

import com.world.data.db.AppDatabase
import com.world.data.db.model.HistoryItem
import com.world.domain.models.HistoryItemModel
import com.world.domain.repository.DatabaseRepository
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val dao: AppDatabase,
) : DatabaseRepository {


    override suspend fun getAll(): List<HistoryItemModel> =  mapToList(dao.historyItemDao().getAll().asReversed())

    override suspend fun insertHistoryItem(model: HistoryItemModel): Boolean {
        val countItemsOld = getItems()
        dao.historyItemDao().insert(HistoryItem.mapTo(model))
        return countItemsOld < getItems()
    }

    private fun mapToList(list: List<HistoryItem>): ArrayList<HistoryItemModel> {
        val updatedList = ArrayList<HistoryItemModel>()
        list.map {
            updatedList.add(HistoryItem.mapToDomain(it))
        }
        return updatedList
    }

    private fun getItems() = dao.historyItemDao().getAll().size
}
package com.world.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.world.data.db.model.HistoryItem


@Dao
interface HistoryItemDao {
    @Query("SELECT * FROM historyItems")
    fun getAll(): List<HistoryItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(historyItem: HistoryItem)
}
package com.world.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.world.data.db.dao.HistoryItemDao
import com.world.data.db.model.HistoryItem

@Database(entities = [HistoryItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyItemDao(): HistoryItemDao

}
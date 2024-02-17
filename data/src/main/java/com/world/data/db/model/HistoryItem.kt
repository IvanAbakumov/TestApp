package com.world.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.world.domain.models.HistoryItemModel
import com.world.domain.utils.ModelMapper

@Entity(tableName = "historyItems")
data class HistoryItem(
    @PrimaryKey(autoGenerate = true) var uid: Int = 0,
    @ColumnInfo(name = "text") val text: String?,
    @ColumnInfo(name = "number") val number: String?

) {
    companion object : ModelMapper<HistoryItemModel, HistoryItem> {
        override fun mapTo(model: HistoryItemModel): HistoryItem {
            return HistoryItem(
                text = model.text,
                number = model.number
            )
        }

        override fun mapToDomain(model: HistoryItem) = HistoryItemModel(
            text = model.text,
            number = model.number
        )
    }
}
package com.world.domain.models

import android.os.Parcelable
import com.world.domain.utils.ModelMapper
import kotlinx.parcelize.Parcelize

@Parcelize
data class FactNumberModel(
    val text: String? = null,
    val found: Boolean? = null,
    val number: String? = null,
    val type: String? = null
) : Parcelable {
    companion object : ModelMapper<HistoryItemModel, FactNumberModel>{
        override fun mapTo(model: HistoryItemModel): FactNumberModel {
            return FactNumberModel(
                text = model.text,
                number = model.number
            )
        }

        override fun mapToDomain(model: FactNumberModel): HistoryItemModel {
            return HistoryItemModel(
                text = model.text,
                number = model.number
            )
        }

    }
}
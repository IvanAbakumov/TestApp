package com.world.data.api.models

import com.world.domain.models.FactNumberModel
import com.world.domain.utils.ModelMapper
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FactByNumberResponse (
    @SerialName("text")  val text: String? = null,
    @SerialName("found")  val found: Boolean? = null,
    @SerialName("number")  val number: String? = null,
    @SerialName("type")  val type: String? = null
){
    companion object : ModelMapper<FactNumberModel, FactByNumberResponse> {
        override fun mapTo(model: FactNumberModel): FactByNumberResponse {
            TODO("Not yet implemented")
        }

        override fun mapToDomain(model: FactByNumberResponse): FactNumberModel {
            return FactNumberModel(
                text = model.text,
                found = model.found,
                number = model.number,
                type = model.type
            )
        }
    }
}
package com.world.domain.repository

import com.world.domain.models.FactNumberModel

interface NetworkRepository {

    suspend fun getFactByNumber(number: String): FactNumberModel
    suspend fun getFactRandom(): FactNumberModel
}
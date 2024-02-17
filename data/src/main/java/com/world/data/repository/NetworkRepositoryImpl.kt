package com.world.data.repository

import com.world.data.api.TestService
import com.world.data.api.models.FactByNumberResponse
import com.world.data.ext.handleRequest
import com.world.domain.models.FactNumberModel
import com.world.domain.repository.NetworkRepository
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val service: TestService
) : NetworkRepository {
    override suspend fun getFactByNumber(number: String): FactNumberModel {
        return handleRequest {
            FactByNumberResponse.mapToDomain(service.getFactByNumber(number))
        }
    }

    override suspend fun getFactRandom(): FactNumberModel {
        return handleRequest {
            FactByNumberResponse.mapToDomain(service.getFactRandom())
        }
    }
}
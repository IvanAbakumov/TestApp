package com.world.domain.useCases.network

import com.world.domain.models.FactNumberModel
import com.world.domain.repository.NetworkRepository
import com.world.domain.useCases.base.BaseUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetFactRandomUseCase @Inject constructor(
    private val repository: NetworkRepository
) : BaseUseCase<FactNumberModel?, GetFactRandomUseCase.Params>() {
    override suspend fun remoteWork(params: Params?): FactNumberModel? {
        params?.let {
            return withContext(Dispatchers.IO) {
                repository.getFactRandom()
            }
        } ?: return null
    }

    class Params()
}
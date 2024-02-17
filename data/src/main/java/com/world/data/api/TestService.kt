package com.world.data.api

import com.world.data.api.models.FactByNumberResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface TestService {

    @GET(GET_FACT_BY_NUMBER)
    suspend fun getFactByNumber(@Path("number") number: String): FactByNumberResponse

    @GET(GET_FACT_RANDOM)
    suspend fun getFactRandom(): FactByNumberResponse

    companion object{
        private const val GET_FACT_BY_NUMBER = "/{number}"
        private const val GET_FACT_RANDOM = "/random/math"
    }
}
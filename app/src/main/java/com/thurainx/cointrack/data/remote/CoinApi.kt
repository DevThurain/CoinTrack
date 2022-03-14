package com.thurainx.cointrack.data.remote

import com.thurainx.cointrack.data.dto.CoinDetailDto
import com.thurainx.cointrack.data.dto.CoinDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinApi{

    @GET("coins")
    suspend fun getCoinList() : List<CoinDto>

    @GET("coins/{id}")
    suspend fun getCoinDetail(
        @Path("id") id: String
    ) : CoinDetailDto
}
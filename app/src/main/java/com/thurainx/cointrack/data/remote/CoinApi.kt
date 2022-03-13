package com.thurainx.cointrack.data.remote

import com.thurainx.cointrack.data.dto.CoinDto
import retrofit2.http.GET

interface CoinApi{

    @GET("/coins")
    suspend fun getCoinList() : List<CoinDto>
}
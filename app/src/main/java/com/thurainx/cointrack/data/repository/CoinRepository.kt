package com.thurainx.cointrack.data.repository

import com.thurainx.cointrack.data.dto.CoinDto
import com.thurainx.cointrack.data.dto.toCoin
import com.thurainx.cointrack.data.dto.toCoinDetail
import com.thurainx.cointrack.data.remote.CoinApi
import com.thurainx.cointrack.domain.model.Coin
import com.thurainx.cointrack.domain.model.CoinDetail
import com.thurainx.cointrack.domain.repository.CoinRepository
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val api: CoinApi
) : CoinRepository{

    override suspend fun getCoinList(): List<Coin> {
        return api.getCoinList().map { it.toCoin() }
    }

    override suspend fun getCoinDetail(id: String): CoinDetail {
        return api.getCoinDetail(id).toCoinDetail()
    }

}
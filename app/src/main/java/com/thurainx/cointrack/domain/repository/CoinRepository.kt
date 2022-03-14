package com.thurainx.cointrack.domain.repository

import com.thurainx.cointrack.domain.model.Coin
import com.thurainx.cointrack.domain.model.CoinDetail

interface CoinRepository {
     suspend fun getCoinList() : List<Coin>
     suspend fun getCoinDetail(id: String): CoinDetail
}
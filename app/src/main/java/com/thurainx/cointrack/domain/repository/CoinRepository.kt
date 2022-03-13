package com.thurainx.cointrack.domain.repository

import com.thurainx.cointrack.domain.model.Coin

interface CoinRepository {
     suspend fun getCoinList() : List<Coin>
}
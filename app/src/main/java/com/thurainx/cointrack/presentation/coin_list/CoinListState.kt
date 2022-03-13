package com.thurainx.cointrack.presentation.coin_list

import com.thurainx.cointrack.domain.model.Coin

data class CoinListState(
    val coins : List<Coin> = emptyList(),
    val error : String = "",
    val isLoading : Boolean = false
)
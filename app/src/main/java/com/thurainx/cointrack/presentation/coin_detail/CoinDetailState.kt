package com.thurainx.cointrack.presentation.coin_detail

import com.thurainx.cointrack.domain.model.CoinDetail

data class CoinDetailState (
    val detail: CoinDetail? = null,
    val error: String = "",
    val isLoading: Boolean = false
)
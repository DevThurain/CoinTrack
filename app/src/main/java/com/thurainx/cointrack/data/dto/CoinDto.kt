package com.thurainx.cointrack.data.dto

import com.thurainx.cointrack.domain.model.Coin

data class CoinDto(
    val id: String?,
    val is_active: Boolean?,
    val is_new: Boolean?,
    val name: String?,
    val rank: Int?,
    val symbol: String?,
    val type: String?
)

fun CoinDto.toCoin() : Coin{
    return Coin(
        id = id,
        is_active = is_active,
        is_new = is_new,
        name = name,
        rank = rank,
    )
}
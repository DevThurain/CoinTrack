package com.thurainx.cointrack.domain.use_case

import com.thurainx.cointrack.common.Resource
import com.thurainx.cointrack.data.dto.CoinDto
import com.thurainx.cointrack.domain.model.Coin
import com.thurainx.cointrack.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinList @Inject constructor(
    private val repository: CoinRepository
){

    operator fun invoke() : Flow<Resource<List<Coin>>> = flow {
        try {
            emit(Resource.Loading<List<Coin>>())
            val coins = repository.getCoinList()
            emit(Resource.Success<List<Coin>>(data = coins))
        }catch (e: HttpException){
            emit(Resource.Error<List<Coin>>(message = e.localizedMessage.toString()))

        }catch (e: IOException){
            emit(Resource.Error<List<Coin>>(message = "Network Connection Error"))

        }
    }
}
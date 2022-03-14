package com.thurainx.cointrack.domain.use_case

import com.thurainx.cointrack.common.Resource
import com.thurainx.cointrack.domain.model.CoinDetail
import com.thurainx.cointrack.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinDetailUseCase @Inject constructor(
    private  val repository: CoinRepository
){
    operator fun invoke(id: String) : Flow<Resource<CoinDetail>> = flow {
        try {
            emit(Resource.Loading<CoinDetail>())
            val data = repository.getCoinDetail(id = id)
            emit(Resource.Success<CoinDetail>(data = data))
        }catch (e: HttpException){
            emit(Resource.Error<CoinDetail>(message = e.localizedMessage.toString()))
        }catch (e: IOException){
            emit(Resource.Error<CoinDetail>(message = e.localizedMessage.toString()))
        }
    }
}
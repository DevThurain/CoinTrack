package com.thurainx.cointrack.presentation.coin_detail

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thurainx.cointrack.common.Constant
import com.thurainx.cointrack.common.Resource
import com.thurainx.cointrack.domain.model.CoinDetail
import com.thurainx.cointrack.domain.use_case.GetCoinDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val getCoinDetailUseCase: GetCoinDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel(){


    private val _state = mutableStateOf(CoinDetailState())
    val state : State<CoinDetailState> = _state


    init {
        savedStateHandle.get<String>(Constant.PARAM_COIN_ID)?.let {
            getCoinDetail(id = it)
        }
    }

    private fun getCoinDetail(id: String){
        Log.d("vm", "coinDetail vm called")

        getCoinDetailUseCase(id).onEach {
            when(it){
                is Resource.Loading<CoinDetail> -> _state.value = CoinDetailState(isLoading = true)

                is Resource.Success<CoinDetail> -> _state.value = CoinDetailState(detail = it.data)

                is Resource.Error<CoinDetail> -> _state.value = CoinDetailState(error = it.message ?: "Unknown Error")
            }
        }.launchIn(viewModelScope)
    }

}
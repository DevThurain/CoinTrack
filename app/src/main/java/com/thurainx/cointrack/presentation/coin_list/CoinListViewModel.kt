package com.thurainx.cointrack.presentation.coin_list

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thurainx.cointrack.common.Resource
import com.thurainx.cointrack.domain.use_case.GetCoinList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinList: GetCoinList
): ViewModel() {

    private var _state  = mutableStateOf(CoinListState())
    val state: State<CoinListState> = _state

    init {
        getCoins()
    }

    private fun getCoins(){
        Log.d("vm", "i am called")

        getCoinList().onEach {
            when(it){
                is Resource.Success -> _state.value = CoinListState(coins = it.data ?: emptyList())

                is Resource.Error -> _state.value = CoinListState(error = it.message ?: "Unknown Error")

                is Resource.Loading -> _state.value = CoinListState(isLoading = true)
            }

        }.launchIn(viewModelScope)
    }


}
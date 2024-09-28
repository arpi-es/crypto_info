package com.arpi.cryptoexchange.domain.use_case.get_coin

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arpi.cryptoexchange.common.Constants
import com.arpi.cryptoexchange.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class GetCoinViewModel @Inject constructor(
    private val getCoinUseCase: GetCoinUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {


    private var _state = mutableStateOf(CoinDetailState())
    val state: State<CoinDetailState> = _state

    init {
        savedStateHandle.get<String>(Constants.PARAM_COIN_ID)?.let { coinId ->
            getCoinDetail(coinId)
        }
    }

    private fun getCoinDetail(coinId: String) {
        getCoinUseCase(coinId).onEach { result ->

            when (result) {
                is Resource.Success -> {
                    _state.value = CoinDetailState(coin = result.data)
                }

                is Resource.Loading -> {
                    _state.value = CoinDetailState(isLoading = true)
                }

                is Resource.Error -> {
                    _state.value = CoinDetailState(error = result.message ?: "Unknown error")
                }
            }

        }.launchIn(viewModelScope) /* launchIn: Instead of use Flow with the collect function directly inside a coroutine, launchIn to launch the collection in a specific scope. */

    }

}
package com.arpi.cryptoexchange.presentation.coin_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arpi.cryptoexchange.common.Resource
import com.arpi.cryptoexchange.domain.use_case.get_coins.GetCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase,
) : ViewModel() {


    private var _state = mutableStateOf(CoinListState())
    val state: State<CoinListState> = _state

    init {
        getCoins()
    }

    private fun getCoins() {
        getCoinsUseCase().onEach { result ->

            when (result) {
                is Resource.Success -> {
                    _state.value = CoinListState(coins = result.data ?: emptyList())
                }

                is Resource.Loading -> {
                    _state.value = CoinListState(isLoading = true)
                }

                is Resource.Error -> {
                    _state.value = CoinListState(error = result.message ?: "Unknown error")
                }
            }

        }.launchIn(viewModelScope) /* launchIn: Instead of use Flow with the collect function directly inside a coroutine, launchIn to launch the collection in a specific scope. */

    }


}
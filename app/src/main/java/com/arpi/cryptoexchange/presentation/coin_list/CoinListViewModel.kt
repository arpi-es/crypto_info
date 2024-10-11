package com.arpi.cryptoexchange.presentation.coin_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arpi.cryptoexchange.domain.model.Coin
import com.arpi.cryptoexchange.domain.use_case.get_coins.GetCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase,
) : ViewModel() {


//    private val _coinState: MutableStateFlow<PagingData<Coin>> = MutableStateFlow(value = PagingData.empty())
//    val coinState: MutableStateFlow<PagingData<Coin>> get() = _coinState

    private val _coins = MutableStateFlow<PagingData<Coin>>(PagingData.empty())
    val coins: StateFlow<PagingData<Coin>> = _coins.asStateFlow()



    private var _state = mutableStateOf(CoinListState())
    val state: State<CoinListState> = _state

    init {
        getCoins()
    }



    private fun getCoins() {
        viewModelScope.launch {
            getCoinsUseCase.getCoins()
                .cachedIn(viewModelScope) // For caching and avoiding recomputation
                .collect { pagingData ->
                    _coins.value = pagingData
                }
        }
    }

//    private fun getCoins() {
//        getCoinsUseCase().onEach { result ->
//
//            when (result) {
//                is Resource.Success -> {
//                    _state.value = CoinListState(coins = result.data ?: emptyList())
//                }
//
//                is Resource.Loading -> {
//                    _state.value = CoinListState(isLoading = true)
//                }
//
//                is Resource.Error -> {
//                    _state.value = CoinListState(error = result.message ?: "Unknown error")
//                }
//            }
//
//        }.launchIn(viewModelScope) /* launchIn: Instead of use Flow with the collect function directly inside a coroutine, launchIn to launch the collection in a specific scope. */
//
//    }

}
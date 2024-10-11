package com.arpi.cryptoexchange.presentation.coin_list


import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.arpi.cryptoexchange.domain.model.Coin
import com.arpi.cryptoexchange.presentation.Screen
import com.arpi.cryptoexchange.presentation.coin_list.components.CoinListItem
import com.arpi.cryptoexchange.presentation.coin_list.components.CoinListStateProvider
import com.arpi.cryptoexchange.presentation.ui.theme.CryptoExchangeTheme
import com.arpi.cryptoexchange.presentation.ui.theme.ThemePreviews


@Composable
fun CoinListScreen(
        navController: NavController,
        viewModel: CoinListViewModel = hiltViewModel(),
) {


    val coins: LazyPagingItems<Coin> = viewModel.coins.collectAsLazyPagingItems()

    CoinListScreenShow(navController, coins)



}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinListScreenShow2(
    navController: NavController,
    coins: LazyPagingItems<Coin>,
    paddingValues: PaddingValues
) {


    val context = LocalContext.current
    LaunchedEffect(key1 = coins.loadState) {
        if(coins.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (coins.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if(coins.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = paddingValues,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                items(coins.itemCount) { index ->
                    coins[index]?.let {
                        CoinListItem(
                            coin = it,
                            onItemClick = {
                                navController.navigate(Screen.CoinDetailScreen.route + "/${it.id}")
                            })
                        HorizontalDivider(thickness = 1.dp)
                    }


                }
                coins.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item { CircularProgressIndicator()  }
                        }

                        loadState.refresh is LoadState.Error -> {
                            val error = coins.loadState.refresh as LoadState.Error
                            item {
                                Text(
                                    text = error.error.localizedMessage!!,
                                    color = MaterialTheme.colorScheme.error,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 20.dp)
                                        .align(Alignment.Center)
                                )
                            }
                        }

                        loadState.append is LoadState.Loading -> {
                            item { CircularProgressIndicator() }
                        }

                        loadState.append is LoadState.Error -> {
                            val error = coins.loadState.append as LoadState.Error
                            item {
                                Text(
                                    text = error.error.localizedMessage!!,
                                    color = MaterialTheme.colorScheme.error,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 20.dp)
                                        .align(Alignment.Center)
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinListScreenShow(
    navController: NavController,
    coins: LazyPagingItems<Coin>) {


    Scaffold(
            topBar = {
                TopAppBar(
                        title = {
                            Text(text = "Crypto Info",
                                    color = MaterialTheme.colorScheme.primary)
                        },
                )
            }
    ) { paddingValues ->
        CoinListScreenShow2(navController , coins, paddingValues)
//        Box(modifier = Modifier.fillMaxSize()) {
//            LazyColumn(
//                    contentPadding = paddingValues,
//                    modifier = Modifier
//                            .fillMaxSize()
//            ) {
//                items(items = state.coins) { coin ->
//                    CoinListItem(
//                            coin = coin,
//                            onItemClick = {
//                                navController.navigate(Screen.CoinDetailScreen.route + "/${coin.id}")
//                            })
//                    HorizontalDivider(thickness = 1.dp)
//                }
//            }
//
//            if (state.error.isNotBlank()) {
//                Text(
//                        text = state.error,
//                        color = MaterialTheme.colorScheme.error,
//                        textAlign = TextAlign.Center,
//                        modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(horizontal = 20.dp)
//                                .align(Alignment.Center)
//                )
//            }
//
//            if (state.isLoading) {
//                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
//            }
//        }
    }


}

@ThemePreviews
@Composable
fun CoinListScreenLoadingPreview(
    @PreviewParameter(CoinListStateProvider::class) state: CoinListState
) {
    CryptoExchangeTheme() {
        Surface {
//            CoinListScreenShow(navController = rememberNavController(), state = state)
        }

    }
}





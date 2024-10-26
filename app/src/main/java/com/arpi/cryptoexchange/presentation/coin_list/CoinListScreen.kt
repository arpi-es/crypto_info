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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.arpi.cryptoexchange.domain.model.Coin
import com.arpi.cryptoexchange.domain.model.coinList
import com.arpi.cryptoexchange.presentation.Screen
import com.arpi.cryptoexchange.presentation.coin_list.components.CoinListItem
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


@Composable
fun CoinListScreenShow(
    navController: NavController,
    coins: LazyPagingItems<Coin>
) {
    val context = LocalContext.current

    // Handles error states and shows a toast message if an error occurs
    LaunchedEffect(coins.loadState) {
        if (coins.loadState.refresh is LoadState.Error) {
            val error = (coins.loadState.refresh as LoadState.Error).error
            Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_LONG).show()
        }
    }


    Scaffold(
        topBar = { CoinListTopBar() }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            when (coins.loadState.refresh) {
                is LoadState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                else -> {
                    CoinListContent(
                        coins = coins,
                        paddingValues = paddingValues,
                        navController = navController
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinListTopBar() {
    TopAppBar(
        title = {
            Text(
                text = "Crypto Info",
                color = MaterialTheme.colorScheme.primary
            )
        }
    )
}

@Composable
fun CoinListContent(
    coins: LazyPagingItems<Coin>,
    paddingValues: PaddingValues,
    navController: NavController
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = paddingValues,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(coins.itemCount) { index ->
            coins[index]?.let { coin ->
                CoinListItem(
                    coin = coin,
                    onItemClick = {
                        navController.navigate(Screen.CoinDetailScreen.route + "/${coin.id}")
                    }
                )
                HorizontalDivider(thickness = 1.dp)
            }
        }

        when {
            coins.loadState.append is LoadState.Loading -> {
                item { LoadingItem() }
            }

            coins.loadState.append is LoadState.Error -> {
                val error = (coins.loadState.append as LoadState.Error).error
                item { ErrorItem(error.localizedMessage ?: "Unknown error") }
            }

            coins.loadState.refresh is LoadState.Error -> {
                val error = (coins.loadState.refresh as LoadState.Error).error
                item { ErrorItem(error.localizedMessage ?: "Unknown error") }
            }
        }
    }
}


@Composable
fun LoadingItem() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center) // align works inside BoxScope
        )
    }
}

@Composable
fun ErrorItem(errorMessage: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .align(Alignment.Center)
        )
    }
}


@ThemePreviews
@Composable
fun CoinListScreenListPreview() {
    val coins = provideFakeLazyPagingItems(coinList)

    CryptoExchangeTheme() {
        Surface {
            CoinListScreenShow(navController = rememberNavController(), coins = coins)
        }

    }
}

@ThemePreviews
@Composable
fun CoinListScreenErrorPreview() {
    val coins = provideErrorPagingItems()

    CryptoExchangeTheme() {
        Surface {
            CoinListScreenShow(navController = rememberNavController(), coins = coins)
        }
    }
}


@Composable
fun provideFakeLazyPagingItems(coins: List<Coin>): LazyPagingItems<Coin> {
    val pagingFlow = Pager(PagingConfig(pageSize = 20)) {
        object : PagingSource<Int, Coin>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Coin> {
                return LoadResult.Page(data = coins, prevKey = null, nextKey = null)
            }

            override fun getRefreshKey(state: PagingState<Int, Coin>): Int? {
                return null
            }
        }
    }.flow

    return pagingFlow.collectAsLazyPagingItems()  // Convert flow to LazyPagingItems
}

@Composable
fun provideErrorPagingItems(): LazyPagingItems<Coin> {
    val pagingFlow = Pager(PagingConfig(pageSize = 20)) {
        object : PagingSource<Int, Coin>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Coin> {
                return LoadResult.Error(Exception("Failed to load data"))
            }

            override fun getRefreshKey(state: PagingState<Int, Coin>): Int? {
                return null
            }
        }
    }.flow

    return pagingFlow.collectAsLazyPagingItems()  // Convert flow to LazyPagingItems
}





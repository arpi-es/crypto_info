package com.arpi.cryptoexchange.presentation.coin_list


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
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

    val state = viewModel.state.value
    CoinListScreenShow(navController, state)

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinListScreenShow(
    navController: NavController,
    state: CoinListState) {


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
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                    contentPadding = paddingValues,
                    modifier = Modifier
                            .fillMaxSize()
            ) {
                items(items = state.coins) { coin ->
                    CoinListItem(
                            coin = coin,
                            onItemClick = {
                                navController.navigate(Screen.CoinDetailScreen.route + "/${coin.id}")
                            })
                    HorizontalDivider(thickness = 1.dp)
                }
            }

            if (state.error.isNotBlank()) {
                Text(
                        text = state.error,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                                .align(Alignment.Center)
                )
            }

            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }


}

@ThemePreviews
@Composable
fun CoinListScreenLoadingPreview(
    @PreviewParameter(CoinListStateProvider::class) state: CoinListState
) {
    CryptoExchangeTheme() {
        Surface {
            CoinListScreenShow(navController = rememberNavController(), state = state)
        }

    }
}





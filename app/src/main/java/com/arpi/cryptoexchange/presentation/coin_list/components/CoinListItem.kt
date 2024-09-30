package com.arpi.cryptoexchange.presentation.coin_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arpi.cryptoexchange.domain.model.Coin

@Composable
fun CoinListItem(
    coin: Coin,
    onItemClick : (Coin) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(coin) }
            .padding(15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ){
        Text(
            text = "${coin.marketCapRank}",
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "${coin.symbol}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "${coin.currentPrice}",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.End
        )


    }

}
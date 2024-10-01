package com.arpi.cryptoexchange.presentation.coin_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.arpi.cryptoexchange.domain.model.Coin

@Composable
fun CoinListItem(
        coin: Coin,
        onItemClick: (Coin) -> Unit,
) {

    Row(
            modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick(coin) }
                    .padding(15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
                painter = rememberAsyncImagePainter(coin.image),
                contentDescription = null,
                modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape),
                contentScale = ContentScale.Crop


        )

        Spacer(modifier = Modifier.width(10.dp)) // Space between the image and text


        Column(
                modifier = Modifier.weight(1f)
        ) {
            Text(
                    text = coin.name,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()

            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                    text = "${coin.marketCapRank} • ${coin.symbol}",
                    style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.width(10.dp)) // Space between text and price


        Column(
                horizontalAlignment = Alignment.End
        ) {
            Text(
                    text = "$${coin.currentPrice}",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.End
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                    text = "MCap: $${coin.marketCap}",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.End
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCoinListItem() {

    val sampleCoin = Coin(

            id = "bitcoin",
            symbol = "BTC",
            name = "bitcoin rememberAsyncImagePainter",
            image = "https://coin-images.coingecko.com/coins/images/1/thumb/bitcoin.png?1696501400",
            currentPrice = 30000.0,
            marketCap = 30000,
            marketCapRank = 1
    )


    CoinListItem(coin = sampleCoin, onItemClick = {})
}
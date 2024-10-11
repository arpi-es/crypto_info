package com.arpi.cryptoexchange.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_coins")
data class CoinEntity(
    @PrimaryKey(autoGenerate = false )
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    val currentPrice: Double,
    val marketCap: Long,
    val marketCapRank: Int,
)
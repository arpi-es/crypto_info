package com.arpi.cryptoexchange.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.arpi.cryptoexchange.data.local.CoinEntity

@Dao
interface CoinDao {

    @Upsert
    suspend fun upsertAll(beers: List<CoinEntity>)

    @Query("SELECT * FROM tbl_coins")
    fun pagingSource(): PagingSource<Int, CoinEntity>

    @Query("DELETE FROM tbl_coins")
    suspend fun clearAll()
}
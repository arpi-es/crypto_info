package com.arpi.cryptoexchange.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arpi.cryptoexchange.data.local.dao.CoinDao
import com.arpi.cryptoexchange.data.local.dao.RemoteKeysDao


@Database(
    entities = [CoinEntity::class, RemoteKeys::class],
    version = 1
)
abstract class CoinDatabase : RoomDatabase() {

    abstract val coinDao: CoinDao
    abstract val remoteKeysDao: RemoteKeysDao
}
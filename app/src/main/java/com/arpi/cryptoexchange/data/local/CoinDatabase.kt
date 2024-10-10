package com.arpi.cryptoexchange.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arpi.cryptoexchange.data.local.dao.CoinDao


@Database(
    entities = [CoinEntity::class],
    version = 1
)
abstract class CoinDatabase : RoomDatabase() {

    abstract val dao: CoinDao
}
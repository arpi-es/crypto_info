package com.arpi.cryptoexchange.domain.use_case.get_coins


import androidx.paging.PagingData
import androidx.paging.map
import com.arpi.cryptoexchange.common.Resource
import com.arpi.cryptoexchange.data.local.CoinEntity
import com.arpi.cryptoexchange.data.mapper.toCoin
import com.arpi.cryptoexchange.domain.model.Coin
import com.arpi.cryptoexchange.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
        private val repository: CoinRepository,
) {

    suspend fun getCoins(): Flow<PagingData<Coin>> {
        return repository.getCoins().map { value: PagingData<CoinEntity> ->
            value.map { coinEntity ->
                coinEntity.toCoin()
            }
        }
    }


    operator fun invoke(): Flow<Resource<List<Coin>>> = flow {

        try {
            emit(Resource.Loading())
            val coins = repository.getAllCoins().map { it.toCoin() }
            emit(Resource.Success(coins))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Server not reachable"))
        }
    }
}



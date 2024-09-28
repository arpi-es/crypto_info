package com.arpi.cryptoexchange.domain.use_case.get_coin


import com.arpi.cryptoexchange.common.Resource
import com.arpi.cryptoexchange.data.remote.dto.coin_dto.toCoin
import com.arpi.cryptoexchange.domain.model.Coin
import com.arpi.cryptoexchange.domain.repository.CoinRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCoinsUseCase @Inject constructor(
    private val repository: CoinRepository,
) {

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



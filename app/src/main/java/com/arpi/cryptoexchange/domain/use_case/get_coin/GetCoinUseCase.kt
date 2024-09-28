package com.arpi.cryptoexchange.domain.use_case.get_coin


import com.arpi.cryptoexchange.common.Resource
import com.arpi.cryptoexchange.data.remote.dto.coin_detail.toCoinDetail
import com.arpi.cryptoexchange.domain.model.CoinDetail
import com.arpi.cryptoexchange.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(
    private val repository: CoinRepository,
) {

    operator fun invoke(coinId: String): Flow<Resource<CoinDetail>> = flow {

        try {
            emit(Resource.Loading())
            val coin = repository.getCoinById(coinId).toCoinDetail()
            emit(Resource.Success(coin))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Server not reachable"))
        }
    }
}



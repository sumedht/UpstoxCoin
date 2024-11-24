package com.sumedh.upstoxcoin.domain.use_case

import com.sumedh.upstoxcoin.common.Resource
import com.sumedh.upstoxcoin.data.dto.toCrypto
import com.sumedh.upstoxcoin.domain.model.Crypto
import com.sumedh.upstoxcoin.domain.repository.CryptoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCryptoUseCase @Inject constructor(
    private val repository: CryptoRepository
){

    operator fun invoke() : Flow<Resource<List<Crypto>>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.getCryptoCoins().map { it.toCrypto() }
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage?:"An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connectivity"))
        }
    }
}
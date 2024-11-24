package com.sumedh.upstoxcoin.data.repository

import com.sumedh.upstoxcoin.data.dto.CryptoDto
import com.sumedh.upstoxcoin.data.dto.Response
import com.sumedh.upstoxcoin.data.remote.CryptoApi
import com.sumedh.upstoxcoin.domain.repository.CryptoRepository
import javax.inject.Inject

class CryptoRepositoryImpl @Inject constructor(
    private val api: CryptoApi
): CryptoRepository {

    private var cryptoCoins : List<CryptoDto> = emptyList()

    override suspend fun getCryptoCoins(): List<CryptoDto> {
        val response = api.getCryptoCoins()
        cryptoCoins = response
        return response
    }

    override suspend fun getCachedCryptoCoins(): List<CryptoDto> {
        if (cryptoCoins.isEmpty()) {
            return getCryptoCoins()
        }
        return cryptoCoins
    }
}
package com.sumedh.upstoxcoin.domain.repository

import com.sumedh.upstoxcoin.data.dto.CryptoDto
import com.sumedh.upstoxcoin.data.dto.Response

interface CryptoRepository {
    suspend fun getCryptoCoins(): List<CryptoDto>
    suspend fun getCachedCryptoCoins(): List<CryptoDto>
}
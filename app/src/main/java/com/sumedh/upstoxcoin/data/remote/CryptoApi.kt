package com.sumedh.upstoxcoin.data.remote

import com.sumedh.upstoxcoin.data.dto.CryptoDto
import com.sumedh.upstoxcoin.data.dto.Response
import retrofit2.http.GET

interface CryptoApi {
    @GET("/")
    suspend fun getCryptoCoins(): List<CryptoDto>
}
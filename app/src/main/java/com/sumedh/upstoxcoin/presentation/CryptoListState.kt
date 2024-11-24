package com.sumedh.upstoxcoin.presentation

import com.sumedh.upstoxcoin.domain.model.Crypto

data class CryptoListState(
    val isLoading: Boolean = false,
    val cryptoCurrency: List<Crypto> = emptyList(),
    val error: String = ""
)
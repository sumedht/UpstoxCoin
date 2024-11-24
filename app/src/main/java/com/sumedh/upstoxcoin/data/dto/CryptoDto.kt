package com.sumedh.upstoxcoin.data.dto

import com.google.gson.annotations.SerializedName
import com.sumedh.upstoxcoin.domain.model.Crypto

data class CryptoDto(
    val name: String,
    val symbol: String,
    @SerializedName("is_new")
    val isNew: Boolean,
    @SerializedName("is_active")
    val isActive: Boolean,
    val type: String
)

fun CryptoDto.toCrypto() : Crypto {
    return Crypto(
        name = name,
        symbol = symbol,
        isNew = isNew,
        isActive = isActive,
        type = type,
    )
}

fun Crypto.toCrypto() : CryptoDto {
    return CryptoDto(
        name = name,
        symbol = symbol,
        isNew = isNew,
        isActive = isActive,
        type = type,
    )
}
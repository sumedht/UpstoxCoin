package com.sumedh.upstoxcoin.domain.model

import com.google.gson.annotations.SerializedName

data class Crypto(
    val name: String,
    val symbol: String,
    val isNew: Boolean,
    val isActive: Boolean,
    val type: String
)

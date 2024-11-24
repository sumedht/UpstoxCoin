package com.sumedh.upstoxcoin.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sumedh.upstoxcoin.domain.model.Crypto

@Composable
fun CryptoCoinItem(coin: Crypto) {
    val textColor = if (coin.isActive) Color.Black else Color.Gray

    Column(
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = coin.name,
            color = textColor,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.weight(0.5f)
        )
        Text(
            text = coin.symbol,
            color = textColor,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.weight(0.5f)
        )
    }
}
package com.sumedh.upstoxcoin.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sumedh.upstoxcoin.domain.model.Crypto

@Composable
fun CryptoCoinItem(coin: Crypto) {
    val textColor = if (coin.isActive) Color.Black else Color.Gray

    Row(
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = coin.name,
                color = textColor,
                style = MaterialTheme.typography.subtitle1
            )
            Text(
                text = coin.symbol,
                color = textColor,
                style = MaterialTheme.typography.subtitle2
            )
        }

        if (coin.type == "coin") {
            Text(
                text = "Coin",
                color = textColor,
                style = MaterialTheme.typography.subtitle2
            )
        } else {
            Text(
                text = "Token",
                color = textColor,
                style = MaterialTheme.typography.subtitle2
            )
        }
    }
}
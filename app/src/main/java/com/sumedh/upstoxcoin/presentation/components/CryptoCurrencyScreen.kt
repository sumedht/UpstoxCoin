package com.sumedh.upstoxcoin.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.sumedh.upstoxcoin.presentation.viewmodel.CryptoViewModel
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CryptoCurrencyScreen(
    viewModel: CryptoViewModel = hiltViewModel()
) {
    val displayCoins = viewModel.getDisplayCoins()

    Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        SearchBar(
            onSearch = { query -> viewModel.updateSearchQuery(query) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        FilterOptions { b, filter, isSelected ->
            when (filter) {
                "Active Coins" -> isSelected?.let { viewModel.toggleActiveFilter(it) }
                "Inactive Coins" -> isSelected?.let { viewModel.toggleInactiveFilter(it) }
                "Only Tokens" -> isSelected?.let { viewModel.toggleOnlyTokensFilter(it) }
                "Only Coins" -> isSelected?.let { viewModel.toggleOnlyCoinsFilter(it) }
                "New Coins" -> isSelected?.let { viewModel.toggleNewCoinsFilter(it) }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (displayCoins.value.isEmpty()) {
            if (viewModel.cryptoCoins.value.cryptoCurrency.isEmpty()) {
                Text(
                    text = "No results found",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                LazyColumn {
                    items(viewModel.cryptoCoins.value.cryptoCurrency) { coin ->
                        CryptoCoinItem(coin)
                        Divider(color = Color.Gray, thickness = 1.dp)
                    }
                }
            }
        } else {
            LazyColumn {
                items(displayCoins.value) { coin ->
                    CryptoCoinItem(coin)
                    Divider(color = Color.Gray, thickness = 1.dp)
                }
            }
        }

    }
}
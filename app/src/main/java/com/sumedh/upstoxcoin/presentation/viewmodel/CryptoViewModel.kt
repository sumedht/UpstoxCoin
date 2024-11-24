package com.sumedh.upstoxcoin.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumedh.upstoxcoin.common.Resource
import com.sumedh.upstoxcoin.domain.model.Crypto
import com.sumedh.upstoxcoin.domain.use_case.FilterCryptoUseCase
import com.sumedh.upstoxcoin.domain.use_case.GetCryptoUseCase
import com.sumedh.upstoxcoin.domain.use_case.SearchCryptoCoinsUseCase
import com.sumedh.upstoxcoin.presentation.CryptoListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoViewModel @Inject constructor(
    private val filterCryptoUseCase: FilterCryptoUseCase,
    private val getCryptoUseCase: GetCryptoUseCase,
    private val searchCryptosUseCase: SearchCryptoCoinsUseCase
) : ViewModel() {

    private val _cryptoCoins = mutableStateOf(CryptoListState())
    val cryptoCoins: State<CryptoListState> = _cryptoCoins

    private val _searchResults = mutableStateOf(CryptoListState())

    private val _filteredCoins = mutableStateOf(CryptoListState())

    private val _searchQuery = MutableStateFlow("")


    private val _activeFilter = mutableStateOf(false)
    private val _inactiveFilter = mutableStateOf(false)
    private val _onlyTokensFilter = mutableStateOf(false)
    private val _onlyCoinsFilter = mutableStateOf(false)
    private val _newCoinsFilter = mutableStateOf(false)

    init {
        getCryptoCurrency()
    }

    fun toggleActiveFilter(isSelected: Boolean) {
        _activeFilter.value = isSelected
        applyFilters()
    }

    fun toggleInactiveFilter(isSelected: Boolean) {
        _inactiveFilter.value = isSelected
        applyFilters()
    }

    fun toggleOnlyTokensFilter(isSelected: Boolean) {
        _onlyTokensFilter.value = isSelected
        applyFilters()
    }

    fun toggleOnlyCoinsFilter(isSelected: Boolean) {
        _onlyCoinsFilter.value = isSelected
        applyFilters()
    }

    fun toggleNewCoinsFilter(isSelected: Boolean) {
        _newCoinsFilter.value = isSelected
        applyFilters()
    }

    private fun getCryptoCurrency() {
        getCryptoUseCase().onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _cryptoCoins.value = CryptoListState(cryptoCurrency = result.data?: emptyList())
                }
                is Resource.Error -> {
                    _cryptoCoins.value = CryptoListState(error = result.message?:"An Unexpected error occurred")
                }
                is Resource.Loading -> {
                    _cryptoCoins.value = CryptoListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

     private fun searchCryptoCurrency(query: Flow<String>) {
        searchCryptosUseCase(query, _filteredCoins.value.cryptoCurrency).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _searchResults.value = CryptoListState(cryptoCurrency = result.data?: emptyList())
                }
                is Resource.Error -> {
                    _searchResults.value = CryptoListState(error = result.message?:"An Unexpected error occurred")
                }
                is Resource.Loading -> {
                    _searchResults.value = CryptoListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

//    private fun observeSearchQuery() {
//        viewModelScope.launch {
//            searchCryptoCurrency(_searchQuery)
//        }
//    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            searchCryptoCurrency(_searchQuery)
        }
    }

    private fun applyFilters() {
        filterCryptoUseCase(
            activeFilter = _activeFilter.value,
            inactiveFilter = _inactiveFilter.value,
            onlyTokensFilter = _onlyTokensFilter.value,
            onlyCoinsFilter = _onlyCoinsFilter.value,
            newCoinsFilter = _newCoinsFilter.value
        ).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _filteredCoins.value = CryptoListState(cryptoCurrency = result.data?: emptyList())
                }
                is Resource.Error -> {
                    _filteredCoins.value = CryptoListState(error = result.message?:"An Unexpected error occurred")
                }
                is Resource.Loading -> {
                    _filteredCoins.value = CryptoListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getDisplayCoins(): State<List<Crypto>> {
        return derivedStateOf {
            if (_searchResults.value.cryptoCurrency.isNotEmpty()) {
                _searchResults.value.cryptoCurrency
            } else {
                _filteredCoins.value.cryptoCurrency
            }
        }
    }
}
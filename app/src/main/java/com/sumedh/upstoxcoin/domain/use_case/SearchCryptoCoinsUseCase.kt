package com.sumedh.upstoxcoin.domain.use_case

import com.sumedh.upstoxcoin.common.Resource
import com.sumedh.upstoxcoin.data.dto.CryptoDto
import com.sumedh.upstoxcoin.data.dto.toCrypto
import com.sumedh.upstoxcoin.domain.model.Crypto
import com.sumedh.upstoxcoin.domain.repository.CryptoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchCryptoCoinsUseCase @Inject constructor(
    private val repository: CryptoRepository
) {
    private var lastQuery: String = ""
    operator fun invoke(queryFlow: Flow<String>, cryptoCoins: List<Crypto>): Flow<Resource<List<Crypto>>> = flow {
        var coins: List<Crypto> = cryptoCoins
        queryFlow
            .debounce(300)
            .distinctUntilChanged()
            .collect { query ->
                if (query.isBlank() || query.length < 2) {
                    emit(Resource.Error("Query must have at least 2 characters"))
                    return@collect
                }

                emit(Resource.Loading())
                lastQuery = query

                try {
                    if (coins.isEmpty()) {
                        coins = repository.getCachedCryptoCoins().map { it.toCrypto() }
                    }
                    val result = coins.filter { crypto ->
                        crypto.name.contains(query, ignoreCase = true) ||
                                crypto.symbol.contains(query, ignoreCase = true)
                    }
                    emit(Resource.Success(result))
                } catch (e: Exception) {
                    emit(Resource.Error("An error occurred: ${e.message}"))
                }
            }
    }
}
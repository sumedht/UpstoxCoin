package com.sumedh.upstoxcoin.domain.use_case

import com.sumedh.upstoxcoin.common.Resource
import com.sumedh.upstoxcoin.data.dto.toCrypto
import com.sumedh.upstoxcoin.domain.model.Crypto
import com.sumedh.upstoxcoin.domain.repository.CryptoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FilterCryptoUseCase @Inject constructor(
    private val repository: CryptoRepository
){
    operator fun invoke(
        activeFilter: Boolean? = null,
        inactiveFilter: Boolean? = null,
        onlyTokensFilter: Boolean? = null,
        onlyCoinsFilter: Boolean? = null,
        newCoinsFilter: Boolean? = null
    ): Flow<Resource<List<Crypto>>> = flow {
        val cryptos = repository.getCachedCryptoCoins().map { it.toCrypto() }
        val filters = cryptos.filter { coin ->
            val isActive = coin.isActive
            val isToken = coin.type == "token"

            val matchesActiveFilter = if (activeFilter == true) isActive else true
            val matchesInactiveFilter = if (inactiveFilter == true) !isActive else true
            val matchesOnlyTokensFilter = if (onlyTokensFilter == true) isToken else true
            val matchesOnlyCoinsFilter = if (onlyCoinsFilter == true) !isToken else true
            val matchesNewCoinsFilter = if (newCoinsFilter == true) coin.isNew else true

            matchesActiveFilter && matchesInactiveFilter && matchesOnlyTokensFilter &&
                    matchesOnlyCoinsFilter && matchesNewCoinsFilter
        }

        emit(Resource.Success((filters)))
    }
}
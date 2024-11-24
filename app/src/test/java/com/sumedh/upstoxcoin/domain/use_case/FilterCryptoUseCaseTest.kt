package com.sumedh.upstoxcoin.domain.use_case

import com.sumedh.upstoxcoin.common.Resource
import com.sumedh.upstoxcoin.data.dto.toCrypto
import com.sumedh.upstoxcoin.domain.model.Crypto
import com.sumedh.upstoxcoin.domain.repository.CryptoRepository
import org.junit.Assert.*

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class FilterCryptoUseCaseTest {

    private lateinit var filterCryptoUseCase: FilterCryptoUseCase
    private lateinit var repository: CryptoRepository

    @Before
    fun setup() {
        repository = mock()
        filterCryptoUseCase = FilterCryptoUseCase(repository)
    }

    @Test
    fun `invoke should filter active cryptos correctly`() = runTest {
        val mockCryptoList = listOf(
            Crypto(name = "Bitcoin", symbol = "BTC", isActive = true, type = "coin", isNew = false),
            Crypto(name = "Ethereum", symbol = "ETH", isActive = false, type = "coin", isNew = false)
        )
        whenever(repository.getCachedCryptoCoins()).thenReturn(mockCryptoList.map { it.toCrypto() })

        val result = filterCryptoUseCase(activeFilter = true).first()

        assert(result is Resource.Success)
        val successResult = result as Resource.Success<List<Crypto>>
        assertEquals(1, successResult.data?.size)
        assertEquals("Bitcoin", successResult.data?.first()?.name)
    }

    @Test
    fun `invoke should filter inactive cryptos correctly`() = runTest {
        val mockCryptoList = listOf(
            Crypto(name = "Bitcoin", symbol = "BTC", isActive = true, type = "coin", isNew = false),
            Crypto(name = "Ethereum", symbol = "ETH", isActive = false, type = "coin", isNew = false)
        )
        whenever(repository.getCachedCryptoCoins()).thenReturn(mockCryptoList.map { it.toCrypto() })

        val result = filterCryptoUseCase(inactiveFilter = true).first()

        assert(result is Resource.Success)
        val successResult = result as Resource.Success<List<Crypto>>
        assertEquals(1, successResult.data?.size) // Only one inactive crypto (Ethereum)
        assertEquals("Ethereum", successResult.data?.first()?.name)
    }

    @Test
    fun `invoke should filter only tokens correctly`() = runTest {
        val mockCryptoList = listOf(
            Crypto(name = "Bitcoin", symbol = "BTC", isActive = true, type = "coin", isNew = false),
            Crypto(name = "Tether", symbol = "USDT", isActive = true, type = "token", isNew = false)
        )
        whenever(repository.getCachedCryptoCoins()).thenReturn(mockCryptoList.map { it.toCrypto() })

        val result = filterCryptoUseCase(onlyTokensFilter = true).first()

        assert(result is Resource.Success)
        val successResult = result as Resource.Success<List<Crypto>>
        assertEquals(1, successResult.data?.size) // Only one token (Tether)
        assertEquals("Tether", successResult.data?.first()?.name)
    }

    @Test
    fun `invoke should filter only coins correctly`() = runTest {
        val mockCryptoList = listOf(
            Crypto(name = "Bitcoin", symbol = "BTC", isActive = true, type = "coin", isNew = false),
            Crypto(name = "Tether", symbol = "USDT", isActive = true, type = "token", isNew = false)
        )
        whenever(repository.getCachedCryptoCoins()).thenReturn(mockCryptoList.map { it.toCrypto() })

        val result = filterCryptoUseCase(onlyCoinsFilter = true).first()

        assert(result is Resource.Success)
        val successResult = result as Resource.Success<List<Crypto>>
        assertEquals(1, successResult.data?.size) // Only one coin (Bitcoin)
        assertEquals("Bitcoin", successResult.data?.first()?.name)
    }

    @Test
    fun `invoke should filter new coins correctly`() = runTest {
        val mockCryptoList = listOf(
            Crypto(name = "Bitcoin", symbol = "BTC", isActive = true, type = "coin", isNew = false),
            Crypto(name = "Shiba Inu", symbol = "SHIB", isActive = true, type = "coin", isNew = true)
        )
        whenever(repository.getCachedCryptoCoins()).thenReturn(mockCryptoList.map { it.toCrypto() })

        val result = filterCryptoUseCase(newCoinsFilter = true).first()

        assert(result is Resource.Success)
        val successResult = result as Resource.Success<List<Crypto>>
        assertEquals(1, successResult.data?.size)
        assertEquals("Shiba Inu", successResult.data?.first()?.name)
    }

    @Test
    fun `invoke should return empty list when no filters match`() = runTest {
        val mockCryptoList = listOf(
            Crypto(name = "Bitcoin", symbol = "BTC", isActive = true, type = "coin", isNew = false),
            Crypto(name = "Tether", symbol = "USDT", isActive = true, type = "token", isNew = false)
        )
        whenever(repository.getCachedCryptoCoins()).thenReturn(mockCryptoList.map { it.toCrypto() })

        val result = filterCryptoUseCase(activeFilter = false).first()

        assert(result is Resource.Success)
        val successResult = result as Resource.Success<List<Crypto>>
        assertEquals(0, successResult.data?.size)
    }
}
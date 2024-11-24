package com.sumedh.upstoxcoin.domain.use_case

import com.sumedh.upstoxcoin.common.Resource
import com.sumedh.upstoxcoin.common.Resource.Loading
import com.sumedh.upstoxcoin.data.dto.toCrypto
import com.sumedh.upstoxcoin.domain.model.Crypto
import com.sumedh.upstoxcoin.domain.repository.CryptoRepository
import org.junit.Assert.*

import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.whenever
import retrofit2.HttpException
import java.io.IOException

class GetCryptoUseCaseTest {

    private lateinit var getCryptoUseCase: GetCryptoUseCase
    private lateinit var repository: CryptoRepository

    @Before
    fun setup() {
        repository = mock(CryptoRepository::class.java)
        getCryptoUseCase = GetCryptoUseCase(repository)
    }

    @Test
    fun `invoke should return success when repository call is successful`(): Unit = runTest {
        // Arrange
        val mockCryptoList = listOf(Crypto(name = "Bitcoin", symbol = "BTC", true, true, "coin"))
        `when`(repository.getCryptoCoins()).thenReturn(mockCryptoList.map { it.toCrypto() })

        // Act
        val result = getCryptoUseCase().first()

        // Assert
        assert(result is Resource.Success)
        val successResult = result as Resource.Success<List<Crypto>>
        assertEquals(mockCryptoList, successResult.data)
    }

    @Test
    fun `invoke should return error when IOException is thrown`() = runTest {
        val ioException = IOException("Network Error")
        `when`(repository.getCryptoCoins()).thenThrow(ioException)

        val result = getCryptoUseCase().first()

        assert(result is Resource.Error)
        val errorResult = result as Resource.Error<List<Crypto>>
        assertEquals("Couldn't reach server. Check your internet connectivity", errorResult.message) // Check the error message
    }
}
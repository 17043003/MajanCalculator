package com.ishzk.android.majancalculator.waithand

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ishzk.android.majancalculator.domain.*
import com.ishzk.android.majancalculator.repository.RetrofitWaitHandRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class WaitHandRepositoryShould {
    private val service: WaitHandService = mock(WaitHandService::class.java)
    private val repository = RetrofitWaitHandRepository(service)
    private val closeTiles = CloseTiles("m123456789s1234")
    private val response = WaitHandResponse(listOf(WinHand("1s", ""), WinHand("4s", "")))

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun getWaitHandsFromService() = runTest {
        val handTiles = HandTiles(closeTiles)
        repository.fetchWaitHand(handTiles.toWaitHandRequest()!!)

        verify(service, times(1)).getWaitHand("123456789", "1234", "", "", "")
    }

    @Test
    fun emitWaitHands() = runTest {
        whenever(service.getWaitHand("123456789", "1234", "", "", "")).thenReturn(
            flow { emit(Result.success(response)) }
        )

        val handTiles = HandTiles(closeTiles)
        val result = repository.fetchWaitHand(handTiles.toWaitHandRequest()!!).first()
        assertEquals(response, result)
    }
}
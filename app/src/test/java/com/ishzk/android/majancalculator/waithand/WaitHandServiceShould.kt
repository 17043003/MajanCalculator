package com.ishzk.android.majancalculator.waithand

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ishzk.android.majancalculator.domain.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify


@OptIn(ExperimentalCoroutinesApi::class)
class WaitHandServiceShould {
    private val api: PointAPI = mock(PointAPI::class.java)
    private val service = WaitHandService(api)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun getWaitHandResults() = runTest {
        service.getWaitHand("123456789", "1234", "", "", "").first()

        verify(api, times(1)).getWaitHand("123456789", "1234", "", "", "")
    }
}

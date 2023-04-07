package com.ishzk.android.majancalculator.repository

import com.ishzk.android.majancalculator.domain.WaitHandRepository
import com.ishzk.android.majancalculator.domain.WaitHandRequest
import com.ishzk.android.majancalculator.domain.WaitHandResponse
import com.ishzk.android.majancalculator.domain.WaitHandService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RetrofitWaitHandRepository @Inject constructor(
    private val service: WaitHandService
) : WaitHandRepository {
    override suspend fun fetchWaitHand(request: WaitHandRequest): Flow<WaitHandResponse> = withContext(Dispatchers.IO) {
            service.getWaitHand(
                request.hands.man,
                request.hands.sou,
                request.hands.pin,
                request.hands.honors,
                request.opens
            ).filter {
                it.isSuccess
            }.map { it.getOrNull()!! }
        }
}
package com.ishzk.android.majancalculator.repository

import com.ishzk.android.majancalculator.domain.WaitHandRepository
import com.ishzk.android.majancalculator.domain.WaitHandRequest
import com.ishzk.android.majancalculator.domain.WaitHandResponse
import com.ishzk.android.majancalculator.domain.WaitHandService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RetrofitWaitHandRepository @Inject constructor(
    private val service: WaitHandService
): WaitHandRepository {
    override suspend fun fetchWaitHand(request: WaitHandRequest): Flow<WaitHandResponse> = flow {
        withContext(Dispatchers.IO){
            request.let {
                service.getWaitHand(it.hands.man, it.hands.sou, it.hands.pin, it.hands.honors, it.opens)
            }
        }
    }
}
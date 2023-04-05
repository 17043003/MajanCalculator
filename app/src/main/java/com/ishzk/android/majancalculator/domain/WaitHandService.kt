package com.ishzk.android.majancalculator.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import javax.inject.Inject

class WaitHandService @Inject constructor(
    private val api: PointAPI
) {
    suspend fun getWaitHand(
        man: String,
        sou: String,
        pin: String,
        honors: String,
        opens: String,
    ): Flow<Result<WaitHandResponse>> {
        val response = api.getWaitHand(man, sou, pin, honors, opens).execute()
        val body = response.body()

        return flow {
            if (body == null) {
                emit(Result.failure(java.lang.RuntimeException("API call failed.")))
                return@flow
            }

            val waitHandResponse = WaitHandResponse(body.win)
            emit( Result.success(waitHandResponse) )
        }
    }
}
package com.ishzk.android.majancalculator.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

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
    ): Flow<Result<WaitHandResponse>> = withContext(Dispatchers.IO) {
        flow{
            val response = api.getWaitHand(man, sou, pin, honors, opens).execute()
            val body = response.body()

            if (body == null) {
                emit(Result.failure(java.lang.RuntimeException("API call failed, body is null.")))
                return@flow
            }

            val waitHandResponse = WaitHandResponse(body.win)
            emit(Result.success(waitHandResponse))
        }
    }.catch {
        emit(Result.failure(java.lang.RuntimeException("API call failed.")))
    }
}
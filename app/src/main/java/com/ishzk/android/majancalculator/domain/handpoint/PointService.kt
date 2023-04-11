package com.ishzk.android.majancalculator.domain.handpoint

import com.ishzk.android.majancalculator.domain.PointAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PointService @Inject constructor(
    private val api: PointAPI
) {
    suspend fun getPoint(
        m: String,
        s: String,
        p: String,
        h: String,
        w_m: String,
        w_s: String,
        w_p: String,
        w_h: String,
        dora_m: String,
        dora_s: String,
        dora_p: String,
        dora_h: String,
        tsumo: Boolean,
    ): Flow<Result<PointResponseData>> {
        val response = api.getPoint(m, s, p, h, w_m, w_s, w_p, w_h, dora_m, dora_s, dora_p, dora_h, tsumo).execute()

        return flow {
            val body = response.body()

            if(body == null){
                emit(Result.failure(java.lang.RuntimeException("API call failed.")))
                return@flow
            }

            val pointResponse = PointResponseData(
                body.cost.total,
                body.cost.yaku_level,
                body.han,
                body.fu,
                body.yaku,
            )
            emit(Result.success(pointResponse))
        }
    }

}
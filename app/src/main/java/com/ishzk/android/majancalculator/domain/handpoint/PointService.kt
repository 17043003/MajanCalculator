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
        opens: String,
        ownWind: Int,
        fieldWind: Int,
        tsumo: Boolean,
        yakus: String,
    ): Flow<Result<PointResponseData>> {
        val response = api.getPoint(m, s, p, h, w_m, w_s, w_p, w_h, opens, ownWind, fieldWind, tsumo, yakus).execute()

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
                body.fu_details,
            )
            emit(Result.success(pointResponse))
        }
    }

}
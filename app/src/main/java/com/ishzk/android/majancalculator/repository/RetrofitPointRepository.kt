package com.ishzk.android.majancalculator.repository

import com.ishzk.android.majancalculator.domain.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RetrofitPointRepository @Inject constructor(
    private val service: PointService
): PointRepository {

    override suspend fun fetchPoint(request: PointRequestData): Flow<PointResponseData> = flow {
        val h = request.hands
        val w = request.win_tile
        val d = request.dora_indicator

        withContext(Dispatchers.IO) {
            service.getPoint(
                h.man,
                h.pin,
                h.sou,
                h.honors,
                w.man,
                w.pin,
                w.sou,
                w.honors,
                d.man,
                d.pin,
                d.sou,
                d.honors,
                request.isTsumo
            )
        }
    }
}
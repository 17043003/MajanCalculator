package com.ishzk.android.majancalculator.domain.handpoint

import kotlinx.coroutines.flow.Flow

interface PointRepository {
    suspend fun fetchPoint(request: PointRequestData): Flow<PointResponseData>
}

data class PointRequestData(
    val hands: TileKinds,
    val win_tile: TileKinds,
    val opens: String,
    val ownWind: Int,
    val fieldWind: Int,
    val isTsumo: Boolean,
    val yakus: String,
)

data class PointResponseData(
    val total: Int,
    val level: String,
    val han: Int,
    val fu: Int,
    val yaku: List<YakuDetail>,
    val fuDetail: List<FuDetail>,
)

data class TileKinds(
    val man: String = "",
    val sou: String = "",
    val pin: String = "",
    val honors: String = "",
)
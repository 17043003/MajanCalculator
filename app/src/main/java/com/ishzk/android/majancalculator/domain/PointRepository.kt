package com.ishzk.android.majancalculator.domain

import kotlinx.coroutines.flow.Flow

interface PointRepository {
    suspend fun fetchPoint(request: PointRequestData): Flow<PointResponseData>
}

data class PointRequestData(
    val hands: TileKinds,
    val win_tile: TileKinds,
    val dora_indicator: TileKinds,
    val isTsumo: Boolean,
)

data class PointResponseData(
    val total: Int,
    val level: String,
    val han: Int,
    val fu: Int,
    val yaku: List<YakuDetail>,
)

data class TileKinds(
    val man: String = "",
    val sou: String = "",
    val pin: String = "",
    val honors: String = "",
)
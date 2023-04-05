package com.ishzk.android.majancalculator.domain

import kotlinx.coroutines.flow.Flow

interface WaitHandRepository {
    suspend fun fetchWaitHand(request: WaitHandRequest): Flow<WaitHandResponse>
}

fun HandTiles.toWaitHandRequest(): WaitHandRequest? {
    if(this.handSize() != 13) return null

    val tiles = this.closeTiles.let {
        TileKinds(man = it.man(), sou = it.sou(), pin = it.pin(), honors = it.honors())
    }

    // No open tiles are.
    if(this.openTiles.size == 0) return WaitHandRequest(tiles, "")

    val openString = this.openTiles.joinToString("_") { it.hand }
    return WaitHandRequest(tiles, openString)
}

data class WaitHandRequest(
    val hands: TileKinds,
    val opens: String,
)

data class WaitHandResponse(
    val winHands: List<WinHand>
)
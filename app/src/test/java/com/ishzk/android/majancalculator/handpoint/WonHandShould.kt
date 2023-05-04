package com.ishzk.android.majancalculator.handpoint

import com.ishzk.android.majancalculator.domain.handpoint.CloseTiles
import com.ishzk.android.majancalculator.domain.handpoint.Tile
import com.ishzk.android.majancalculator.domain.handpoint.WonHand
import org.junit.Test

class WonHandShould {
    @Test
    fun won_hand_is_valid(){
        validWonHand()
    }

    @Test(expected = java.lang.IllegalArgumentException::class)
    fun won_hand_in_invalid(){
        invalidWonHand()
    }

    private fun validWonHand(): WonHand {
        val tiles = (1..9).map{ Tile("s", it) } + (1..4).map { Tile("m", it) }
        val closeTiles = CloseTiles(tiles)
        val winTile = Tile("m", 1)
        return WonHand(closeTiles, listOf(), winTile)
    }

    private fun invalidWonHand(): WonHand {
        val tiles = (1..9).map{ Tile("s", it) } + (1..4).map { Tile("m", it) } + Tile("s", 5)
        val closeTiles = CloseTiles(tiles)
        val winTile = Tile("m", 1)
        return WonHand(closeTiles, listOf(), winTile)
    }
}
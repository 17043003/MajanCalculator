package com.ishzk.android.majancalculator.domain

data class HandTiles(
    val closeTiles: CloseTiles,
    val openTiles: List<OpenTile>? = null,
    val winTile: WinTile,
)

class CloseTiles(
    private var tiles: String,
){
    fun add(value: String){
        if(value.length >= 13) tiles else tiles + value
    }
}

class WinTile(
   kind: TileKind,
   n: Int,
){
    var number = n
        set(value) { field = if(value in 1..9) value else -1 }

    val hand = if(number == -1) "" else "$kind$number"
}


sealed class TileKind{
    open val kind: String = ""
    override fun toString(): String {
        return kind
    }

    class Manzu: TileKind() {
        override val kind: String
            get() = "m"
    }

    class Souzu: TileKind() {
        override val kind: String
            get() = "s"
    }

    class Pinzu: TileKind() {
        override val kind: String
            get() = "p"
    }

    class Honor: TileKind() {
        override val kind: String
            get() = "h"
    }
}
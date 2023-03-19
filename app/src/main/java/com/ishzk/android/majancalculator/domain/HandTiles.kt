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
        if(value.length != 2) return

        val eachKinds = splitByKind()
        tiles = if(eachKinds == null) value
        else{
            val prefix = value.take(1)

            val foundKind = eachKinds.find { it.take(1) == prefix }
            if(foundKind == null){
                tiles += value
                return
            }

            eachKinds.joinToString("") { kind ->
                if (kind.take(1) != prefix) kind
                else {
                    val number = value.drop(1)
                    if(kind.count{ it == number.first() } >= 4) return@joinToString kind
                    prefix + (kind.drop(1) + number).split("").sorted().joinToString("")
                }
            }
        }
    }

    private fun splitByKind(): List<String>? {
        val pattern = Regex("""([mpsh]\d+)""")
        val results = pattern.findAll(tiles)

        return results.flatMap { result -> result.groups.map { group -> group?.value ?: "" }?.drop(1) }
            ?.toList()
    }

    override fun toString(): String {
        return tiles
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
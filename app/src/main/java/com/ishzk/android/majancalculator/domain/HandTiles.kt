package com.ishzk.android.majancalculator.domain


class Tile(hand: String){
    val kind: TileKind = TileKind.getKind(hand) ?: throw java.lang.IllegalStateException("Tile kind char is not contains.")
    val num: String = hand.last().toString()
}

data class HandTiles(
    val closeTiles: CloseTiles,
    val openTiles: MutableList<OpenTile> = mutableListOf(),
    val winTile: WinTile? = null,
){

    fun handSize(): Int = closeTiles.length() + (openTiles.size.times(3))
    private fun under13Tiles() = handSize() < 13

    private fun lessThan4SameTiles(tile: String): Boolean {
        val closes = closeTiles.splitByKindWithPrefix() ?: listOf()
        val opens = openTiles.flatMap { it.hands }
        val tiles = closes + opens
        return tiles.count { it == tile } < 4
    }

    private fun lessThan4SameTiles(tile: OpenTile): Boolean {
        val closes = closeTiles.splitByKindWithPrefix() ?: listOf()
        val opens = (openTiles + tile).flatMap { it.hands }
        val tiles = closes + opens

        return tiles.groupBy { it }.map { it.value.count() }.all { it <= 4 }
    }

    fun add(closeTile: String){
        if(under13Tiles() && lessThan4SameTiles(closeTile)) {
            closeTiles.add(closeTile)
        }
    }

    fun add(openTile: OpenTile){
        if(!lessThan4SameTiles(openTile)) return

        if(openTiles.size <= 3 && handSize() + 3 <= 13 && under13Tiles()) {
            openTiles.add(openTile)
        }
    }
}

class CloseTiles(
    private var tiles: String,
){
    fun man(): String = splitByKind()?.find { it.first() == 'm' }?.drop(1) ?: ""
    fun sou(): String = splitByKind()?.find { it.first() == 's' }?.drop(1) ?: ""
    fun pin(): String = splitByKind()?.find { it.first() == 'p' }?.drop(1) ?: ""
    fun honors(): String = splitByKind()?.find { it.first() == 'h' }?.drop(1) ?: ""

    private fun under13Tiles(): Boolean = length() <= 13

    fun add(value: String){
        if(value.length != 2) return
        if(!under13Tiles()) return

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

    fun splitByKindWithPrefix(): List<String>? {
        val list = splitByKind()
        return list?.flatMap {
            val prefix = it.first()
            val numbers = it.drop(1)

            numbers.map { number ->
                prefix + number.toString()
            }
        }
    }

    fun length(): Int = splitByKindWithPrefix()?.size ?: 0

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

    companion object {
        fun getKind(hand: String): TileKind? {
            return if(hand.length != 2){
                null
            }else{
                when(hand.first()){
                    'm' -> Manzu()
                    's' -> Souzu()
                    'p' -> Pinzu()
                    'h' -> Honor()
                    else -> null
                }
            }
        }
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
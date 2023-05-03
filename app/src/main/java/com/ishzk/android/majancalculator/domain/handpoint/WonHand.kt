package com.ishzk.android.majancalculator.domain.handpoint

import com.ishzk.android.majancalculator.domain.OpenTile

// 和了の形になっている手牌
class WonHand(
    val closeTiles: CloseTiles,
    val openTiles: List<OpenTile>,
    val wonTile: Tile?,
) {
    init {
        require(closeTiles.tiles.size in 0..13)
        require(openTiles.size.times(3) in 0..12)

        val list = closeTiles.tiles + openTiles.flatMap {
            val kind = it.hand.first().toString()
            it.hand.mapNotNull { it.digitToIntOrNull() }.map { Tile(kind, it) }
        } + wonTile
        val counts = list.groupBy { it }.map { it.key to it.value.count() }
        require(counts.all { it.second in 0..4 })
        require(size() in 0..14)
    }

    fun size() = closeTiles.size() + openTiles.size * 3 + 1

    fun toRequest(ownWind: Int, fieldWind: Int, isTsumo: Boolean, yakus: String): PointRequestData? {
        val tiles = closeTiles.tiles.map { it.kind to it.number }
        val m = tiles.filter { it.first == "m" }.joinToString("") { it.second.toString() }
        val s = tiles.filter { it.first == "s" }.joinToString("") { it.second.toString() }
        val p = tiles.filter { it.first == "p" }.joinToString("") { it.second.toString() }
        val h = tiles.filter { it.first == "h" }.joinToString("") { it.second.toString() }

        val hands = TileKinds(
            man = m,
            sou = s,
            pin = p,
            honors = h,
        )

        if(wonTile == null) return null
        val w = wonTile.toTileKinds()

        val opens = openTiles.map { it.hand }.joinToString("_")

        return PointRequestData(hands, w, opens, ownWind, fieldWind, isTsumo, yakus)
    }
}

// 牌1枚
data class Tile(
    val kind: String,
    val number: Int,
){
    init {
        require(kind in "msph") { "Tile kind string is not tile kind." }
        require(number in 1..9) { "Tile number is not in one to nine." }
    }

    override fun toString(): String = "$kind$number"

    fun toTileKinds(): TileKinds {
        val num = number.toString()
        return when(kind){
            "m" -> TileKinds(man = num)
            "s" -> TileKinds(sou = num)
            "p" -> TileKinds(pin = num)
            "h" -> TileKinds(honors = num)
            else -> TileKinds()
        }
    }
}

// 面前の牌(和了牌は含まない)
data class CloseTiles(
    var tiles: List<Tile> = listOf(),
){
    init {
        require(tiles.size in 0..13) { "CloseTiles size is not zero to thirteen" }
        require(eachTileSize().all { it.value in 0..4 }) { "CloseTiles have same tiles over four." }
    }

    private fun eachTileSize(): Map<Tile, Int>{
        return tiles.groupBy{ it }.map { it.key to it.value.count() }.toMap()
    }

    private fun sizeIsUnder13() = tiles.size in 0..13
    private fun sizeIsInFourEachKind() = eachTileSize().all { it.value in 0..4 }
    private fun isValid(): Boolean = sizeIsUnder13() && sizeIsInFourEachKind()

    fun size() = tiles.size

    fun add(tile: Tile): CloseTiles{
        return if(isValid()){
            try{
                val groupTiles = (tiles + tile).sortedBy { it.toString() }.groupBy { it.kind }
                val newTiles = listOf("m", "s", "p", "h").mapNotNull { groupTiles[it] }.flatten()
                CloseTiles(newTiles)
            }catch (e: IllegalArgumentException){
                this
            }
        }else{
            this
        }
    }

    fun remove(tile: Tile): CloseTiles{
        return if(tiles.isEmpty()){
            CloseTiles()
        }else{
            val list = tiles.toMutableList()
            if(list.remove(tile)) {
                CloseTiles(list)
            }else{
                this
            }
        }
    }

    override fun toString(): String = tiles.joinToString { it.toString() }
}
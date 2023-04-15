package com.ishzk.android.majancalculator.domain.handpoint

// 和了の形になっている手牌
class WonHand(
    val closeTiles: CloseTiles,
    val openTiles: OpenTiles,
    val wonTile: Tile,
) {
    init {
        require(closeTiles.tiles.size in 0..13)
        require(openTiles.tiles.size in 0..12)
        require(size() == 14)
    }

    fun size() = closeTiles.size() + openTiles.size() + 1
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

// 鳴いた牌の組をまとめたクラス
data class OpenTiles(
    var tiles: List<OpenTile> = listOf()
){
    init {
        require(tiles.size in 0..4) { "OpenTiles has open tile pairs more four." }
        require(size() in listOf(0, 3, 6, 9, 12)) { "OpenTiles size is not zero to thirteen." }
        require(eachTileSize().all { it.value in 0..4 }) { "OpenTiles has same tiles over four." }
    }
    private fun eachTileSize(): Map<Tile, Int>{
        return tiles.flatMap { it.tiles.groupBy { it }.map { it.key to it.value.count() } }.toMap()
    }

    private fun sizeIsUnder4() = tiles.size in 0..4
    private fun sizeIsInFourEachKind() = eachTileSize().all { it.value in 0..4 }
    private fun isValid() = sizeIsUnder4() && sizeIsInFourEachKind()

    fun size() = tiles.fold(0) { sum, openTile -> sum + openTile.size() }

    fun add(openTile: OpenTile){
        if(isValid()){
            tiles += openTile
        }
    }
}

// 鳴いた牌の一組
data class OpenTile(
    val kind: String,
    private val numbers: List<Int>,
){
    val tiles: List<Tile> = numbers.map { Tile(kind, it) }

    init {
        require(numbers.all { it in 1..9 }) { "OpenTile number is not one to nine." }
        require(tiles.size in 3..4) { "OpenTile tiles size is not 3 or 4." }
        require(tiles.all { it.kind == tiles.first().kind }) { "OpenTile has not same kind tiles." }
    }
    fun size() = 3
}

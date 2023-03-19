package com.ishzk.android.majancalculator.domain

sealed class OpenTile {
    open val hand: String = ""

    class Pon(
        kind: TileKind,
        private val n: String,
    ): OpenTile() {
        private fun isValid() = hasThree() && hasSameNumber()
        private fun hasThree() = n.length == 3
        private fun hasSameNumber() = n.map { it }.toSet().size == 1

        private val num = if(isValid()) n else -1

        override val hand = "$kind$num"
    }

    class Chi(
        kind: TileKind,
        private val n: String,
    ): OpenTile() {
        private fun isValid() = hasThree() && isSequential()
        private fun hasThree() = n.length == 3
        private fun isSequential(): Boolean {
            if(!hasThree()) return false
            val seqNumbers = n.toSortedSet().map { it.digitToInt() }
            return (seqNumbers[0] + 1 == seqNumbers[1]) && (seqNumbers[1] == seqNumbers[2] - 1)
        }

        private val num = if(isValid()) n else -1

        override val hand = "$kind$num"
    }

    class Kan(
        kind: TileKind,
        private val n: String,
        close: Boolean,
    ): OpenTile() {
        private fun isValid() = hasFour() && hasSameNumber()
        private fun hasFour() = n.length == 4
        private fun hasSameNumber() = n.map { it }.toSet().size == 1

        private val num = if(isValid()) n else -1
        private val closed = if(close) "" else "o"

        override val hand = "$kind$num$closed"
    }
}
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

        private val num: String = if(isValid()) n else ""

        override val hand: String = if(isValid()) "$kind$num" else ""

        companion object {
            fun sequenceNumbers(num: String): List<String?> {
                val n = num.toInt()
                return listOf(
                    listOf<Int>(n - 2, n - 1, n),
                    listOf<Int>(n - 1, n, n + 1),
                    listOf<Int>(n, n + 1, n + 2),
                )
                    .map { list -> if(list.all { number -> number in 1..9 }) list else null }
                    .map { list -> list?.joinToString("") }
            }
        }
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
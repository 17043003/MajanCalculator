package com.ishzk.android.majancalculator.domain

import kotlin.math.ceil
import kotlin.math.min
import kotlin.math.pow

class FannFu(
    fann: Int,
    fu: Int,
){
    val basePoint = fu * 2.0.pow(fann.toDouble())

    enum class ScoreGrade{
        MANGAN,
        HANEMAN,
        BAIMAN,
        SANBAIMAN,
        YAKUMAN,
    }
    val scoreGrade = when(fann){
        in 1..5 -> ScoreGrade.MANGAN
        in 6..7 -> ScoreGrade.HANEMAN
        in 8..10 -> ScoreGrade.BAIMAN
        in 11..12 -> ScoreGrade.SANBAIMAN
        else -> ScoreGrade.YAKUMAN
    }
}

data class TsumoPoint(
    val parentPayment: Int,
    val childPayment: Int,
)

data class RonPoint(
    val payment: Int,
)

abstract class Winner {
    abstract fun receiveTsumoPoints(fannFu: FannFu): TsumoPoint
    abstract fun receiveRonPoint(fannFu: FannFu): RonPoint
    internal fun ceilPoint(point: Int): Int{
        return ceil(point / 100.0).toInt() * 100
    }
}

class WinnerParent: Winner(){
    override fun receiveTsumoPoints(fannFu: FannFu): TsumoPoint {
        val payments = arrayOf(4000, 6000, 8000, 12000, 16000)

        val childPayment = ceilPoint( fannFu.basePoint.toInt() * 8 )
        return TsumoPoint(0, min(childPayment, payments[fannFu.scoreGrade.ordinal]))

    }

    override fun receiveRonPoint(fannFu: FannFu): RonPoint {
        val payments = arrayOf(12000, 18000, 24000, 36000, 48000)

        val payment = ceilPoint(fannFu.basePoint.toInt() * 24)
        return RonPoint(min(payment, payments[fannFu.scoreGrade.ordinal]))
    }
}

class WinnerChild: Winner() {
    override fun receiveTsumoPoints(fannFu: FannFu): TsumoPoint {
        val childPayments = arrayOf(2000, 3000, 4000, 6000, 8000)
        val parentPayments = childPayments.map { it * 2 }.toIntArray()

        val parentPayment = ceilPoint( fannFu.basePoint.toInt() * 8 )
        val childPayment = ceilPoint(fannFu.basePoint.toInt() * 4)

        val grade = fannFu.scoreGrade.ordinal
        return TsumoPoint(min(parentPayment, parentPayments[grade]), min(childPayment, childPayments[grade]))
    }

    override fun receiveRonPoint(fannFu: FannFu): RonPoint {
        val payments = arrayOf(8000, 12000, 16000, 24000, 32000)

        val payment = ceilPoint(fannFu.basePoint.toInt() * 4)
        return RonPoint(min(payment, payments[fannFu.scoreGrade.ordinal]))
    }

}
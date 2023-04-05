package com.ishzk.android.majancalculator.domain

data class WaitHandData(
    val win: List<WinHand>,
)

data class WinHand(
    val tile: String,
    val error: String,
)
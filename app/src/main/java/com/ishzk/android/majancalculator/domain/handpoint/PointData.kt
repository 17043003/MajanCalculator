package com.ishzk.android.majancalculator.domain.handpoint

data class PointData(
    val cost: CostDetail,
    val han: Int,
    val fu: Int,
    val error: String,
    val is_open_hand: Boolean,
    val yaku: List<YakuDetail>,
    val fu_details: List<FuDetail>,
)

data class CostDetail(
    val main: Int,
    val main_bonus: Int,
    val additional: Int,
    val additional_bonus: Int,
    val total: Int,
    val yaku_level: String,
)

data class YakuDetail(
    val name: String,
    val han_open: Int,
    val han_closed: Int,
    val is_yakuman: Boolean,
)

data class FuDetail(
    val fu: Int,
    val reason: String,
)

data class ResultHandPoint(
    val fann: Int,
    val fu: Int,
    val total: String,
    val yakuDetail: List<YakuDetail>,
    val fuDetail: List<FuDetail>,
)
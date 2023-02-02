package com.ishzk.android.majancalculator.domain

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PointAPI {
    @GET("point/")
    fun getPoint(
        @Query("m") m: String,
        @Query("s") s: String,
        @Query("p") p: String,
        @Query("h") h: String,
        @Query("w_m") w_m: String,
        @Query("w_s") w_s: String,
        @Query("w_p") w_p: String,
        @Query("w_h") w_h: String,
        @Query("dora_m") dora_m: String,
        @Query("dora_s") dora_s: String,
        @Query("dora_p") dora_p: String,
        @Query("dora_h") dora_h: String,
        @Query("tsumo") tsumo: Boolean,
    ): Call<PointData>
}
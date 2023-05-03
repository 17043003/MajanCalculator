package com.ishzk.android.majancalculator.domain

import com.ishzk.android.majancalculator.domain.handpoint.PointData
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
        @Query("opens") opens: String,
        @Query("own_wind") ownWind: Int,
        @Query("field_wind") fieldWind: Int,
        @Query("tsumo") tsumo: Boolean,
        @Query("yakus") yakus: String,
    ): Call<PointData>

    @GET("/wait")
    fun getWaitHand(
        @Query("m") m: String,
        @Query("s") s: String,
        @Query("p") p: String,
        @Query("h") h: String,
        @Query("opens") open: String,
    ): Call<WaitHandData>
}

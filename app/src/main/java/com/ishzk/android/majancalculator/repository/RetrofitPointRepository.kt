package com.ishzk.android.majancalculator.repository

import com.google.gson.GsonBuilder
import com.ishzk.android.majancalculator.BuildConfig
import com.ishzk.android.majancalculator.domain.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitPointRepository: PointRepository {
    private var service: PointService
    private val baseUrl: String = BuildConfig.MAJAN_API_URL

    init {
        val gsonFactory = GsonConverterFactory.create(GsonBuilder().serializeNulls().create())

        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logger)

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(gsonFactory)
            .client(httpClient.build())
            .build()

        service = retrofit.create(PointService::class.java)
    }

    override suspend fun fetchPoint(request: PointRequestData): Flow<PointResponseData> = flow {
        val h = request.hands
        val w = request.win_tile
        val d = request.dora_indicator

        withContext(Dispatchers.IO) {
            val response = service.getPoint(
                h.man,
                h.pin,
                h.sou,
                h.honors,
                w.man,
                w.pin,
                w.sou,
                w.honors,
                d.man,
                d.pin,
                d.sou,
                d.honors,
                request.isTsumo
            )
                .execute()

            val body = response.body() ?: return@withContext
            val pointResponse = PointResponseData(
                body.cost.total,
                body.cost.yaku_level,
                body.han,
                body.fu,
                body.yaku,
            )
            emit(pointResponse)
        }
    }
}
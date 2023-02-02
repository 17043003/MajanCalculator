package com.ishzk.android.majancalculator.domain

import com.google.gson.GsonBuilder
import com.ishzk.android.majancalculator.BuildConfig
import com.ishzk.android.majancalculator.repository.RetrofitPointRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ActivityRetainedComponent::class)
object PointModule {

    @Provides
    fun pointAPI(): PointAPI {
        val gsonFactory = GsonConverterFactory.create(GsonBuilder().serializeNulls().create())

        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logger)

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.MAJAN_API_URL)
            .addConverterFactory(gsonFactory)
            .client(httpClient.build())
            .build()

        return retrofit.create(PointAPI::class.java)
    }
}


@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindRetrofitRepository(impl: RetrofitPointRepository): PointRepository
}
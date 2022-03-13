package com.thurainx.cointrack.di

import com.google.gson.Gson
import com.thurainx.cointrack.common.Constant
import com.thurainx.cointrack.data.remote.CoinApi
import com.thurainx.cointrack.data.repository.CoinRepositoryImpl
import com.thurainx.cointrack.domain.repository.CoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCoinApi() : CoinApi {
        return Retrofit.Builder()
            .baseUrl(Constant.BASED_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinApi::class.java)
    }

    @Singleton
    @Provides
    fun provideCoinRepository(api: CoinApi) : CoinRepository{
        return CoinRepositoryImpl(api = api)
    }
}
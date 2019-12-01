package io.github.bestandori.data.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create

object BestdoriServiceFactory {
    private val bestdoriService = Retrofit.Builder()
        .baseUrl("https://bestdori.com/api/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create<BestdoriService>()

    fun get() = bestdoriService
}
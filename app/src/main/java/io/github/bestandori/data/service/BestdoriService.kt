package io.github.bestandori.data.service

import io.github.bestandori.data.service.pojo.*
import retrofit2.Response
import retrofit2.http.*

interface BestdoriService {
    @GET("cards/all.5.json")
    suspend fun listCards(): Map<String, CardBean>

    @GET("characters/main.3.json")
    suspend fun listCharacters(): Map<String, CharacterBean>

    @POST("user/login")
    suspend fun login(@Header("cookie") cookie: String, @Body body: LoginRequestBody): Response<Result>

    @GET
    suspend fun homePage(@Url url: String): Response<String>

    @GET("user/me")
    suspend fun getMe(@Header("cookie") cookie: String): GetMeResult
}

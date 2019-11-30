package io.github.bestandori.data.service

import io.github.bestandori.data.service.pojo.CardBean
import io.github.bestandori.data.service.pojo.CharacterBean
import retrofit2.http.GET

interface BestdoriService {
    @GET("cards/all.5.json")
    suspend fun listCards(): Map<String, CardBean>

    @GET("characters/main.3.json")
    suspend fun listCharacters(): Map<String, CharacterBean>
}

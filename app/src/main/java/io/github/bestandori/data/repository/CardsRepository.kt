package io.github.bestandori.data.repository

import io.github.bestandori.data.service.BestdoriService
import io.github.bestandori.ui.page.cards.Card
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object CardsRepository {
    private val bestdoriService = Retrofit.Builder()
        .baseUrl("https://bestdori.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create<BestdoriService>()

    suspend fun getAllCards(): List<Card> {
        // TODO: Caching required!
        val characters = bestdoriService.listCharacters()
        val cards = bestdoriService.listCards()
        return cards.map { (_, cardBean) ->
            println("CardBean: ${cardBean.prefix[0]} ${(characters[cardBean.characterId.toString()] ?: error("Invalid characterId: ${cardBean.characterId}")).characterName[0]}")
            Card(
                cardBean.prefix[0] ?: "",
                (characters[cardBean.characterId.toString()] ?: error("Invalid characterId: ${cardBean.characterId}")).characterName[0]
            )
        }
    }
}

package io.github.bestandori.data.repository

import io.github.bestandori.data.service.BestdoriServiceFactory
import io.github.bestandori.ui.page.cards.Card

object CardsRepository {
    suspend fun getAllCards(): List<Card> {
        // TODO: Caching required!
        val characters = BestdoriServiceFactory.get().listCharacters()
        val cards = BestdoriServiceFactory.get().listCards()
        return cards.map { (_, cardBean) ->
            println("CardBean: ${cardBean.prefix[0]} ${(characters[cardBean.characterId.toString()] ?: error("Invalid characterId: ${cardBean.characterId}")).characterName[0]}")
            Card(
                cardBean.prefix[0] ?: "",
                (characters[cardBean.characterId.toString()] ?: error("Invalid characterId: ${cardBean.characterId}")).characterName[0]
            )
        }
    }
}

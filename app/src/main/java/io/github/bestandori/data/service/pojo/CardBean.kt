package io.github.bestandori.data.service.pojo

data class CardBean(
    val characterId: Int,
    // Some cards are server-only and don't have names for all prefixes
    val prefix: List<String?>
)
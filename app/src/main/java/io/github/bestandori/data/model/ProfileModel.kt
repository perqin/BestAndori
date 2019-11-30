package io.github.bestandori.data.model

data class ProfileModel(
    val id: Int,
    val name: String,
    val server: Server,
    val items: ItemsYouseiModel?,
    val cards: List<CardYouseiModel>? = null,
    private val cardsCountOnBasic: Int? = null
) {
    val cardsCount = cards?.size ?: cardsCountOnBasic ?: 0

    fun serialize(): String {
        return "TODO"
    }

    companion object {
        fun fromSerialized(serialized: String, basic: Boolean = false): ProfileModel {
            return ProfileModel(
                0, "Untitled", Server.JP,
                if (basic) null else ItemsYouseiModel(0),
                if (basic) null else emptyList(),
                if (basic) 233 else null
            )
        }
    }
}

enum class Server {
    JP, EN, TW, CN, KR
}

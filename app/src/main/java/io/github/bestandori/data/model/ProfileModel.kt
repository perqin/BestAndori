package io.github.bestandori.data.model

import io.github.bestandori.data.service.pojo.ItemsBean
import io.github.bestandori.data.service.pojo.ProfileBean
import io.github.bestandori.util.gson

data class ProfileModel(
    val id: Int,
    val name: String,
    val server: Server,
    val items: ItemsYouseiModel?,
    val cards: List<CardYouseiModel>? = null,
    private val cardsCountOnBasic: Int? = null
) {
    val cardsCount = cards?.size ?: cardsCountOnBasic ?: 0
    private val serverInt = when (server) {
        Server.JP -> 0
        Server.EN -> 1
        Server.TW -> 2
        Server.CN -> 3
        Server.KR -> 4
    }

    fun serialize(): String {
        return gson.toJson(ProfileBean(
            name,
            serverInt,
            "1",
            (cards ?: emptyList()).joinToString { it.compress() },
            getItemsBean()
        ))
    }

    private fun getItemsBean(): ItemsBean {
        // TODO: Create from items
        return ItemsBean(
            // PoppinParty
            intArrayOf(4, 4, 4, 4, 4, 4, 4),
            //Afterglow
            intArrayOf(4, 4, 4, 4, 4, 4, 4),
            //PastelPalettes
            intArrayOf(4, 4, 4, 4, 4, 4, 4),
            // Roselia
            intArrayOf(4, 4, 4, 4, 4, 4, 4),
            // HelloHappyWorld
            intArrayOf(4, 4, 4, 4, 4, 4, 4),
            // Everyone
            intArrayOf(4, 4, 4, 4, 4, 4, 4),
            // Magazine
            intArrayOf(4, 4, 4),
            // Plaza
            intArrayOf(4, 4, 4, 4),
            // Menu
            intArrayOf(4, 4, 4, 4)
        )
    }

    companion object {
        fun fromSerialized(id: Int, serialized: String, basic: Boolean = false): ProfileModel {
            val profileBean = gson.fromJson(serialized, ProfileBean::class.java)
            return ProfileModel(
                id, profileBean.name, serverFromInt(profileBean.server),
                if (basic) null else ItemsYouseiModel(0),
                if (basic) null else (0 until (profileBean.data.length / 5)).map { CardYouseiModel.fromCompressed(profileBean.data.substring(it until (it + 5))) },
                if (basic) profileBean.data.length / 5 else null
            )
        }

        private fun serverFromInt(int: Int) = when (int) {
            0 -> Server.JP
            1 -> Server.EN
            2 -> Server.TW
            3 -> Server.CN
            4 -> Server.KR
            else -> throw IllegalArgumentException("Invalid int $int")
        }
    }
}

enum class Server {
    JP, EN, TW, CN, KR
}

fun Server.toAssetFilename() = when (this) {
    Server.JP -> "ic_jp.svg"
    Server.EN -> "ic_en.svg"
    Server.TW -> "ic_tw.svg"
    Server.CN -> "ic_cn.svg"
    Server.KR -> "ic_kr.svg"
}

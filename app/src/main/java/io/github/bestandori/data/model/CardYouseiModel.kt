package io.github.bestandori.data.model

import androidx.annotation.IntRange

private const val ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-_"

data class CardYouseiModel(
    @IntRange(from = 1, to = 60)
    val level: Int,
    @IntRange(from = 0, to = 4)
    val skill: Int,
    @IntRange(from = 0, to = 2)
    val story: Int,
    val training: Boolean,
    val trainingArt: Boolean
) {
    fun compress(): String {
        val number = skill * 24 + story * 8 + (if (training) 4 else 0) + (if (trainingArt) 2 else 0)
        return "${ALPHABET[number / 64]}${ALPHABET[number % 64]}"
    }

    companion object {
        fun fromCompressed(compressed: String): CardYouseiModel {
            val level = ALPHABET.indexOf(compressed[0])
            val number = ALPHABET.indexOf(compressed[1]) * 64 + ALPHABET.indexOf(compressed[2])
            return CardYouseiModel(level, number / 24, number / 8, number / 4 == 1, number / 2 == 1)
        }
    }
}

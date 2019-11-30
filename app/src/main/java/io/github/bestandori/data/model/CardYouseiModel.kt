package io.github.bestandori.data.model

import androidx.annotation.IntRange

private const val ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-_"

private fun intToChar(int: Int, length: Int): String {
    var left = int
    var ret = ""
    while (left >= ALPHABET.length) {
        ret = "${left % ALPHABET.length}$ret"
        left /= ALPHABET.length
    }
    ret = "$left$ret"
    if (ret.length > length) {
        throw RuntimeException("Fail to convert from $int to $ret with length $length")
    }
    if (ret.length < length) {
        ret.padStart(length - ret.length, '0')
    }
    return ret
}

private fun charToInt(char: String): Int {
    var ret = 0
    for (c in char) {
        ret = ret * ALPHABET.length + ALPHABET.indexOf(c)
    }
    return ret
}

data class CardYouseiModel(
    val cardId: Int,
    val card: CardModel? = null,
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
        return "${intToChar(cardId, 2)}${intToChar(number, 3)}"
    }

    companion object {
        fun fromCompressed(compressed: String): CardYouseiModel {
            val cardId = charToInt(compressed.substring(0..1))
            val level = charToInt(compressed.substring(2..2))
            val number = charToInt(compressed.substring(3..4))
            return CardYouseiModel(cardId, null, level, number / 24, number / 8, number / 4 == 1, number / 2 == 1)
        }
    }
}

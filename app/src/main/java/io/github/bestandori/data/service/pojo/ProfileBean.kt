package io.github.bestandori.data.service.pojo

data class ProfileBean(
    val name: String,
    val server: Int,
    val compression: String,
    val data: String,
    val items: ItemsBean
)

data class ItemsBean(
    val PoppinParty: IntArray,
    val Afterglow: IntArray,
    val PastelPalettes: IntArray,
    val Roselia: IntArray,
    val HelloHappyWorld: IntArray,
    val Everyone: IntArray,
    val Magazine: IntArray,
    val Plaza: IntArray,
    val Menu: IntArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ItemsBean

        if (!Menu.contentEquals(other.Menu)) return false
        if (!Plaza.contentEquals(other.Plaza)) return false
        if (!Roselia.contentEquals(other.Roselia)) return false
        if (!Everyone.contentEquals(other.Everyone)) return false
        if (!Magazine.contentEquals(other.Magazine)) return false
        if (!Afterglow.contentEquals(other.Afterglow)) return false
        if (!PoppinParty.contentEquals(other.PoppinParty)) return false
        if (!PastelPalettes.contentEquals(other.PastelPalettes)) return false
        if (!HelloHappyWorld.contentEquals(other.HelloHappyWorld)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = Menu.contentHashCode()
        result = 31 * result + Plaza.contentHashCode()
        result = 31 * result + Roselia.contentHashCode()
        result = 31 * result + Everyone.contentHashCode()
        result = 31 * result + Magazine.contentHashCode()
        result = 31 * result + Afterglow.contentHashCode()
        result = 31 * result + PoppinParty.contentHashCode()
        result = 31 * result + PastelPalettes.contentHashCode()
        result = 31 * result + HelloHappyWorld.contentHashCode()
        return result
    }
}

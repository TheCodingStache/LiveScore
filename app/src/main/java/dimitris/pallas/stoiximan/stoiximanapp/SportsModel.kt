package dimitris.pallas.stoiximan.stoiximanapp

import com.google.gson.annotations.SerializedName

data class SportsModel(
    @SerializedName("e")
    val e: List<Events>,
    @SerializedName("d")
    val d: String,
    var visibility: Boolean = false
) {
    data class Events(
        @SerializedName("sh")
        val sh: String,
        @SerializedName("tt")
        val tt: Long
    ) {
    }

}


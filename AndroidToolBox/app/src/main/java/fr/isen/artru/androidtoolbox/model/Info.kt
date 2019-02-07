package fr.isen.artru.androidtoolbox.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Awesome Pojo Generator
 */
class Info {
    @SerializedName("seed")
    @Expose
    var seed: String? = null
    @SerializedName("page")
    @Expose
    var page: Int? = null
    @SerializedName("results")
    @Expose
    var results: Int? = null
    @SerializedName("version")
    @Expose
    var version: Double? = null
}
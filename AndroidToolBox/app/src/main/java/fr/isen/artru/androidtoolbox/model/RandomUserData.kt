package fr.isen.artru.androidtoolbox.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Awesome Pojo Generator
 */
class RandomUserData {
    @SerializedName("results")
    @Expose
    var results: List<Results>? = null
    @SerializedName("info")
    @Expose
    var info: Info? = null
}
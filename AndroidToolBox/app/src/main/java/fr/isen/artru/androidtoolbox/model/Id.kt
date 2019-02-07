package fr.isen.artru.androidtoolbox.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Awesome Pojo Generator
 */
class Id {
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("value")
    @Expose
    var value: Any? = null
}
package fr.isen.artru.androidtoolbox.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


/**
 * Awesome Pojo Generator
 */
class Location {
    @SerializedName("city")
    @Expose
    var city: String? = null
    @SerializedName("street")
    @Expose
    var street: String? = null
    @SerializedName("timezone")
    @Expose
    var timezone: Timezone? = null
    @SerializedName("postcode")
    @Expose
    var postcode: String? = null
    @SerializedName("coordinates")
    @Expose
    var coordinates: Coordinates? = null
    @SerializedName("state")
    @Expose
    var state: String? = null
    val adress: String
        get() {
            val format = String.format("%s,\n%s %s", street, postcode, city)
            return format
        }
}
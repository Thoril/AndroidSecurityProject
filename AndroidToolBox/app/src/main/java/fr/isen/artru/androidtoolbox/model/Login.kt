package fr.isen.artru.androidtoolbox.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Awesome Pojo Generator
 */
class Login {
    @SerializedName("sha1")
    @Expose
    var sha1: String? = null
    @SerializedName("password")
    @Expose
    var password: String? = null
    @SerializedName("salt")
    @Expose
    var salt: String? = null
    @SerializedName("sha256")
    @Expose
    var sha256: String? = null
    @SerializedName("uuid")
    @Expose
    var uuid: String? = null
    @SerializedName("username")
    @Expose
    var username: String? = null
    @SerializedName("md5")
    @Expose
    var md5: String? = null
}
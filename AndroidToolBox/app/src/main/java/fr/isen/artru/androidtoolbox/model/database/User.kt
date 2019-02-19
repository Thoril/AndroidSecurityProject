package fr.isen.artru.androidtoolbox.model.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class User(
    @ColumnInfo(name = "first_name") var firstName: String?,
    @ColumnInfo(name = "last_name") var lastName: String?,
    @ColumnInfo(name = "password") var password: String?
){
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}
package fr.isen.artru.androidtoolbox

import java.text.SimpleDateFormat
import java.util.*

class User{
    var userNom: String? = null
    var userPrenom: String? = null
    var userBirthDate: String? = null


    constructor() : super() {}

    constructor(userNom: String, userPrenom: String, userBirthDate: String) : super() {
        this.userNom = userNom
        this.userPrenom = userPrenom
        this.userBirthDate = userBirthDate
    }
    fun getAge():String{
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val date = sdf.parse(this.userBirthDate)
        val cal = Calendar.getInstance()
        cal.time = date
        val maintenant = GregorianCalendar()
        var age = maintenant.get(Calendar.YEAR) - cal.get(Calendar.YEAR)
        if (maintenant.get(Calendar.DAY_OF_YEAR) < cal.get(Calendar.DAY_OF_YEAR)){
            age--
        }
        return age.toString()
    }
}
package fr.isen.artru.androidtoolbox

import android.app.DatePickerDialog
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_sauvegarde.*
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class SauvegardeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sauvegarde)
        //naissance.calendarViewShown = false

        val cal = Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val formater = SimpleDateFormat("dd/MM/yyyy",Locale.FRENCH)
            calendrier.text = formater.format(cal.time)
        }
        calendrier.setOnClickListener{
            showDatePicker(dateSetListener,cal)
            //Toast.makeText(this, "COUCOU", Toast.LENGTH_SHORT).show()
        }

        val tempFile = this.getFile(this,"RandomUserData")

        saveData.setOnClickListener{
            //val date = GregorianCalendar(naissance.year, naissance.month, naissance.dayOfMonth)
            //Log.d("Sauvegarde",date.toString())
            //val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            val user = User(nom.text.toString(),prenom.text.toString(),calendrier.text.toString())

            if(user.userPrenom != "" && user.userPrenom !="" && user.userBirthDate != "") {
                val gson = Gson()
                val jsonString:String=gson.toJson(user)
                tempFile?.writeText(jsonString)
                val stringBuilder = StringBuilder("")
                stringBuilder.append("Nom: " + user.userNom + "\nPrénom: " + user.userPrenom)
                stringBuilder.append("\nDate de naissance: " + user.userBirthDate)
                Log.d("Sauvegarde", "Age: " + user.getAge())
                Toast.makeText(this, "Données Sauvegardées", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"Remplissez tous les champs", Toast.LENGTH_SHORT).show()
            }

        }
        displayInfo.setOnClickListener{
            val gson = Gson()
            //Read the PostJSON.json file
          try{
                val bufferedReader: BufferedReader = tempFile?.bufferedReader()!!
                // Read the text from buffferReader and store in String variable
                val inputString = bufferedReader.use { it.readText() }
                //Convert the Json File to Gson Object
                val user = gson.fromJson(inputString, User::class.java)
                //Initialize the String Builder
                val stringBuilder = StringBuilder("")
                if (user != null) {
                    stringBuilder.append("Nom: " + user.userNom + "\nPrénom: " + user.userPrenom)
                    stringBuilder.append("\nDate de naissance: " + user.userBirthDate)
                    stringBuilder.append("\nAge: " + user.getAge())
                }
                Log.d("Sauvegarde", stringBuilder.toString())
                Log.d("Sauvegarde", stringBuilder.toString())
                val builder: AlertDialog.Builder? = this.let {
                    AlertDialog.Builder(it)
                }
                builder?.setMessage(stringBuilder.toString())?.setTitle("Vos informations")
                val dialog: AlertDialog? = builder?.create()
                dialog!!.show()
                //Toast.makeText(this, stringBuilder.toString(), Toast.LENGTH_SHORT).show()
            }
          catch (e: IOException){
              Toast.makeText(this, "Sauvegarder d'abord vos données", Toast.LENGTH_SHORT).show()
          }
        }
    }


    private fun showDatePicker(dateSetListener: DatePickerDialog.OnDateSetListener, cal:Calendar){
        DatePickerDialog(
            this@SauvegardeActivity,
            dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun getFile(context: Context, url: String): File? =
                 File( context.filesDir, url)


}


package fr.isen.artru.androidtoolbox

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val sharedPref = getSharedPreferences("LOGIN",Context.MODE_PRIVATE)
        val intent = Intent(this, HomeActivity::class.java)
        if(sharedPref.getString("identifiant","")=="admin" && sharedPref.getString("password","")=="123"){
            startActivity(intent)
        }
        button_valider.setOnClickListener {
            if (input_identifiant.text.toString() == "") {
                Toast.makeText(this, "Veuillez saisir votre identifiant", Toast.LENGTH_SHORT).show()
            }else if (input_password.text.toString() == ""){
                Toast.makeText(this, "Veuillez saisir votre mot de passe", Toast.LENGTH_SHORT).show()
            }else if (input_password.text.toString() == "123" && input_identifiant.text.toString() == "admin"){
                val editor = sharedPref.edit()
                editor.putString("identifiant", input_identifiant.text.toString())
                editor.putString("password", input_password.text.toString())
                editor.apply()
                startActivity(intent)
            }else {
                Toast.makeText(this, "Couple identifiant mot de passe incorrecte", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

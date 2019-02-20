package fr.isen.artru.androidtoolbox

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import java.security.Key

class LoginActivity : AppCompatActivity() {

    private lateinit var sharedPref  : SharedPreferences
    val secretKey = CipherWrapper.getKey("keyLogin",true)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sharedPref = getSharedPreferences("LOGIN",Context.MODE_PRIVATE)
        val intent = Intent(this, HomeActivity::class.java)
        if(this.getEncryptedInShared("identifiant",secretKey)=="admin" && this.getEncryptedInShared("password",secretKey)=="123"){
            startActivity(intent)
        }
        button_valider.setOnClickListener {
            if (input_identifiant.text.toString() == "") {
                Toast.makeText(this, "Veuillez saisir votre identifiant", Toast.LENGTH_SHORT).show()
            }else if (input_password.text.toString() == ""){
                Toast.makeText(this, "Veuillez saisir votre mot de passe", Toast.LENGTH_SHORT).show()
            }else if (input_password.text.toString() == "123" && input_identifiant.text.toString() == "admin"){
                this.storeEncryptedInShared("identifiant",input_identifiant.text.toString(),secretKey)
                this.storeEncryptedInShared("password", input_password.text.toString(),secretKey)
                                startActivity(intent)
            }else {
                Toast.makeText(this, "Couple identifiant mot de passe incorrecte", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun storeEncryptedInShared(key:String,value: String, encryptionKey : Key?)
    {
        val editor = sharedPref.edit()
        editor.putString(key,CipherWrapper().encrypt(value,encryptionKey,true))
        editor.apply()
    }

    fun getEncryptedInShared(key : String,encryptionKey: Key?):String{
        val value =sharedPref.getString(key,"")
        if(value=="")
        {
            return ""
        }
        return CipherWrapper().decrypt(value,encryptionKey,true)
    }

}

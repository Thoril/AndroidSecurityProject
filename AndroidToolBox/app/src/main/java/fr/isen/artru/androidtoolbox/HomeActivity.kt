package fr.isen.artru.androidtoolbox

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        imageCycleDeVie.setOnClickListener{
            startActivity(Intent(this, CycleDeVieActivity::class.java))
        }
        deconnexion.setOnClickListener{
            val sharedPref = getSharedPreferences("LOGIN", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString("identifiant", "")
            editor.putString("password", "")
            editor.apply()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        imageSauvegarde.setOnClickListener{
            startActivity(Intent(this, SauvegardeActivity::class.java))
        }

        imagePermissions.setOnClickListener{
            startActivity(Intent(this, PermissionActivity::class.java))
        }

        imageService.setOnClickListener{
            startActivity(Intent(this,CaPinningActivity::class.java))
        }

        imagePagination.setOnClickListener{
            startActivity(Intent(this,PaginationActivity::class.java))
        }
    }

}

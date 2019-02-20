package fr.isen.artru.androidtoolbox

import android.arch.persistence.room.Room
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import fr.isen.artru.androidtoolbox.model.database.AppDatabase
import fr.isen.artru.androidtoolbox.model.database.User
import kotlinx.android.synthetic.main.activity_sauvegarde.*


class SauvegardeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sauvegarde)

        val secretKey = CipherWrapper.getKey("myKey",true)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()

        insert.setOnClickListener{
            val cypherWrapper = CipherWrapper()
            resources.getIdentifier(firstName.text.toString(), lastName.text.toString(), this.packageName)
            val user = User(firstName.text.toString(), lastName.text.toString(), cypherWrapper.encrypt(password.text.toString(),secretKey, true))
            db.userDao().insertAll(user)
            Toast.makeText(this,"Informations Enregistrées", Toast.LENGTH_SHORT).show()
        }

        display.setOnClickListener {
            startActivity(Intent(this, UserActivity::class.java))
        }

        deleteAll.setOnClickListener {
            db.userDao().deleteAll()
            Toast.makeText(this,"Informations Supprimées", Toast.LENGTH_SHORT).show()
        }
    }
}


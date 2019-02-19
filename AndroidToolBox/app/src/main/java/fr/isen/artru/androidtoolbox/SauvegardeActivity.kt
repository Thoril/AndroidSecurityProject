package fr.isen.artru.androidtoolbox

import android.arch.persistence.room.Room
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.support.annotation.RequiresApi
import fr.isen.artru.androidtoolbox.model.database.AppDatabase
import kotlinx.android.synthetic.main.activity_sauvegarde.*
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator


class SauvegardeActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sauvegarde)

        val keyStore = KeyStore.getInstance("AndroidKeyStore")
        keyStore.load(null)
        val alias = "myKey"

        if (!keyStore.containsAlias(alias)) {

            val keyGenerator =KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES,"AndroidKeyStore")
            val keyGenParameterSpec = KeyGenParameterSpec.Builder(
                alias,
               KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .build()
            keyGenerator.init(keyGenParameterSpec)
            keyGenerator.generateKey()
        }

        val secretKeyEntry =  keyStore.getEntry(alias, null) as KeyStore.SecretKeyEntry
        val secretKey = secretKeyEntry.secretKey

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()

        insert.setOnClickListener{
            val cipherEn = Cipher.getInstance("AES/GCM/NoPadding")
            val cypherWrapper : CipherWrapper = CipherWrapper()
            val user = User(firstName.text.toString(),lastName.text.toString(), cypherWrapper.encrypt(password.text.toString(),secretKey, true))

        }

        display.setOnClickListener {
        }
    }
}


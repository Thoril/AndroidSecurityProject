package fr.isen.artru.androidtoolbox

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.support.annotation.RequiresApi
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import fr.isen.artru.androidtoolbox.model.database.User
import java.security.KeyStore
import javax.crypto.KeyGenerator


class UserAdapter(private val UserList: List<User>) : RecyclerView.Adapter<UserAdapter.MyViewHolder>() {

    @RequiresApi(Build.VERSION_CODES.M)
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var firstName: TextView
        var lastName: TextView
        var password: TextView
        var uncrypt: Button

        init {
            firstName = view.findViewById(R.id.firstName)
            lastName = view.findViewById(R.id.lastName)
            password = view.findViewById(R.id.password)
            uncrypt = view.findViewById(R.id.uncrypt)
            uncrypt.setOnClickListener {
                val keyStore = KeyStore.getInstance("AndroidKeyStore")
                keyStore.load(null)
                val alias = "myKey"

                if (!keyStore.containsAlias(alias)) {

                    val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES,"AndroidKeyStore")
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
                val cypherWrapper = CipherWrapper()
                password.text= cypherWrapper.decrypt(password.text.toString(),secretKey,true)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_adapter, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = UserList[position]
        holder.firstName.text = user.firstName
        holder.lastName.text = user.lastName
        holder.password.text = user.password
    }

    override fun getItemCount(): Int {
        return UserList.size
    }
}
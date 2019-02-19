package fr.isen.artru.androidtoolbox

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.android.gms.auth.api.credentials.*
import com.google.android.gms.tasks.OnCompleteListener
import kotlinx.android.synthetic.main.activity_logged.*


class LoggedActivity : AppCompatActivity() {

    private val TAG = LoggedActivity::class.java.canonicalName
    private lateinit var mCredentialsClient: CredentialsClient
    private lateinit var cred : Credential

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logged)
        mCredentialsClient = Credentials.getClient(this)


        val mCredentialRequest = CredentialRequest.Builder()
            .setPasswordLoginSupported(true)
            .build()
        mCredentialsClient.request(mCredentialRequest).addOnCompleteListener(
            OnCompleteListener<CredentialRequestResponse> { task ->
                if (task.isSuccessful) {
                    // See "Handle successful credential requests"
                    onCredentialRetrieved(task.result!!.credential)
                    return@OnCompleteListener
                }
            })

        DisconnectSmartLock.setOnClickListener {
            mCredentialsClient.delete(this.cred).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this,SharedprefActivity::class.java))
                }
            }

        }
    }

    private fun onCredentialRetrieved(credential: Credential) {
        val accountType = credential.accountType
        this.cred= credential
        Log.i(TAG, "AccountType : " + accountType.toString())
        Log.i(TAG, "id : $"+credential.id+"  pass : "+credential.password)
        showId.text=credential.id
        showPass.text=credential.password
    }
}

package fr.isen.artru.androidtoolbox

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.auth.api.credentials.*
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_sharedpref.*


/**
 * A login screen that offers login via email/password.
 */
class SharedprefActivity : AppCompatActivity() {

    private val TAG = SharedprefActivity::class.java.canonicalName
    private val url = "https://reqres.in/api/login"
    private lateinit var mCredentialsClient: CredentialsClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sharedpref)
        mCredentialsClient = Credentials.getClient(this)
        Log.i(TAG, "debut de l'activité")

        // Récuperation des credential stocké dans smart lock
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

        email_sign_in_button.setOnClickListener {
            this.sendAuthent(email.text.toString(), password.text.toString())
        }
    }

    fun sendAuthent(email: String, pass: String) {
        val queue = Volley.newRequestQueue(this)

        val stringRequest = object : StringRequest(
            Request.Method.POST, this.url,
            Response.Listener<String> { response ->
                // Display the first 500 characters of the response string.
                Log.i(TAG, response)
                val resp: Map<String, Any> = Gson().fromJson(response, object : TypeToken<Map<String, Any>>() {}.type)
                Log.i(TAG, resp["token"]?.toString())
                this.store(email, pass)
            },
            Response.ErrorListener { Log.i(TAG, "Erreur dans la requete : " + it.message) }) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["email"] = email
                params["password"] = pass
                return params
            }
        }
        queue.add(stringRequest)
    }

    fun store(email: String, pass: String) {
        val credential: Credential = Credential.Builder(email).setPassword(pass).build()
        mCredentialsClient.save(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d(TAG, "SAVE: OK")
                Toast.makeText(this, "Credentials saved", Toast.LENGTH_SHORT).show()
                return@addOnCompleteListener
            }
            val e = it.exception
            if (e is ResolvableApiException) {
                val rae = e
                try {
                    rae.startResolutionForResult(this, 100)
                } catch (e: IntentSender.SendIntentException) {
                    Log.e(TAG, "Failed to send resolution.", e)
                    Toast.makeText(this, "Save failed", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Save failed", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun onCredentialRetrieved(credential: Credential) {
        val accountType = credential.accountType
        Log.i(TAG, "AccountType : " + accountType.toString())
        signInWithPassword(credential.id, credential.password)

    }

    fun signInWithPassword(id: String?, pass: String?) {
        Log.i(TAG, "id : $id pass : $pass")
        val intent = Intent(this, LoggedActivity::class.java)
        startActivity(intent)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                Log.d(TAG, "SAVE: OK")
                Toast.makeText(this, "Credentials saved", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoggedActivity::class.java)
                startActivity(intent)
            } else {
                Log.e(TAG, "SAVE: Canceled by user")
            }
        }
    }
}

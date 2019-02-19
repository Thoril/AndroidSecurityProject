package fr.isen.artru.androidtoolbox

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.AsyncTask.execute
import android.util.Log
import android.widget.Toast
import okhttp3.*
import java.io.IOException
import java.net.URL


class CertificatePinnerActivity : AppCompatActivity() {

    private var exception: Exception? = null
    private var myToast:String ?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certificate_pinner)

        /**
         * Faire un deuxième exemple quand le hash ne correspond pas
         */

        val certificatePinner = CertificatePinner.Builder()
            .add(
                "www.isen-mediterranee.fr",
                //"sha256/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA="
                "sha256/hFXPJO0krtKIdyQtElkuqRoAbJLup/jRKQs0DcVZ9Ac="
            )
            .add(
                "www.isen-mediterranee.fr",
                //"sha256/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA="
                "sha256/13db7a5f84f997bdb79dad36654f48a9753a46a710abc67303d12c13e41c2902"
            )
            .build()

        /**
         * Le OkHttpClient check le certificat via le fingerprint, si le hash ne correspond pas une exception apparait
         */
        val okHttpClient = OkHttpClient.Builder()
            .certificatePinner(certificatePinner)
            .build()

        val request = Request.Builder()
            .url("https://www.isen-mediterranee.fr")
            .build()


        val response = okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                Log.d("OkHttpTest", "Response code : " + response.code())
                if (response.isSuccessful()) {
                    Log.e("CA MARCHE", response.toString())
                    val url = URL("https://www.isen-mediterranee.fr/")
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url.toString())))
                    //val toast = Toast.makeText(this,"OKKKKKK", Toast.LENGTH_LONG)
                    myToast = "ok"
                }
            }

            override fun onFailure(call: Call, exception: IOException) {
                Log.e("Erreur", exception.toString())
                myToast="pas ok"

            }
        })

        if(myToast!=null){
            Toast.makeText(this,myToast, Toast.LENGTH_LONG).show()
        }

       /* if(response.equals(true)){
            Toast.makeText(this,"OKKKKKK", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this,"PAS OK", Toast.LENGTH_LONG).show()
        }*/

    }

}

package fr.isen.artru.androidtoolbox

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import fr.isen.artru.androidtoolbox.model.RequestTask
import kotlinx.android.synthetic.main.activity_certificat.*


class CertificatActivity : AppCompatActivity() {

    private var TAG : String = CertificatActivity::class.java.toString()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certificat)
        Log.i(TAG, "OK")

        authSecure.setOnClickListener {
            Log.i(TAG,"****** Certificat OK ********")
            RequestTask().execute("https://google.com")
            certificate.text = RequestTask().execute("https://google.com").get()
        }
        authUnsecure.setOnClickListener {
            val myToast:String = RequestTask().execute("https://expired.badssl.com/").get()
            Toast.makeText(this, myToast, Toast.LENGTH_LONG).show()
        }
        wrongHost.setOnClickListener {
            val myToast:String = RequestTask().execute("https://wrong.host.badssl.com/").get()
            Toast.makeText(this, myToast, Toast.LENGTH_LONG).show()
        }
        selfSigned.setOnClickListener {
            val myToast:String = RequestTask().execute("https://self-signed.badssl.com/").get()
            Toast.makeText(this, myToast, Toast.LENGTH_LONG).show()
        }
        untrustedRoot.setOnClickListener {
            val myToast:String = RequestTask().execute("https://untrusted-root.badssl.com/").get()
            Toast.makeText(this, myToast, Toast.LENGTH_LONG).show()
        }
        revoked.setOnClickListener {
            val myToast:String = RequestTask().execute("https://revoked.badssl.com/").get()
            Toast.makeText(this, myToast, Toast.LENGTH_LONG).show()
        }
        pinningTest.setOnClickListener {
            val myToast:String = RequestTask().execute("https://pinning-test.badssl.com/").get()
            Toast.makeText(this, myToast, Toast.LENGTH_LONG).show()
        }
        shaOne.setOnClickListener {
            val myToast:String = RequestTask().execute("https://sha1-intermediate.badssl.com/").get()
            Toast.makeText(this, myToast, Toast.LENGTH_LONG).show()
        }
        sct.setOnClickListener {
            val myToast:String = RequestTask().execute("https://invalid-expected-sct.badssl.com/").get()
            Toast.makeText(this, myToast, Toast.LENGTH_LONG).show()
        }
        sslVersion.setOnClickListener {
            val myToast:String = RequestTask().execute("https://rc4-md5.badssl.com/").get()
            Toast.makeText(this, myToast, Toast.LENGTH_LONG).show()
        }
    }


}



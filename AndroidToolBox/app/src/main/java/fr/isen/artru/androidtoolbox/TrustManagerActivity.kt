package fr.isen.artru.androidtoolbox

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import okhttp3.OkHttpClient
import java.io.*
import java.lang.Exception
import java.lang.System.load
import java.net.URL
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate

import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLPeerUnverifiedException
import javax.net.ssl.TrustManagerFactory

class TrustManagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trust_manager)

        val sslContext = SslUtils.getSslContextForCertificateFile(this, "badssl_cert.cer")
        val url = URL("https://pinning-test.badssl.com/")
        val urlConnection = url.openConnection() as HttpsURLConnection
        urlConnection.sslSocketFactory = sslContext.socketFactory

        //Toast.makeText(this, "Connection performed : " + sslContext.socketFactory, Toast.LENGTH_LONG).show()


       /* try {
            val cf: CertificateFactory = CertificateFactory.getInstance("X.509")
            //Load KeyStore with the Certificate file from resources as InputStream
            val caInput: InputStream = BufferedInputStream(FileInputStream("./main/res/raw/badssl_cert.cer"))
            val ca: X509Certificate = caInput.use {
                cf.generateCertificate(it) as X509Certificate
            }

            System.out.println("ca=" + ca.subjectDN)

            val keyStoreType = KeyStore.getDefaultType()
            val keyStore = KeyStore.getInstance(keyStoreType).apply {
                load(null, null)
                setCertificateEntry("ca", ca)
            }
            //keyStore.load(resourceStream, null)

            //Get TrustManagerFactory and init it with KeyStore
            val trustManagerAlgorithm = TrustManagerFactory.getDefaultAlgorithm()
            val trustManagerFactory = TrustManagerFactory.getInstance(trustManagerAlgorithm)
            trustManagerFactory.init(keyStore)

            /**
             * Get an instance of SSLContext
             * bind it with TrustManager
             * Create an sslContext with a URL connection
             */

            var sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, trustManagerFactory.trustManagers, null)
            val url = URL("https://pinning-test.badssl.com/")
            val urlConnection = url.openConnection() as HttpsURLConnection
            urlConnection.sslSocketFactory = sslContext.socketFactory

            /*val inputStream: InputStream = urlConnection.inputStream
            copyInputStreamToOutputStream(inputStream, System.out)*/

            Toast.makeText(this, sslContext.socketFactory.toString(), Toast.LENGTH_LONG).show()


            //Lancement de la page web
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url.toString())))
        }
        catch(e:Exception){
            Toast.makeText(this, "Exception" + e.toString(), Toast.LENGTH_LONG).show()
            Log.e("Exception", e.toString())
        }*/

    }

}

package fr.isen.artru.androidtoolbox

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import java.lang.Exception
import java.net.URL

import javax.net.ssl.HttpsURLConnection


class TrustManagerActivity : AppCompatActivity() {


  /*  override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trust_manager)

        val sslContext = SslUtils.getSslContextForCertificateFile(this, "badssl_cert.cer")
        val url = URL("https://pinning-test.badssl.com/")
        val urlConnection = url.openConnection() as HttpsURLConnection
        urlConnection.sslSocketFactory = sslContext.socketFactory

        val resu = RequestTask().execute()

        if (resu.equals(true)){
            Toast.makeText(this, "Ca marche", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Ca marche pas", Toast.LENGTH_LONG).show()
        }




        //Toast.makeText(this, "request: $urlConnection \nResponse Code: ${urlConnection.responseCode} \nMessage: ${urlConnection.responseMessage}", Toast.LENGTH_LONG).show()


         try {
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
        }

    }
    */
}
/*
 class RequestTask : AsyncTask<URL, Void, Boolean>() {

    private var exception: Exception? = null

    override fun doInBackground(vararg urls: URL):Boolean {
        return try {

            val sslContext = SslUtils.getSslContextForCertificateFile(this, "badssl_cert.cer")
            val url = URL("https://pinning-test.badssl.com/")
            val urlConnection = url.openConnection() as HttpsURLConnection
            urlConnection.sslSocketFactory = sslContext.socketFactory


            true
        } catch (e: Exception) {
            this.exception = e

            false
        }
    }
}
*/
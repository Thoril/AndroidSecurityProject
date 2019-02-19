package fr.isen.artru.androidtoolbox.model

import android.os.AsyncTask
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.net.MalformedURLException
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLPeerUnverifiedException


internal class RequestTask : AsyncTask<String, Void, String>() {

    private var TAG : String = RequestTask::class.java.toString()
    private var exception: Exception? = null
    private var resu : String ?=null

    override fun doInBackground(vararg urls: String):String {
        try {
            /*
            //val url = URL("https://wikipedia.org")
            Log.i("On doInBackground", "URL : "+ urls[0])
            val urlConnection: URLConnection = urls[0].openConnection()

            Log.i("On doInBackground", "URLconnection : $urlConnection")
            val inputStream: InputStream = urlConnection.getInputStream()
            val chaine = inputStream.bufferedReader().use(BufferedReader::readText)
            Log.i("On doInBackground", "inputStream :$chaine")
            */

            testIt(urls[0])
            return this.resu!!
        } catch (e: Exception) {
            this.exception = e

            return "Errors"
        }
    }

    private fun testIt(https_url: String){

        val url: URL
        try {

            url = URL(https_url)
            val con = url.openConnection() as HttpsURLConnection
            /*
            val certificat = con.serverCertificates
            for(i in certificat){
                if (i.encoded.(owner)) {
                    LOG.error("certificate revoked");
                    throw new CertificateException("certificate revoked");
                }
            }*/

            //dumpl all cert info
            printHttpsCert(con)

        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun printHttpsCert(con: HttpsURLConnection?) {
        if (con != null) {
            try {
                resu="Response Code : " + con.responseCode
                Log.i(TAG,"Response Code : " + con.responseCode)
                resu +="\nCipher Suite : " + con.cipherSuite
                Log.i(TAG,"Cipher Suite : " + con.cipherSuite)
                Log.i(TAG,"\n")

                val certs = con.serverCertificates
                for (cert in certs) {
                    resu+="\nCert Type : " + cert.type
                    Log.i(TAG,"Cert Type : " + cert.type)
                    resu+="\nCert Hash Code : " + cert.hashCode()
                    Log.i(TAG,"Cert Hash Code : " + cert.hashCode())
                    resu+="\nCert Public Key Algorithm : " + cert.publicKey.algorithm
                    Log.i(TAG,"Cert Public Key Algorithm : " + cert.publicKey.algorithm)
                    resu+="\nCert Public Key Format : " + cert.publicKey.format
                    Log.i(TAG,"Cert Public Key Format : " + cert.publicKey.format)
                    Log.i(TAG,"\n")
                }

            } catch (e: SSLPeerUnverifiedException) {
                Log.e(TAG,"Cause d'erreur"+e.cause.toString())
                resu = getRootException(e).message!!
                e.printStackTrace()
            } catch (e: IOException) {
                Log.e(TAG,"Cause d'erreur IO "+ getRootException(e).message)
                resu = getRootException(e).message!!
                e.printStackTrace()
            }
        }
    }

    private fun print_content(con: HttpsURLConnection?) {
        if (con != null) {
            try {
                Log.i(TAG,"****** Content of the URL ********")
                val inputStream: InputStream = con.inputStream
                val chaine = inputStream.bufferedReader().use(BufferedReader::readText)
                //println(chaine)
                //Log.i("On doInBackground", "inputStream :$chaine")

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun getRootException(exception: Throwable): Throwable {
        var rootException = exception
        while (rootException.cause != null) {
            rootException = rootException.cause!!
        }
        return rootException
    }
}
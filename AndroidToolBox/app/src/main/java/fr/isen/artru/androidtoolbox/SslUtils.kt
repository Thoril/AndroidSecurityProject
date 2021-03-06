package fr.isen.artru.androidtoolbox

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import android.widget.Toast
import java.security.KeyStore
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory


object SslUtils {

    fun getSslContextForCertificateFile(context: Context, fileName: String): SSLContext {
        try {
            val keyStore = SslUtils.getKeyStore(context, fileName)
            val sslContext = SSLContext.getInstance("SSL")
            val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            trustManagerFactory.init(keyStore)

            Log.d("TrustManager : ", trustManagerFactory.toString())
            sslContext.init(null, trustManagerFactory.getTrustManagers(),null)
            return sslContext
        } catch (e: Exception) {
            val msg = "Error during creating SslContext for certificate from assets"
            Log.e("SslUtilsAndroid", msg, e)
            throw RuntimeException(msg)
        }

    }

    private fun getKeyStore(context: Context, fileName: String): KeyStore? {
        var keyStore: KeyStore? = null
        try {
            val assetManager = context.getAssets()
            val cf = CertificateFactory.getInstance("X.509")
            val caInput = assetManager.open(fileName)
            val ca: Certificate
            try {
                ca = cf.generateCertificate(caInput)
                Log.d("SslUtilsAndroid", "ca : " + (ca as X509Certificate).getSubjectDN() +"\nCert type : " +ca.type +"\n Cert hash code : "+ca.hashCode()+"\n Cert Public Key Algorithm : " +ca.publicKey.algorithm + "\n Cert public key format : " + ca.publicKey.format)
            } finally {
                caInput.close()
            }

            val keyStoreType = KeyStore.getDefaultType()
            keyStore = KeyStore.getInstance(keyStoreType)
            keyStore!!.load(null, null)
            keyStore.setCertificateEntry("ca", ca)
        } catch (e: Exception) {
            Log.e("SslUtilsAndroid", "Error during getting keystore", e)
        }

        return keyStore
    }
}
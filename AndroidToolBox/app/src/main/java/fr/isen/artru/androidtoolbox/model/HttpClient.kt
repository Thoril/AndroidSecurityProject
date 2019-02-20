package fr.isen.artru.androidtoolbox.model

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Toast
import okhttp3.Call
import okhttp3.Callback
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import okhttp3.logging.HttpLoggingInterceptor.Logger
import java.io.IOException
import java.net.URL

class HttpClient(
    private val okHttpClient: OkHttpClient = createOkHttpClient()
) {

    fun load(display: (String) -> Unit) {
        val request = Request.Builder()
            .url("https://www.isen-mediterranee.fr/")
            .head()
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                display(e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                display("request: $request \nResponse Code: ${response.code()} \nProtocol: ${response.protocol()} \nMessage: ${response.message()}")
            }
        })
    }
}

private const val DOMAIN = "www.isen-mediterranee.fr"
private const val PIN = "sha256/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"

private fun createOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder().apply {
        addInterceptor(HttpLoggingInterceptor(Logger { println(it) }).setLevel(Level.BASIC))
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            certificatePinner(CertificatePinner.Builder()
                .add(DOMAIN, PIN)
                .build()
            )
        }
    }.build()
}
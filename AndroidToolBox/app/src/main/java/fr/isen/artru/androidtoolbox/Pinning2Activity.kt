package fr.isen.artru.androidtoolbox

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import fr.isen.artru.androidtoolbox.model.HttpClient
import kotlinx.android.synthetic.main.activity_pinning2.*

class Pinning2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pinning2)

        val httpClient = HttpClient()

        button_go.setOnClickListener {
            httpClient.load(::display)
        }
    }

    private fun display(text: String) {
        runOnUiThread {
            text1.text = text
        }
    }
}

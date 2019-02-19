package fr.isen.artru.androidtoolbox

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_certificate_pinning.*


class CaPinningActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certificate_pinning)

        trust_button.setOnClickListener{
            startActivity(Intent(this,TrustManagerActivity::class.java))
        }

        pinning_button.setOnClickListener{
            startActivity(Intent(this, Pinning2Activity::class.java))
        }
    }






}

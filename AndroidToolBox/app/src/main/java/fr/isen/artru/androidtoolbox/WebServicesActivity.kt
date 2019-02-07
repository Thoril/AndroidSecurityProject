package fr.isen.artru.androidtoolbox

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.artru.androidtoolbox.model.RandomUserData
import kotlinx.android.synthetic.main.activity_web_services.*


class WebServicesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_services)

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://randomuser.me/api?results=30"

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                val resu = Gson().fromJson(response, RandomUserData::class.java)
                val mAdapter = UserAdapter(resu.results)
                val mLayoutManager = LinearLayoutManager(applicationContext)
                recyclerUser.layoutManager = mLayoutManager
                recyclerUser.itemAnimator = DefaultItemAnimator()
                recyclerUser.adapter = mAdapter

            },
            Response.ErrorListener {  })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)


    }
}

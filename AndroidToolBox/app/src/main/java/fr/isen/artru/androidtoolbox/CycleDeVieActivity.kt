package fr.isen.artru.androidtoolbox

import android.graphics.Color
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_cycle_de_vie.*
import kotlin.random.Random

class CycleDeVieActivity : AppCompatActivity(), CycleDeVieFragment.OnFragmentInteractionListener {

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.w("LifeCycle","Create")
        setContentView(R.layout.activity_cycle_de_vie)
        swapFragment.setOnClickListener{
            fragment.fragmentManager!!.beginTransaction().replace(R.id.fragment, CycleDeVieFragment()).commit()
            fragment.view!!.setBackgroundColor(Color.argb(255, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256)))
        }
    }

    override fun onStart() {
        super.onStart()
        Log.w("LifeCycle","onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.w("LifeCycle","OnResume")
        activityState.setText("OnResume")
    }

    override fun onPause() {
        super.onPause()
        Log.w("LifeCycle","OnPause")
        activityState.setText("OnPause")
    }

    override fun onStop() {
        super.onStop()
        Log.w("LifeCycle","OnStop")
        activityState.setText("OnStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.w("LifeCycle","onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.w("LifeCycle","onDestroy")
        Toast.makeText(this, "OnDestroy", Toast.LENGTH_SHORT).show()
    }
}

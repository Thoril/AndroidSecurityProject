package fr.isen.artru.androidtoolbox

import android.arch.persistence.room.Room
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import fr.isen.artru.androidtoolbox.model.database.AppDatabase
import kotlinx.android.synthetic.main.activity_web_services.*

class UserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()

        val res = db.userDao().getAll()
        val mAdapter = UserAdapter(res)
        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerUser.layoutManager = mLayoutManager
        recyclerUser.itemAnimator = DefaultItemAnimator()
        recyclerUser.adapter = mAdapter
    }
}

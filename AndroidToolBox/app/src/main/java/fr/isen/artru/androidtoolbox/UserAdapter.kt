package fr.isen.artru.androidtoolbox

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.support.v7.widget.RecyclerView
import android.view.View
import fr.isen.artru.androidtoolbox.model.database.User


class UserAdapter(private val UserList: List<User>) : RecyclerView.Adapter<UserAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var firstName: TextView
        var lastName: TextView
        var password: TextView

        init {
            firstName = view.findViewById(R.id.firstName)
            lastName = view.findViewById(R.id.lastName)
            password = view.findViewById(R.id.password)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_adapter, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = UserList[position]
        holder.firstName.text = user.firstName
        holder.lastName.text = user.lastName
        holder.password.text = user.password
    }

    override fun getItemCount(): Int {
        return UserList.size
    }
}
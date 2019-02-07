package fr.isen.artru.androidtoolbox


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso
import fr.isen.artru.androidtoolbox.model.Results


class UserAdapter(private val UserList: List<Results>?) : RecyclerView.Adapter<UserAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView
        var adress: TextView
        var email: TextView
        var logo: ImageView

        init {
            name = view.findViewById(R.id.name)
            adress = view.findViewById(R.id.adress)
            email = view.findViewById(R.id.mail)
            logo =view.findViewById(R.id.logo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_adapter, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = UserList?.get(position)
        holder.name.text = user?.name?.first?.substring(0, 1)?.toUpperCase() + user?.name?.first?.substring(1) + " " +
                user?.name?.last?.toUpperCase()
        holder.adress.text = user?.location?.adress
        holder.email.text = user?.email
        Picasso.get().load(user?.picture?.medium).transform(CircleTransform()).into(holder.logo)

    }

    override fun getItemCount(): Int {
        return UserList?.size ?: 0
    }
}
package fr.isen.artru.androidtoolbox

import android.content.Context
import android.widget.TextView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.ArrayAdapter

class ContactAdapter(private val dataSet: ArrayList<ContactData>, internal var mContext: Context) :
    ArrayAdapter<ContactData>(mContext, R.layout.contact_adapter, dataSet), View.OnClickListener {

    private var lastPosition = -1

    // View lookup cache
    private class ViewHolder {
        internal var txtName: TextView? = null
        internal var txtNumber: TextView? = null

    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        // Get the data item for this position
        val dataModel = getItem(position)
        // Check if an existing view is being reused, otherwise inflate the view
        val viewHolder: ViewHolder // view lookup cache stored in tag

        val result: View

        if (convertView == null) {

            viewHolder = ViewHolder()
            val inflater = LayoutInflater.from(context)
            convertView = inflater.inflate(R.layout.contact_adapter, parent, false)
            viewHolder.txtName = convertView!!.findViewById(R.id.nom)
            viewHolder.txtNumber = convertView!!.findViewById(R.id.tel)
            result = convertView

            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
            result = convertView
        }

        viewHolder.txtName!!.text = dataModel!!.name
        viewHolder.txtNumber!!.text = dataModel.number

        // Return the completed view to render on screen
        return convertView
    }
    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
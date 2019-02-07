package fr.isen.artru.androidtoolbox

import android.Manifest
import android.app.Activity
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_permission.*
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.widget.Toast
import android.graphics.BitmapFactory
import android.content.Intent
import java.io.FileNotFoundException
import android.os.Build
import android.provider.ContactsContract
import android.location.Criteria
import android.support.v7.app.AlertDialog
import android.provider.ContactsContract.CommonDataKinds.Phone

const val PERMISSIONS_REQUEST_READ_CONTACTS = 100
const val MY_PERMISSIONS_REQUEST = 10

class PermissionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)
        showContacts()

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(this, "Activer votre GPS pour afficher les données de localisation", Toast.LENGTH_SHORT)
                    .show()
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST)
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

            // Define a listener that responds to location updates
            val locationListener = object : LocationListener {

                override fun onLocationChanged(location: Location) {
                    // Called when a new location is found by the network location provider.
                    //makeUseOfNewLocation(location)
                    latitude.text = location.latitude.toString()
                    longitude.text = location.longitude.toString()
                }
                override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
                }
                override fun onProviderEnabled(provider: String) {
                }
                override fun onProviderDisabled(provider: String) {
                }
            }
            val criteria = Criteria()
            //obtenir le fournisseur qui correspond le plus à nos critères
            val provider = locationManager.getBestProvider(criteria, false)
            locationManager.requestLocationUpdates(provider, 0, 0f, locationListener)
            val location = locationManager.getLastKnownLocation(provider)
            if(location !=null) {
                latitude.text = location.latitude.toString()
                longitude.text = location.longitude.toString()
            }

            if (  !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("GPS non activé")  // GPS not found
                builder.setMessage("Voulez vous activez votre GPS") // Want to enable?
                builder.setPositiveButton("Oui"
                ) { _, _ -> this.startActivity(Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
                builder.setNegativeButton("Non", null)
                builder.create().show()
            }
        }

        val RESULT_LOAD_IMG = 1
        photo.setOnClickListener{
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG)
        }
    }

    override fun onActivityResult(reqCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(reqCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            try {
                val imageUri = data!!.data
                val imageStream = contentResolver.openInputStream(imageUri!!)
                val selectedImage = BitmapFactory.decodeStream(imageStream)
                photo.setImageBitmap(selectedImage)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Toast.makeText(applicationContext, "Une erreur s'est produite", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(applicationContext, "Vous n'avez pas choisi d'image", Toast.LENGTH_LONG).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                showContacts()
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun showContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), PERMISSIONS_REQUEST_READ_CONTACTS)
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            val contacts = getContactNames()
            val adapter = ContactAdapter(contacts as ArrayList<ContactData>,this)
            contactList.adapter=adapter
        }
    }

    private fun getContactNames(): List<ContactData> {
        val contacts = ArrayList<ContactData>()
        // Get the ContentResolver
        val cr = contentResolver
        // Get the Cursor of all the contacts
        //val simUri = Uri.parse("content://icc/adn")
        val PROJECTION = arrayOf(ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME, Phone.NUMBER)
        val cursor = cr.query(Phone.CONTENT_URI, PROJECTION, null, null, null)
        //val cursorSim = this.contentResolver.query(simUri, null, null, null, null)
        // Move the cursor to first. Also check whether the cursor is empty or not.
        if (cursor!!.moveToFirst()) {
            // Iterate through the cursor
            do {
                // Get the contacts name
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val num = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                val contact = ContactData(name,num)
                contacts.add(contact)
            } while (cursor.moveToNext())
        }
        // Close the curosor
        cursor.close()

        return contacts
    }

}
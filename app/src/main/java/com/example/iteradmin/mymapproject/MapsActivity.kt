package com.example.iteradmin.mymapproject

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private  var latitude:Double=10.0
    val REQUEST_CODE=100
    private  var longitude:Double=32.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        if(longitude!=null && latitude!=null){
            val sydney=LatLng(latitude,longitude)
            mMap.addMarker(MarkerOptions().position(sydney).title("Marker in device"))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        }
        else{
        val sydney= LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }}

    fun getLocation(){

        val fl= LocationServices.
                getFusedLocationProviderClient(this)

        if(ContextCompat.checkSelfPermission(this,android.Manifest.
                        permission.ACCESS_COARSE_LOCATION)== PackageManager.
                        PERMISSION_GRANTED) {
            fl.lastLocation.addOnSuccessListener { location: Location? ->
                if(location==null){
                    Toast.makeText(this,"Location Not Found",Toast.LENGTH_LONG).show()
                }else{
                    latitude=location.latitude
                    longitude=location.longitude
                    Toast.makeText(this,"Location Found",Toast.LENGTH_LONG).show()

                }
            }
        }
                 else{
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),100)
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode==100)
        {
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permission han been granted",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,"Permission denied",Toast.LENGTH_LONG).show()
            }
        }
    }
}

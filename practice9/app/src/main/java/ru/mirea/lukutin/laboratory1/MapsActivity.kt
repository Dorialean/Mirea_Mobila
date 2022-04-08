package ru.mirea.lukutin.laboratory1

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ru.mirea.lukutin.laboratory1.databinding.ActivityMapsBinding


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val markers: ArrayList<MarkerOptions> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        mMap.setOnMapClickListener(this);
        val sydney = LatLng(-34.0, 151.0)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        setUpMap()
        placeMarkers(markers)
        mMap.isMyLocationEnabled = true
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.isTrafficEnabled = true
        addNewMarker("Marker in Sydney","",sydney)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    private fun addNewMarker(title: String, snippet: String, latLng: LatLng) {
        val marker = MarkerOptions().title(title)
            .snippet(snippet).position(latLng)
        mMap.addMarker(marker)
        markers.add(marker)
    }


    override fun onMapClick(p0: LatLng) {
        val cameraPosition = CameraPosition.Builder().target(
            p0
        ).zoom(12f).build()
        mMap.animateCamera(
            CameraUpdateFactory
                .newCameraPosition(cameraPosition)
        )
        mMap.addMarker(
            MarkerOptions().title("Где я?")
                .snippet("Новое место").position(p0)
        )
    }

    private fun setUpMap(){
        mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
        val mirea = LatLng(55.670005, 37.479894)
        val cameraPosition = CameraPosition.Builder().target(
            mirea
        ).zoom(12f).build()
        mMap.animateCamera(
            CameraUpdateFactory
                .newCameraPosition(cameraPosition)
        )
        mMap.addMarker(
            MarkerOptions().title("МИРЭА")
                .snippet("Крупнейший политехнический ВУЗ").position(mirea)
        )
    }
    private fun placeMarkers(markers: ArrayList<MarkerOptions>) {
        for (marker in markers) mMap.addMarker(marker)
    }
}
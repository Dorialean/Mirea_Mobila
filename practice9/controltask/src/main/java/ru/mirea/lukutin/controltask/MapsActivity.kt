package ru.mirea.lukutin.controltask

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
import ru.mirea.lukutin.controltask.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

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

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        mMap.isMyLocationEnabled = true
    }

    private fun controlTaskMapping(){

        var mirea = LatLng(55.661445, 37.477049)
        var cameraPosition = CameraPosition.Builder().target(
            mirea
        ).zoom(12f).build()
        mMap.animateCamera(
            CameraUpdateFactory.newCameraPosition(
                cameraPosition
            )
        )
        var snippetText = """
            Корпус РТУ МИРЭА
            Дата основания: 1 июля 1900 г
            Адрес: пр-т Вернадского 86 
            Координаты: 55.661445 , 37.477049
            """.trimIndent()
        includeMarker("РТУ МИРЭА, Институт тонких химический технологий", snippetText, mirea)


        mirea = LatLng(55.794259, 37.701448)
        cameraPosition = CameraPosition.Builder().target(mirea).zoom(12f).build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        snippetText = """
            Корпус РТУ МИРЭА
            Дата основания: 29 августа 1936 г
            Адрес: ул. Стромынка 20 
            Координаты: 55.794259, 37.701448 
            """.trimIndent()
        includeMarker("РТУ МИРЭА", snippetText, mirea)

    }

    private fun includeMarker(title: String, snippet: String, latLng: LatLng) {
        val marker: MarkerOptions = MarkerOptions().title(title)
            .snippet(snippet).position(latLng)
        mMap.addMarker(marker)
        markers.add(marker)
    }

    private fun inputMarkers(markers: ArrayList<MarkerOptions>) {
        for (marker in markers)
            mMap.addMarker(marker)
    }

}
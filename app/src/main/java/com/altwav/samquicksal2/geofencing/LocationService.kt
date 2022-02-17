package com.altwav.samquicksal2.geofencing

import android.Manifest
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.altwav.samquicksal2.Adapters.NearbyRestaurantsAdapter
import com.altwav.samquicksal2.R
import com.altwav.samquicksal2.models.GeofencingModel
import com.altwav.samquicksal2.models.GeofencingModelResponse
import com.altwav.samquicksal2.models.NearbyRestoModel
import com.altwav.samquicksal2.models.QRScannedModel
import com.altwav.samquicksal2.retrofit.RetroInstance
import com.altwav.samquicksal2.retrofit.RetroServiceInterface
import com.altwav.samquicksal2.viewmodel.NearbyRestoViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import retrofit2.Call
import retrofit2.Response

class LocationService: Service() {
    private lateinit var location: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    fun getLocation(){
        val sharedPreferences = this.applicationContext?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val customerId = sharedPreferences?.getInt("CUSTOMER_ID", 0)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        } else {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(applicationContext)
            fusedLocationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, null)
            fusedLocationProviderClient.locationAvailability.addOnSuccessListener {
                if(it.isLocationAvailable){
                    fusedLocationProviderClient.lastLocation.addOnCompleteListener { location2 ->
                        val location: Location = location2.result

                        val custLoc = GeofencingModel(customerId, location.latitude.toString(), location.longitude.toString())

                        Log.e("Service", "${custLoc}")
                        val retroService = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
                        val call = retroService.geofencingListener(custLoc)
                        call.enqueue(object : retrofit2.Callback<GeofencingModelResponse> {
                            override fun onFailure(call: Call<GeofencingModelResponse>?, t: Throwable?) {

                            }
                            override fun onResponse(
                                call: Call<GeofencingModelResponse>?,
                                response: Response<GeofencingModelResponse>?
                            ) {
                                if(response != null){

                                } else {

                                }
                            }

                        })
                    }
                } else {
                    //do nothing
                    Log.e("Service", "Cannot process on background")
                }
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val mainHandler = Handler(Looper.getMainLooper())

        mainHandler.post(object : Runnable {
            override fun run() {
                getLocation()
                mainHandler.postDelayed(this, 10000)
            }
        })

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
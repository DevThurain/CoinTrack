package com.thurainx.cointrack.presentation.map

import android.Manifest
import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import com.thurainx.cointrack.R
import kotlin.math.absoluteValue
import kotlin.random.Random


const val ACTION_START = "1"
const val ACTION_PAUSE = "2"
const val ACTION_END = "3"


typealias Polyline = MutableList<LatLng>
typealias Polylines = MutableList<Polyline>


class TestService : Service(){

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    companion object {
        val isTracking = MutableLiveData<Boolean>()
        val pathPoints = MutableLiveData<Polylines>()
        val liveInt = MutableLiveData<MutableList<Int>>()

    }

    private fun addPathPoint(location: Location?) {
        location?.let {
            var pos = LatLng(location.latitude, location.longitude)
//            pathPoints.value?.apply {
//                plus(last().add(pos))
//                Log.d("raw",pos.toString())
//                pathPoints.postValue(this)
//            }

//            pathPoints.value?.apply {
//                plus(pathPoints.value?.last()?.add(pos))
//                Log.d("raw", pos.toString())
//                pathPoints.postValue(this)
//            }


            var temp : MutableList<Polyline> = mutableListOf(mutableListOf())
            pathPoints.value?.forEach {
                if(it.isNotEmpty())
                temp.add(it)
            }

            //temp.last().add(pos)
            pos = LatLng(Random.nextDouble(0.77,17.77),Random.nextDouble(0.1,96.1))
            temp.last().add(pos)

            pathPoints.value?.clear()
            pathPoints.value = pathPoints.value?.plus(temp) as MutableList<Polyline>
            //pathPoints.value = pathPoints.value?.plus(pathPoints.value?.last()?.add(pos)) as MutableList<Polyline>

            liveInt.value = liveInt.value?.plus(mutableListOf(location.latitude.toInt()))?.toMutableList()
            temp.clear()
        }
    }

    private fun addEmptyPolyline() = pathPoints.value?.apply {
        add(mutableListOf())
        pathPoints.postValue(this)
    } ?: pathPoints.postValue(mutableListOf(mutableListOf()))

    @SuppressLint("MissingPermission")
    private fun updateLocationTracking(isTracking: Boolean) {
        if(isTracking) {
                val request = LocationRequest().apply {
                    interval = 5000L
                    fastestInterval = 5000L
                    priority = PRIORITY_HIGH_ACCURACY
                }
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
                fusedLocationProviderClient.requestLocationUpdates(
                    request,
                    locationCallback,
                    Looper.getMainLooper()
                )

        } else {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        }
    }

    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)
            if(true) {
                result?.locations?.let { locations ->
                    for(location in locations) {
                        addPathPoint(location)
                        Log.d("location","NEW LOCATION: ${location.latitude}, ${location.longitude}")
                    }
                }
            }
        }
    }


    override fun onCreate() {
        super.onCreate()
        liveInt.value = mutableListOf(0)
        pathPoints.value?.apply {
            plus((mutableListOf(mutableListOf())))
            pathPoints.postValue(this)
        }
        fusedLocationProviderClient = FusedLocationProviderClient(this)


    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when(it.action){
                ACTION_START -> {

                    Log.d("service","start service")
                    updateLocationTracking(true)

                    buildNoti()
                }
                ACTION_PAUSE -> {
                    Log.d("service","pause service")
                }
                ACTION_END -> {
                    Log.d("service","end service")
                    updateLocationTracking(false)

                    endService()
                }

                else -> {
                    Log.d("service","unknown action")
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


    fun buildNoti(){
        addEmptyPolyline()
        val notiBuilder = NotificationCompat.Builder(this, "noti_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Noti Title")
            .setContentText("service is running")
            .setAutoCancel(false)
            .setOngoing(true)

        startForeground(1,notiBuilder.build())
    }

    fun endService(){
        stopForeground(true)
        stopSelf()
    }


}
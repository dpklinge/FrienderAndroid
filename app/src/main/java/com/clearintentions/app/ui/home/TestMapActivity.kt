package com.clearintentions.app.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import com.clearintentions.app.data.model.BasicUser
import com.clearintentions.app.data.model.ParcelableString
import com.clearintentions.app.services.LocationService
import com.clearintentions.app.utils.changeActivity
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import javax.inject.Inject

class TestMapActivity : AppCompatActivity() {
    @Inject
    lateinit var locationService: LocationService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setContent {
            val user = remember { mutableStateOf<BasicUser?>(null) }
            intent.extras?.getParcelable<BasicUser>("user").let { user.value = it }
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
                val context = LocalContext.current
                changeActivity(context, HomePageActivity::class.java, arrayOf("user", "error"), arrayOf(user.value!!, ParcelableString("Location services not enabled")))
            }
            val lastLocation = fusedLocationClient.lastLocation.result

            LocationMap(location = LatLng(lastLocation.latitude, lastLocation.latitude))
        }
    }
}

@Composable
fun LocationMap(location: LatLng) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, 10f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = location),
            title = "You!",
            snippet = "Marker for you"
        )
    }
}

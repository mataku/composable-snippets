package com.mataku.snippets.ui.compose.sample

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.android.catalog.framework.annotations.Sample
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.Locale

@Sample(
  name = "Location Completion",
  description = "Get address from current location",
  tags = ["Location", "Geocoder"],
  sourcePath = "https://github.com/mataku/composable-snippets/blob/develop/app/src/main/java/com/mataku/snippets/ui/compose/sample/LocationCompletionScreen.kt"
)
@Composable
fun LocationCompletionScreen() {
  val context = LocalContext.current
  val hasLocationPermission = remember {
    mutableStateOf(
      ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
      ) == PackageManager.PERMISSION_GRANTED
    )
  }

  val locationLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.RequestPermission()
  ) { isGranted: Boolean ->
    hasLocationPermission.value = isGranted
  }

  var address by remember { mutableStateOf("No addresses") }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(
        16.dp
      ),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
  ) {
    if (hasLocationPermission.value) {
      Button(onClick = {
        getLocationAddress(
          context = context,
          provider = LocationManager.GPS_PROVIDER
        ) { result ->
          address = result
        }
      }) {
        Text(text = "Get Location from GPS_PROVIDER")
      }
      Spacer(modifier = Modifier.height(16.dp))
      Button(onClick = {
        getLocationAddress(
          context = context,
          provider = LocationManager.NETWORK_PROVIDER
        ) { result ->
          address = result
        }
      }) {
        Text(text = "Get Location from NETWORK_PROVIDER")
      }
      Spacer(modifier = Modifier.height(16.dp))
      Button(onClick = {
        getLocationAddress2(context) { result ->
          address = result
        }
      }) {
        Text(text = "Get Location using play-services-location")
      }
      Spacer(modifier = Modifier.height(24.dp))
      Text(text = address)
    } else {
      Button(onClick = {
        locationLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
      }) {
        Text(text = "Request Location permission")
      }
    }
  }
}

@RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
private fun getLocationAddress(
  context: Context,
  provider: String = LocationManager.NETWORK_PROVIDER,
  onGetAddress: (String) -> Unit,
) {
  val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
  val location = locationManager.getLastKnownLocation(provider)
  if (location != null) {
    val geocoder = Geocoder(context, Locale.getDefault())

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      geocoder.getFromLocation(
        location.latitude,
        location.longitude,
        1
      ) { addresses ->
        if (addresses.isNotEmpty()) {
          onGetAddress.invoke(addresses[0].getAddressLine(0))
        }
      }
    } else {
      val result = geocoder.getFromLocation(location.latitude, location.longitude, 1)?.get(0)
        ?.getAddressLine(0)
      result?.let(onGetAddress)
    }
  } else {
    onGetAddress.invoke("No location")
  }
}


@RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
private fun getLocationAddress2(context: Context, onGetAddress: (String) -> Unit) {
  val fusedLocationClient: FusedLocationProviderClient =
    LocationServices.getFusedLocationProviderClient(context)
  fusedLocationClient.lastLocation.addOnSuccessListener { location ->
    if (location != null) {
      val geocoder = Geocoder(context, Locale.getDefault())
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        geocoder.getFromLocation(
          location.latitude,
          location.longitude,
          1
        ) { addresses ->
          println("MATAKUDEBUG addresses2 $addresses")
          if (addresses.isNotEmpty()) {
            onGetAddress.invoke(addresses[0].getAddressLine(0))
          }
        }
      } else {
        val address = geocoder.getFromLocation(location.latitude, location.longitude, 1)?.get(0)
          ?.getAddressLine(0)
        address?.let(onGetAddress)
      }
    } else {
      onGetAddress("No location 2")
    }
  }
}

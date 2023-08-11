package com.example.myapplication.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentSender
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.Window
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.utils.AppUtil.hasGps
import com.example.myapplication.utils.Constants.Companion.FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
import com.example.myapplication.utils.Constants.Companion.REQUEST_CHECK_SETTINGS
import com.example.myapplication.utils.Constants.Companion.UPDATE_INTERVAL_IN_MILLISECONDS
import com.example.myapplication.utils.Permissions.REQUEST_CODE_LOCATION_PERMISSIONS
import com.example.myapplication.utils.Permissions.locationPermissionGranted
import com.example.myapplication.utils.observe
import com.example.myapplication.viewModel.WeatherViewModel
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: WeatherViewModel

    //Location
    private var requestingLocationUpdates: Boolean? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var settingsClient: SettingsClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private var currentLatLng: LatLng? = null
    private var currentLocation: Location? = null
    private lateinit var locationSettingsRequest: LocationSettingsRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        supportActionBar?.hide()
        initViews()

        requestingLocationUpdates = false
        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(this)
        settingsClient = LocationServices.getSettingsClient(this)
        createLocationCallback()
        createLocationRequest()
        buildLocationSettingsRequest()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStart() {
        super.onStart()

        if (!locationPermissionGranted(this)) {
            requestLocPermissions()
        } else {
            startLocationUpdates()
        }

    }

    private fun initViews() {
        addObservers()
    }

    private fun addObservers() {
        observe(viewModel.state, ::onViewStateChanged)
    }

    private fun onViewStateChanged(state: MainViewState) {

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestLocPermissions() {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(
            this@MainActivity,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        if (shouldProvideRationale) {
            if (!locationPermissionGranted(this)) {
                permissionRequiredDialog()
            }

        } else {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                REQUEST_CODE_LOCATION_PERMISSIONS
            )
        }
    }

    /**
     * n]F
     *  Request for location permission
     **/
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_CODE_LOCATION_PERMISSIONS -> if (locationPermissionGranted(this)) {
                startLocationUpdates()
            } else {
                permissionRequiredDialog()
            }
        }
    }

    private fun permissionRequiredDialog() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setTitle(resources.getString(R.string.text_permissions))
            builder.setMessage(R.string.text_permissions_prompt)
            builder.setCancelable(true)
            builder.setNegativeButton(resources.getString(R.string.text_exit)) { dialogInterface, _ ->
                if (!locationPermissionGranted(this@MainActivity)) {
                    dialogInterface.dismiss()
                    this.finish()
                } else {
                    dialogInterface.dismiss()
                }
            }
            builder.setPositiveButton(resources.getString(R.string.text_grant_permissions)) { _, _ ->
                val i = Intent()
                i.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                i.addCategory(Intent.CATEGORY_DEFAULT)
                i.data = Uri.parse("package:" + this.packageName)
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                this.startActivity(i)
            }
            val dialog = builder.create()
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) //before
            dialog.show()
        } else if (!hasGps(this)) {
            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setTitle(R.string.text_gps_disabled)
            builder.setMessage(R.string.text_gps_prompt)
            builder.setCancelable(false)
            builder.setPositiveButton(R.string.text_enable) { _, _ ->
                startActivity(
                    Intent(
                        Settings.ACTION_LOCATION_SOURCE_SETTINGS
                    )
                )
            }
            builder.setNegativeButton(R.string.text_exit) { dialog, _ ->
                if (!locationPermissionGranted(this)) {
                    dialog.dismiss()
                    this@MainActivity.finish()
                } else {
                    dialog.dismiss()
                }
            }
            val dialog = builder.create()
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) //before
            dialog.show()
        }
    }

    /**
     * Uses a [com.google.android.gms.location.LocationSettingsRequest.Builder] to build
     * a [com.google.android.gms.location.LocationSettingsRequest] that is used for checking
     * if a device has the needed location settings.
     */
    private fun buildLocationSettingsRequest() {
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(locationRequest)
        locationSettingsRequest = builder.build()
    }

    /**
     * Creates a callback for receiving location events.
     */
    private fun createLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                currentLocation = locationResult.lastLocation
                currentLatLng = locationResult.lastLocation?.latitude?.let { lat ->
                    locationResult.lastLocation?.longitude?.let { long ->

                        val latitude = lat.toString()
                        val longitude = long.toString()
                        viewModel.getCurrentWeather(latitude, longitude)
                        viewModel.getFiveDayWeather(latitude, longitude)

                        LatLng(
                            lat,
                            long
                        )
                    }
                }
            }
        }
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.interval = UPDATE_INTERVAL_IN_MILLISECONDS
        locationRequest.fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    /**
     * Requests location updates from the FusedLocationApi. Note: we don't call this unless location
     * runtime permission has been granted.
     */
    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = UPDATE_INTERVAL_IN_MILLISECONDS
        locationRequest.fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)

        // Begin by checking if the device has the necessary location settings.
        settingsClient.checkLocationSettings(locationSettingsRequest)
            .addOnSuccessListener(this) {
                Looper.myLooper()?.let { it1 ->
                    fusedLocationClient.requestLocationUpdates(
                        this.locationRequest,
                        locationCallback, it1
                    )
                }

            }
            .addOnFailureListener(this) { e ->
                when ((e as ApiException).statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the
                            // result in onActivityResult().
                            val rae = e as ResolvableApiException
                            rae.startResolutionForResult(
                                this,
                                REQUEST_CHECK_SETTINGS
                            )
                        } catch (e: IntentSender.SendIntentException) {
                            Timber.w(e)
                        }
                    }

                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                        val errorMessage = "Location settings are inadequate, and cannot be " +
                                "fixed here. Fix in Settings."
                        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
                    }
                }
            }
    }
}
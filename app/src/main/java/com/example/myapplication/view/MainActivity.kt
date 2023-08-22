package com.example.myapplication.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentSender
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.TransitionDrawable
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getColor
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.model.dataSource.network.data.response.CurrentWeather
import com.example.myapplication.model.dataSource.network.data.response.FiveDayWeather
import com.example.myapplication.utils.AppUtil.hasGps
import com.example.myapplication.utils.Constants
import com.example.myapplication.utils.Constants.Companion.FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
import com.example.myapplication.utils.Constants.Companion.REQUEST_CHECK_SETTINGS
import com.example.myapplication.utils.Constants.Companion.UPDATE_INTERVAL_IN_MILLISECONDS
import com.example.myapplication.utils.DateTimeUtils.Companion.formatStringToDate
import com.example.myapplication.utils.DateTimeUtils.Companion.getDayOfWeek
import com.example.myapplication.utils.DateTimeUtils.Companion.getTime
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
import java.util.Date


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: WeatherViewModel

    //View binding
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding

    //Location
    private var requestingLocationUpdates: Boolean? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var settingsClient: SettingsClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private var currentLatLng: LatLng? = null
    private var currentLocation: Location? = null
    private lateinit var locationSettingsRequest: LocationSettingsRequest
    private var backgroundcolors: Array<ColorDrawable>? = null
    private var transition: TransitionDrawable? = null
    private var currentWeatherName: String? = null

    //RecyclerView
    private var layoutManager: LinearLayoutManager? = null
    private lateinit var weatherAdapter: WeatherListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding?.root
        setContentView(view)

        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        supportActionBar?.hide()

        // Array of background colors
        backgroundcolors = arrayOf(
            ColorDrawable(getColor(this, R.color.bg_sunny_secondary)),
            ColorDrawable(getColor(this, R.color.bg_cloudy_secondary)),
            ColorDrawable(getColor(this, R.color.bg_rainy_secondary))
        )
        transition = TransitionDrawable(backgroundcolors)

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
        observe(viewModel.fiveDayWeather, ::onFiveDayWeatherChanged)
        observe(viewModel.currentWeather, ::onCurrentWeather)
    }

    private fun onFiveDayWeatherChanged(fiveDayWeather: FiveDayWeather) {
        fiveDayWeather.list?.let {
            setUpRecyclerView(it)
        }
    }

    private fun filterUniqueDates(weatherList: List<CurrentWeather>?): ArrayList<CurrentWeather> {
        val filteredWeatherList: ArrayList<CurrentWeather> = arrayListOf()
        val map = hashMapOf<String, CurrentWeather>()
        val currentDate = Date()

        if (weatherList != null) {
            for (row in weatherList) {
                val date = formatStringToDate(row.date)
                val day = getDayOfWeek(date)

                //exclude today's forecasts
                if (getDayOfWeek(currentDate) != day) {

                    //Only midday temperatures
                    if (getTime(date) == 12) {
                        if (!map.containsKey(day)) {
                            map[day] = row
                        }
                    }
                }
            }

            for ((k, v) in map) {
               filteredWeatherList.add(v)
            }
        }

        return filteredWeatherList
    }

    @SuppressLint("SetTextI18n")
    private fun onCurrentWeather(currentWeather: CurrentWeather) {
        currentWeatherName = currentWeather.weather?.get(0)?.main

        binding?.tvTemp?.text =
            "${currentWeather.main?.temp.toString()}${getString(R.string.text_degrees)}"


        binding?.tvMinTemp?.text =
            "${currentWeather.main?.tempMin.toString()}${getString(R.string.text_degrees)}"
        binding?.tvCurrentTemp?.text =
            "${currentWeather.main?.temp.toString()}${getString(R.string.text_degrees)}"
        binding?.tvMaxTemp?.text =
            "${currentWeather.main?.tempMax.toString()}${getString(R.string.text_degrees)}"

        viewModel.getFiveDayWeather(
            currentLocation?.latitude.toString(),
            currentLocation?.longitude.toString()
        )

        when (currentWeather.weather?.get(0)?.main) {
            Constants.CLOUDY -> {
                binding?.tvWeather?.text = getString(R.string.text_cloudy)
                _binding?.llTerrain?.background = getDrawable(R.drawable.bg_forest_cloudy)
                _binding?.llBackground?.background = transition?.getDrawable(1)
                _binding?.clWeather?.background = transition?.getDrawable(1)
                transition?.startTransition(2000)

                //change statusBar color
                val window = this.window
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = getColor(this, R.color.bg_cloudy_secondary)
            }

            Constants.SUNNY -> {
                binding?.tvWeather?.text = getString(R.string.text_sunny)
                _binding?.llTerrain?.background = getDrawable(R.drawable.bg_forest_sunny)
                _binding?.llBackground?.background = transition?.getDrawable(0)
                _binding?.clWeather?.background = transition?.getDrawable(0)
                transition?.startTransition(2000)

                //change statusBar color
                val window = this.window
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = getColor(this, R.color.bg_sunny_secondary)

            }

            Constants.RAINY -> {
                binding?.tvWeather?.text = getString(R.string.text_rainy)
                _binding?.llTerrain?.background = getDrawable(R.drawable.bg_forest_sunny)
                _binding?.llBackground?.background = transition?.getDrawable(3)
                _binding?.clWeather?.background = transition?.getDrawable(3)
                transition?.startTransition(2000)

                //change statusBar color
                val window = this.window
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = getColor(this, R.color.bg_rainy_secondary)
            }

            else -> {
                _binding?.llTerrain?.background = getDrawable(R.drawable.bg_forest_sunny)
                _binding?.llBackground?.background = transition?.getDrawable(0)
                _binding?.clWeather?.background = transition?.getDrawable(0)
                transition?.startTransition(2000)

                //change statusBar color
                val window = this.window
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = getColor(this, R.color.bg_cloudy_secondary)
            }
        }
    }

    private fun setUpRecyclerView(weatherItemsArray: List<CurrentWeather>) {
        weatherAdapter =
            WeatherListAdapter(
                filterUniqueDates(weatherItemsArray),
                currentWeatherName
            )
        layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding?.rvFiveDay?.isNestedScrollingEnabled = false
        binding?.rvFiveDay?.layoutManager = layoutManager
        binding?.rvFiveDay?.adapter = weatherAdapter
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
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
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
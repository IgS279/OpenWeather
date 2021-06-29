package com.igld279.openweather.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.igld279.openweather.R
import com.igld279.openweather.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

const val TAG: String = "Weather"

class MainActivity : AppCompatActivity() {

    private val locationPermissionRequestCode = 1000

    private lateinit var recyclerHourly: RecyclerView
    private lateinit var adapterHourly : AdapterHourly
    private lateinit var recyclerDaily: RecyclerView
    private lateinit var adapterDaily : AdapterDaily

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        Log.i(TAG, "onCreate +")

        binding.mainViewModel = viewModel
        binding.lifecycleOwner = this

        recyclerHourly = binding.recyclerViewHourly
        recyclerHourly.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false
        )

        recyclerDaily = binding.recyclerViewDaily
        recyclerDaily.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )
        recyclerDaily.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
                // request permission
                Log.i(TAG, "request permission +")
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionRequestCode
                )
        }

        viewModel.myCityName.observe(this){
            Log.i(TAG, "textCity = $it")
        }

        viewModel.weatherCurrent.observe(this){ weatherCurrent ->
            Log.i(TAG, "weatherCurrent = $weatherCurrent")
        }

        viewModel.weatherHourly.observe(this){ weatherHourly ->
            Log.i(TAG, "weatherHourly = $weatherHourly")
            adapterHourly = AdapterHourly(weatherHourly)
            recyclerHourly.adapter = adapterHourly
        }

        viewModel.weatherDaily.observe(this){ weatherDaily ->
            Log.i(TAG, "weatherDaily = $weatherDaily")
            adapterDaily = AdapterDaily(weatherDaily)
            recyclerDaily.adapter = adapterDaily
        }

        viewModel.hasNetwork.observe(this){ hasNetwork ->
            if (!hasNetwork){
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            locationPermissionRequestCode -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    // permission granted
                } else {
                    // permission denied
                    Toast.makeText(
                        this, "You need to grant permission to access location",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

}
package com.example.myapplication.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.utils.observe
import com.example.myapplication.viewModel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: WeatherViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        initViews()
    }

    private fun initViews() {
        viewModel.getCurrentWeather("1.2694", "36.7272")
        viewModel.getFiveDayWeather("1.2694", "36.7272")
        addObservers()
    }

    private fun addObservers() {
        observe(viewModel.state, ::onViewStateChanged)
    }

    private fun onViewStateChanged(state: MainViewState){

    }
}
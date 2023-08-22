package com.example.myapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.R
import com.example.myapplication.model.dataSource.network.data.response.CurrentWeather
import com.example.myapplication.model.dataSource.network.data.response.FiveDayWeather
import com.example.myapplication.model.repository.abstraction.IWeatherRepository
import com.example.myapplication.view.MainViewState
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val iODispatcher: CoroutineDispatcher,
    private val repository: IWeatherRepository,
) : ViewModel() {


    //current weather data
    private val _currentWeather = MutableLiveData<CurrentWeather>()
    val currentWeather: LiveData<CurrentWeather> get() = _currentWeather

    //five day weather
    private val _fiveDayWeather = MutableLiveData<FiveDayWeather>()
    val fiveDayWeather: LiveData<FiveDayWeather> get() = _fiveDayWeather


    private val _state = MutableLiveData<MainViewState>()
    val state: LiveData<MainViewState>
        get() = _state


    fun getCurrentWeather(
        lat: String?,
        long: String
    ) {
        viewModelScope.launch(iODispatcher) {
            _state.postValue(MainViewState.Loading)
            when (val result = repository.getCurrentWeather(
                lat, long
            )) {

                is NetworkResponse.Success -> {
                    _state.postValue(MainViewState.Success)
                    result.body.let {
                        _currentWeather.postValue(it)
                    }
                }

                is NetworkResponse.ServerError -> {
                    _state.postValue(
                        MainViewState.Error(
                            result.body?.message, null, null
                        )
                    )
                }

                is NetworkResponse.NetworkError -> {
                    _state.postValue(
                        MainViewState.Error(
                             null,
                            R.string.error_network,
                            null
                        )
                    )
                }

                is NetworkResponse.UnknownError -> {
                    _state.postValue(
                        MainViewState.Error(
                            null,
                            R.string.error_generic,
                            null
                        )
                    )
                }
            }

        }
    }

    fun getFiveDayWeather(
        lat: String?,
        long: String
    ) {
        viewModelScope.launch(iODispatcher) {
            _state.postValue(MainViewState.Loading)
            when (val result = repository.getFiveDayWeather(
                lat, long
            )) {

                is NetworkResponse.Success -> {
                    _state.postValue(MainViewState.Success)
                    result.body.let {
                        _fiveDayWeather.postValue(it)
                    }
                }

                is NetworkResponse.ServerError -> {
                    _state.postValue(
                        MainViewState.Error(
                            result.body?.message, null, null
                        )
                    )
                }

                is NetworkResponse.NetworkError -> {
                    _state.postValue(
                        MainViewState.Error(
                            null,
                            R.string.error_network,
                            null
                        )
                    )
                }

                is NetworkResponse.UnknownError -> {
                    _state.postValue(
                        MainViewState.Error(
                            null,
                            R.string.error_generic,
                            null
                        )
                    )
                }
            }

        }
    }

}
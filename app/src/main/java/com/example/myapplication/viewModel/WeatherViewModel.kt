package com.example.myapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.di.IoDispatcher
import com.example.myapplication.model.dataSource.network.data.response.GetReasonsResponse
import com.example.myapplication.model.repository.abstraction.IWeatherRepository
import com.example.myapplication.view.MainViewState
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch


@HiltViewModel
class WeatherViewModel(
    @IoDispatcher
    private val iODispatcher: CoroutineDispatcher,
    private val repository: IWeatherRepository,
) : ViewModel() {

    private val _reallocationReasons = MutableLiveData<GetReasonsResponse>()
    val reallocationReasons: LiveData<GetReasonsResponse> get() = _reallocationReasons

    private val _state = MutableLiveData<MainViewState>()
    val state: LiveData<MainViewState>
        get() = _state

    fun fetchReallocationReasons(
        partnerId: String?,
        vendorType: String?,
        country: String?
    ) {
        viewModelScope.launch(iODispatcher) {
            _state.postValue(MainViewState.Loading)
            when (val result = repository.getReasons(
                partnerId,
                country
            )) {

                is NetworkResponse.Success -> {
                    _state.postValue(MainViewState.Success)
                    result.body.let {
                        _reallocationReasons.postValue(it)
                    }
                }
                is NetworkResponse.ServerError -> {
                    _state.postValue(
                        MainViewState.Error(
                            null, result.body?.message, null
                        )
                    )
                }
                is NetworkResponse.NetworkError -> {
                    _state.postValue(
                        MainViewState.Error(
                            "Error",
                            null,
                            null
                        )
                    )
                }
                is NetworkResponse.UnknownError -> {
                    _state.postValue(
                        MainViewState.Error(
                            "error",
                            null,
                            null
                        )
                    )
                }
            }

        }
    }


}
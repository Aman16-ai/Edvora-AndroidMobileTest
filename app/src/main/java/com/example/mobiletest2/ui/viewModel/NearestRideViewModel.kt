package com.example.mobiletest2.ui.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mobiletest2.data.model.Ride
import com.example.mobiletest2.data.repository.ApiResponse
import com.example.mobiletest2.data.repository.RideRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response


class NearestRideViewModel(application: Application) : AndroidViewModel(application) {

    private val rideRepository = RideRepository()

    val rides = rideRepository.rides
    init {
        viewModelScope.launch(Dispatchers.IO) {
            rideRepository.fetchNearestRides()
        }
    }


 

}
package com.example.mobiletest2.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobiletest2.data.repository.RideRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpcomingRideViewModel(application: Application):AndroidViewModel(application) {

    private val rideRepository = RideRepository()

    val upComingRides = rideRepository.upComingRides

    init {
        viewModelScope.launch {
            rideRepository.fetchUpcomingRides()
        }
    }
}
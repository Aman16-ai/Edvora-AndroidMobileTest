package com.example.mobiletest2.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobiletest2.data.model.Ride
import com.example.mobiletest2.data.repository.RideRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PastRideViewModel(application: Application) : AndroidViewModel(application) {

    private val rideRepository = RideRepository()

    val pastRide = rideRepository.pastRides

    val fliteredRides = rideRepository.filteredRide
    init {
        viewModelScope.launch(Dispatchers.IO) {
            rideRepository.fetchPastRides()
        }
    }
    fun filterByState(rides:List<Ride>, state:String)  {
        rideRepository.filterRidesByState(rides,state)
    }

    fun filterByCity(rides: List<Ride>, city:String) {
        rideRepository.filterRidesByCity(rides,city)
    }
}
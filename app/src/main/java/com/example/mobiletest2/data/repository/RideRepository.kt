package com.example.mobiletest2.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mobiletest2.data.model.Ride
import com.example.mobiletest2.data.model.User
import com.example.mobiletest2.data.service.RideApiService
import com.example.mobiletest2.utils.checkPastDate
import com.example.mobiletest2.utils.checkUpcomingDate
import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RideRepository {


    private val _ridesLiveData = MutableLiveData<ApiResponse<List<Ride>>>()
    private val _upComingRidesLiveData = MutableLiveData<ApiResponse<List<Ride>>>()
    private val _pastRidesLiveDate = MutableLiveData<ApiResponse<List<Ride>>>()
    private val _stateFilteredRide = MutableLiveData<List<Ride>>()

    val rides:LiveData<ApiResponse<List<Ride>>>
    get()= _ridesLiveData

    val upComingRides:LiveData<ApiResponse<List<Ride>>>
    get()= _upComingRidesLiveData

    val pastRides : LiveData<ApiResponse<List<Ride>>>
    get()= _pastRidesLiveDate

    val stateFilteredRide : LiveData<List<Ride>>
    get() = _stateFilteredRide

    private fun closestStationDistance(stations:List<Int>, target:Int):Int {
        var max: Int = abs(stations[0] - target)
        for (i in 1 until stations.size) {
            if (abs(stations[i] - target) <= max) {
                max = abs(stations[i] - target)
            }
        }
        return max
    }

    private fun setDistance(rides:List<Ride>) : List<Ride> {
        val updatedRides = ArrayList<Ride>()
        for (ride in rides) {
            val distance = closestStationDistance(ride.station_path,User().station)
            ride.distance = distance
            updatedRides.add(ride)
        }
        return updatedRides
    }
    suspend fun fetchNearestRides() {
            try {
                val rides:List<Ride>? = RideApiService.rideInstance.getAllRides().body()
                var updatedRides = ArrayList<Ride>()
                rides?.let {
                    updatedRides = setDistance(it) as ArrayList<Ride>
                }
                updatedRides.sortBy { it.distance }

                _ridesLiveData.postValue(ApiResponse.Success(updatedRides))
            }
            catch (err:Error) {
                _ridesLiveData.postValue(ApiResponse.Error(err.toString()))
            }
    }

    suspend fun fetchUpcomingRides() {
        try {
            val rides:List<Ride>? = RideApiService.rideInstance.getAllRides().body()
            var updatedRide = ArrayList<Ride>()
            var upComingRideList = ArrayList<Ride>()
            rides?.let {
                updatedRide = setDistance(rides) as ArrayList<Ride>
            }

            for (ride in updatedRide) {
                if(checkUpcomingDate(ride.date)!!) {
                    Log.d("ridedate", "fetchUpcomingRides: ${ride.city}")
                    upComingRideList.add(ride)
                }
            }
            upComingRideList.sortBy { it.distance }
            _upComingRidesLiveData.postValue(ApiResponse.Success(upComingRideList))
        }
        catch(err:Error) {
            _upComingRidesLiveData.postValue(ApiResponse.Error(err.toString()))
        }
    }

    suspend fun fetchPastRides() {
        try {
            val rides:List<Ride>? = RideApiService.rideInstance.getAllRides().body()
            val updatedRides = rides?.let {
                setDistance(it)
            }

            val pastRideList = ArrayList<Ride>()
            for(ride in updatedRides!!) {
                if(checkPastDate(ride.date)!!) {
                    pastRideList.add(ride)
                }
            }
            pastRideList.sortBy { it.distance }
            _pastRidesLiveDate.postValue(ApiResponse.Success(pastRideList))
        }
        catch (err : Error) {
            _pastRidesLiveDate.postValue(ApiResponse.Error(err.toString()))
        }
    }

    fun filterRidesByState(rides : List<Ride>,state:String) {
        if(state == "state") _stateFilteredRide.value = rides
        else _stateFilteredRide.value = rides.filter { it -> it.state == state }
    }

    fun filterRidesByCity(rides : List<Ride>,city:String) {
        if(city == "city") _stateFilteredRide.value = rides
        else _stateFilteredRide.value = rides.filter { it -> it.city == city }
    }
}
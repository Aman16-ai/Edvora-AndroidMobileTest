package com.example.mobiletest2.data.service

import com.example.mobiletest2.data.model.Ride
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import retrofit2.http.GET

const val BASE_URL = "https://assessment.api.vweb.app/"
interface RideApiInterface {
    @GET("rides")
    suspend fun getAllRides() : Response<List<Ride>>
}

object RideApiService {
    val rideInstance : RideApiInterface
    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        rideInstance = retrofit.create(RideApiInterface::class.java)
    }
}
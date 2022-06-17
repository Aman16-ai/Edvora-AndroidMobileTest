package com.example.mobiletest2.data.repository

import com.example.mobiletest2.data.model.Ride

sealed class ApiResponse<T>(val data : T?=null,val errorMessage:String?=null) {
    class Loading<T> : ApiResponse<T>()
    class Success<T>(rides:T?=null) : ApiResponse<T>(data = rides)
    class Error<T>(error:String) : ApiResponse<T>(errorMessage = error)
}

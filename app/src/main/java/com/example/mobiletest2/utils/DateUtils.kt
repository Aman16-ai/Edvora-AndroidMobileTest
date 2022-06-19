package com.example.mobiletest2.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

fun convertStringToDate(dateStr: String): Date? {
    val dateFormatter = SimpleDateFormat("MM/dd/yy HH:mm aa")
    return dateFormatter.parse(dateStr)
}
fun checkUpcomingDate(rideDate: String): Boolean? {
    val currentDate = Date()
    val formattedRideDate = convertStringToDate(rideDate)
    Log.d("date", "fetchUpcomingRides: ${currentDate} ${formattedRideDate} ${formattedRideDate?.after(currentDate)}")
    return formattedRideDate?.after(currentDate)
}

fun checkPastDate(rideDate: String):Boolean? {
    val currentDate = Date()
    val formattedRideDate = convertStringToDate(rideDate)
    return formattedRideDate?.before(currentDate)
}
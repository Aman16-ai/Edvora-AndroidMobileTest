package com.example.mobiletest2.ui.adapter

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobiletest2.R
import com.example.mobiletest2.data.model.Ride
import com.example.mobiletest2.databinding.RideListLayoutBinding
import com.example.mobiletest2.utils.makeSpannableString

class UpComingRideAdapter(val context:Context) : RecyclerView.Adapter<UpComingRideAdapter.rideViewHolder>() {
    val rides = ArrayList<Ride>()

    inner class rideViewHolder(private val itemBinding: RideListLayoutBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(ride : Ride) {
            itemBinding.apply {
                Glide.with(context).load(ride.map_url).into(mapImg)

                stateTv.text = ride.state

                cityTv.text = ride.city

                val rideIdTxt = "Ride Id : ${ride.id}"
                val rideIdSpannableTxt= makeSpannableString(rideIdTxt,10,rideIdTxt.length)
                rideTv.text = rideIdSpannableTxt

                val originStationTxt = "Origin Station : ${ride.origin_station_code}"
                val originStationSpannableTxt = makeSpannableString(originStationTxt,17,originStationTxt.length)
                originStationTv.text = originStationSpannableTxt

                val stationPathTxt = "Station path : ${ride.station_path.toString()}"
                val stationPathSpannableTxt = makeSpannableString(stationPathTxt,15,stationPathTxt.length)
                stationPathTv.text = stationPathSpannableTxt

                val dateTxt = "Date : ${ride.date}"
                val dateSpannableTxt = makeSpannableString(dateTxt,7,dateTxt.length)
                dateTv.text = dateSpannableTxt

                val distanceTxt = "Distance : ${ride.distance}"
                val distanceSpannableTxt = makeSpannableString(distanceTxt,11,distanceTxt.length)
                distanceTv.text = distanceSpannableTxt
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): rideViewHolder {
        val itemBinding = RideListLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return rideViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: rideViewHolder, position: Int) {
        val ride = rides[position]
        holder.bind(ride)
    }

    fun updateRides(newRides:List<Ride>) {
        rides.clear()
        rides.addAll(newRides)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return rides.size
    }
}
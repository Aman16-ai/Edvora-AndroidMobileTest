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

class NearestRideAdapter(val context:Context) : RecyclerView.Adapter<NearestRideAdapter.rideViewHolder>() {

    val rides = ArrayList<Ride>()

    inner class rideViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        val mapImg = itemView.findViewById<ImageView>(R.id.map_img)
        val stateTv = itemView.findViewById<TextView>(R.id.state_tv)
        val cityTv = itemView.findViewById<TextView>(R.id.city_tv)
        val rideIdTv = itemView.findViewById<TextView>(R.id.ride_tv)
        val originStationTv = itemView.findViewById<TextView>(R.id.origin_station_tv)
        val stationPathTv = itemView.findViewById<TextView>(R.id.station_path_tv)
        val dateTv = itemView.findViewById<TextView>(R.id.date_tv)
        val distanceTv = itemView.findViewById<TextView>(R.id.distance_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): rideViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.ride_list_layout,parent,false)
        return rideViewHolder(view)
    }

    override fun onBindViewHolder(holder: rideViewHolder, position: Int) {
        val ride = rides[position]

        Glide.with(context).load(ride.map_url).into(holder.mapImg)
        holder.stateTv.text = ride.state
        holder.cityTv.text = ride.city
        holder.rideIdTv.text = "Ride Id : ${Html.fromHtml("<font color=#FFFFFF>${ride.id}</font>")}"
        holder.originStationTv.text = "Origin Station : ${Html.fromHtml("<font color=#FFFFFF>${ride.origin_station_code}</font>")}"
        holder.stationPathTv.text = "Station path : ${Html.fromHtml("<font color=#FFFFFF>${ride.station_path.toString()}</font>")}"
        holder.dateTv.text = "Date : ${Html.fromHtml("<font color=#FFFFFF>${ride.date}</font>")}"
        holder.distanceTv.text = "Distance : ${ride.distance}"
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
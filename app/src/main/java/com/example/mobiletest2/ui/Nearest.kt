package com.example.mobiletest2.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobiletest2.R
import com.example.mobiletest2.data.model.Ride
import com.example.mobiletest2.data.repository.ApiResponse
import com.example.mobiletest2.databinding.FragmentNeareastBinding
import com.example.mobiletest2.ui.adapter.NearestRideAdapter
import com.example.mobiletest2.ui.viewModel.NearestRideViewModel


class Nearest : Fragment() {


    private lateinit var recyclerView: RecyclerView
    private var _binding : FragmentNeareastBinding? = null

    private val binding get() = _binding!!
    private lateinit var nearestRideAdapter: NearestRideAdapter

    private  var rideList:List<Ride>?=null

    private val nearestRideViewModel : NearestRideViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNeareastBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        nearestRideAdapter = NearestRideAdapter(requireContext())

        binding.nearestRideRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = nearestRideAdapter
        }

        nearestRideViewModel.stateFliteredRides.observe(viewLifecycleOwner) {
            it?.let {
                nearestRideAdapter.updateRides(it)
            }
        }
        nearestRideViewModel.rides.observe(viewLifecycleOwner) { it ->
            it?.let { it ->
                when(it) {
                        is ApiResponse.Success -> {
                            it.data?.let {
                                rideList = it
                                nearestRideAdapter.updateRides(it)
                            }
                        }
                    is ApiResponse.Error -> {
                        Toast.makeText(context,it.errorMessage,Toast.LENGTH_SHORT).show()
                    }
                    }
                }
            }

        return view
    }


    fun setDataFromActivity(state : String) {
        rideList?.let {
            nearestRideViewModel.filterByState(it,state)
        }
    }

    fun setCityDataFromActivity(city : String) {
        rideList?.let {
            nearestRideViewModel.filterByCity(it,city)
        }
    }

    fun getStateList() : List<String> {
        val stateList = ArrayList<String>()
        rideList?.let {
            for(i in it) {
                stateList.add(i.state)
            }
        }
        return stateList
    }

    fun getCityList() : List<String> {
        val cityList = ArrayList<String>()
        rideList?.let {
            for(i in it) {
                cityList.add(i.city)
            }
        }
        return cityList
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
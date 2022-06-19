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


class NearestFragment : Fragment() {


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

        //setting up recyclerview
        binding.nearestRideRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = nearestRideAdapter
        }

        //observing the stateFilteredRides live data
        nearestRideViewModel.stateFliteredRides.observe(viewLifecycleOwner) {
            it?.let {
                nearestRideAdapter.updateRides(it)
            }
        }

        //observing the nearest ride live data
        nearestRideViewModel.rides.observe(viewLifecycleOwner) { it ->
            it?.let { it ->

                //checking the ApiResponse type and taking action according to it
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


    //method taking the state string from activity to filter the list of rides by selected states
    fun setDataFromActivity(state : String) {
        rideList?.let {
            nearestRideViewModel.filterByState(it,state)
        }
    }

    //method taking the city string from activity to filter the list of rides by selected city
    fun setCityDataFromActivity(city : String) {
        rideList?.let {
            nearestRideViewModel.filterByCity(it,city)
        }
    }

    //passing the list of state of rides to activity
    fun getStateList() : List<String> {
        val stateList = ArrayList<String>()
        rideList?.let {
            for(i in it) {
                if(i.state !in stateList) {
                    stateList.add(i.state)
                }
            }
        }
        return stateList
    }

    //passing the list of city of rides to activity
    fun getCityList() : List<String> {
        val cityList = ArrayList<String>()
        rideList?.let {
            for(i in it) {
               if(i.city !in cityList) {
                   cityList.add(i.city)
               }
            }
        }
        return cityList
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
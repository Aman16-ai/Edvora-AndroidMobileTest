package com.example.mobiletest2.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobiletest2.R
import com.example.mobiletest2.data.repository.ApiResponse
import com.example.mobiletest2.databinding.FragmentUpcomingBinding
import com.example.mobiletest2.ui.adapter.UpComingRideAdapter
import com.example.mobiletest2.ui.viewModel.NearestRideViewModel
import com.example.mobiletest2.ui.viewModel.UpcomingRideViewModel
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*


class Upcoming : Fragment() {


    private var _binding : FragmentUpcomingBinding? = null

    private val binding get() = _binding!!
    private lateinit var upComingRideAdapter: UpComingRideAdapter
    private val upcomingRideViewModel : UpcomingRideViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpcomingBinding.inflate(layoutInflater,container,false)
        val view = binding.root

        upComingRideAdapter = UpComingRideAdapter(requireContext())

        binding.upcomingRideRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = upComingRideAdapter
        }

        upcomingRideViewModel.upComingRides.observe(viewLifecycleOwner) {
            it?.let {
                when(it) {
                    is ApiResponse.Success -> {
                        it.data?.let { it1 -> upComingRideAdapter.updateRides(it1) }
                    }
                    is ApiResponse.Error -> {
                        Toast.makeText(context, it.errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
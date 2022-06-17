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
import com.example.mobiletest2.databinding.FragmentNeareastBinding
import com.example.mobiletest2.databinding.FragmentPreviousRideBinding
import com.example.mobiletest2.ui.adapter.NearestRideAdapter
import com.example.mobiletest2.ui.adapter.PastRideAdapter
import com.example.mobiletest2.ui.viewModel.NearestRideViewModel
import com.example.mobiletest2.ui.viewModel.PastRideViewModel
import com.example.mobiletest2.ui.viewModel.UpcomingRideViewModel
import java.text.SimpleDateFormat
import java.util.*


class PreviousRide : Fragment() {

    private var _binding : FragmentPreviousRideBinding? = null

    private val binding get() = _binding!!
    private lateinit var pastRideAdapter: PastRideAdapter
    private val pastRideViewModel : PastRideViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPreviousRideBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        pastRideAdapter = PastRideAdapter(requireContext())

        binding.rideRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = pastRideAdapter
        }

        pastRideViewModel.pastRide.observe(viewLifecycleOwner) { it ->
            it?.let { it ->
                when(it) {
                    is ApiResponse.Success -> {
                        it.data?.let {
                            pastRideAdapter.updateRides(it)
                        }
                    }
                    is ApiResponse.Error -> {
                        Toast.makeText(context,it.errorMessage, Toast.LENGTH_SHORT).show()
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
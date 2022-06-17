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
import com.example.mobiletest2.data.repository.ApiResponse
import com.example.mobiletest2.databinding.FragmentNeareastBinding
import com.example.mobiletest2.ui.adapter.NearestRideAdapter
import com.example.mobiletest2.ui.viewModel.NearestRideViewModel


class Nearest : Fragment() {


    private lateinit var recyclerView: RecyclerView
    private var _binding : FragmentNeareastBinding? = null

    private val binding get() = _binding!!
    private lateinit var nearestRideAdapter: NearestRideAdapter
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

        nearestRideViewModel.rides.observe(viewLifecycleOwner) { it ->
            it?.let { it ->
                when(it) {
                        is ApiResponse.Success -> {
                            it.data?.let {
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


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
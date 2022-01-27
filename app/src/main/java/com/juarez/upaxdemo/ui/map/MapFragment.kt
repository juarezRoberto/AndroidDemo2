package com.juarez.upaxdemo.ui.map

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.juarez.upaxdemo.R
import com.juarez.upaxdemo.databinding.FragmentMapBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback {
    private val viewModel: LocationsViewModel by activityViewModels()
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private var map: GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        createMapFragment()
        shouldShowErrorOptions()
        viewModel.locations.observe(viewLifecycleOwner, {
            it.forEach { location ->
                map?.addMarker(MarkerOptions()
                    .position(LatLng(location.latitude.toDouble(), location.longitude.toDouble()))
                )
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, {
            binding.progressBarMap.isVisible = it
        })
        viewModel.error.observe(viewLifecycleOwner, {
            if (!it.isNullOrEmpty()) {
                shouldShowErrorOptions(true)
                binding.txtError.text = it
            }
        })

        return binding.root
    }

    private fun shouldShowErrorOptions(visible: Boolean = false) {
        binding.txtError.isVisible = visible
    }

    private fun createMapFragment() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        viewModel.getLocations()
        val orizaba = LatLng(18.848090149308483, -97.09952330501545)
        map?.animateCamera(
            CameraUpdateFactory.newLatLngZoom(orizaba, 13f), 2000, null
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
package com.juarez.upaxdemo.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.juarez.upaxdemo.R
import com.juarez.upaxdemo.data.models.Location
import com.juarez.upaxdemo.databinding.FragmentMapBinding
import com.juarez.upaxdemo.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback {
    private val viewModel: LocationsViewModel by activityViewModels()
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var map: GoogleMap? = null

    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        binding.btnSaveLocation.isVisible = false
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        createMapFragment()
        shouldShowErrorOptions()

        binding.btnSaveLocation.setOnClickListener {
            fusedLocationClient.lastLocation.addOnSuccessListener {
                if (it == null) {
                    toast("Location could not be found")
                } else {
                    val newLocation = Location(it.latitude.toString(), it.longitude.toString())
                    viewModel.saveLocation(newLocation)
                }
            }
        }
        
        lifecycleScope.launchWhenStarted {
            viewModel.locationState.collectLatest {
                when (it) {
                    is LocationsState.Error -> {
                        if (it.message.isNotEmpty()) {
                            shouldShowErrorOptions(true)
                            binding.txtError.text = it.message
                        }
                    }
                    is LocationsState.Loading -> {
                        binding.progressBarMap.isVisible = it.isLoading
                        binding.btnSaveLocation.isVisible = !it.isLoading
                    }
                    is LocationsState.Success -> {
                        it.data.forEach { location ->
                            map?.addMarker(MarkerOptions()
                                .position(LatLng(location.latitude.toDouble(),
                                    location.longitude.toDouble()))
                            )
                        }
                    }
                    else -> Unit
                }
            }
        }
        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map?.uiSettings?.isZoomControlsEnabled = true
        viewModel.getLocations()
        val cdmx = LatLng(19.42847, -99.12766)
        map?.animateCamera(
            CameraUpdateFactory.newLatLngZoom(cdmx, 5f), 2000, null
        )
        requestPermission(Manifest.permission.ACCESS_COARSE_LOCATION) { result ->
            when (result) {
                PermissionResult.GRANTED -> enableLocation()
                PermissionResult.DENIED -> {
                    requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                }
                PermissionResult.RATIONALE ->
                    showRequestPermissionRationaleAlert(Constants.STORAGE_PERMISSION_REQUIRED) {
                        when (it) {
                            AlertAction.POSITIVE -> {
                                requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                            }
                            AlertAction.NEGATIVE -> Unit
                        }
                    }
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) enableLocation()
            else toast(Constants.LOCATION_PERMISSION_DENIED)
        }

    @SuppressLint("MissingPermission")
    private fun enableLocation() {
        map?.isMyLocationEnabled = true
        if (!isLocationEnabled()) showGPSRequiredMessage()
        else binding.btnSaveLocation.isVisible = true
    }

    private fun showGPSRequiredMessage() {
        showAlert(message = Constants.GPS_REQUIRED) {
            when (it) {
                AlertAction.POSITIVE -> {
                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
                AlertAction.NEGATIVE -> binding.btnSaveLocation.isVisible = false
            }
        }
    }

    private fun shouldShowErrorOptions(visible: Boolean = false) {
        binding.txtError.isVisible = visible
    }

    private fun createMapFragment() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onResume() {
        super.onResume()
        if (!isLocationEnabled()) binding.btnSaveLocation.isVisible = false
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
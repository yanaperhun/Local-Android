package com.local.app.ui.fragments.event.create

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FetchPlaceResponse
import com.google.android.libraries.places.api.net.PlacesClient
import com.local.app.R
import com.local.app.data.PlaceData
import com.local.app.data.event.create.EventAddress
import com.local.app.databinding.FragmentCreateEventStepPlaceBinding
import com.local.app.ui.BaseFragment
import com.local.app.ui.adapters.place.PlaceArrayAdapter
import com.local.app.utils.LocationUtils
import com.local.app.utils.LocationUtils.Companion.EMPTY_LATLNG

class CreateEventStepPlaceFragment : BaseCreateEventFragment<FragmentCreateEventStepPlaceBinding>(),
    OnMapReadyCallback {

    private lateinit var mPlacesClient: PlacesClient
    private lateinit var mPlaceArrayAdapter: PlaceArrayAdapter
    private lateinit var googleMap: GoogleMap

    override fun setBinding(inflater: LayoutInflater) {
        binding = FragmentCreateEventStepPlaceBinding.inflate(inflater)
    }

    override fun initUI(state: Bundle?) {
        super.initUI(state)
        binding.mapView.onCreate(state)

        mPlacesClient = Places.createClient(requireContext())
        binding.etInputPrice.threshold = 3

        mPlaceArrayAdapter = PlaceArrayAdapter(requireContext(), R.layout.item_place, mPlacesClient)
        binding.etInputPrice.setAdapter(mPlaceArrayAdapter)
        binding.etInputPrice.onItemClickListener = onPlacePredictClick
        binding.etInputPrice.setText(viewModel.eventBuilder().eventAddress?.address)

        binding.btnClear.setOnClickListener { binding.etInputPrice.setText("") }

    }

    private val onPlacePredictClick = AdapterView.OnItemClickListener { parent, _, position, _ ->
        log("On place predict click")
        val place = parent.getItemAtPosition(position) as PlaceData

        if (place.placeId == null) {
            setPlace(place.fullText, EMPTY_LATLNG)
            return@OnItemClickListener
        }
        enableCameraPosListener(false)
        val placeFields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
        val request = FetchPlaceRequest.newInstance(place.placeId!!, placeFields)
        mPlacesClient
            .fetchPlace(request)
            .addOnSuccessListener { response: FetchPlaceResponse ->
                val place = response.place
                log("Place found: ${place.name}")
                if (place.address.isNullOrEmpty()) {
                    return@addOnSuccessListener
                }
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(place.latLng))
                setPlace(place.address!!, place.latLng ?: EMPTY_LATLNG)
            }
            .addOnFailureListener { exception: Exception ->
                if (exception is ApiException) {
                    logError("Place not found: ${exception.message}")
                    val statusCode = exception.statusCode
                    TODO("Handle error with given status code")
                }
            }
            .addOnCompleteListener {
                Handler().postDelayed({ enableCameraPosListener(true) }, 1000L)
            }
    }

    private val cameraMovingListener = GoogleMap.OnCameraIdleListener {
        log("Call OnCameraIdleListener")
        val targetLatLng = googleMap.cameraPosition.target
        log(LocationUtils.getPlaceFormattedByLatLng(targetLatLng, requireContext()))
        binding.etInputPrice.isEnabled = false

        val formattedPlace = LocationUtils.getPlaceFormattedByLatLng(targetLatLng, requireContext())
        setPlace(formattedPlace, targetLatLng)
        binding.etInputPrice.isEnabled = true
    }

    private fun setPlace(address: String, latLng: LatLng) {
        binding.etInputPrice.setText(address)
        binding.etInputPrice.setSelection(binding.etInputPrice.length())
        hideKeyboard()
        viewModel.eventBuilder().eventAddress = EventAddress.build(address, latLng)
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        viewModel.eventBuilder().eventAddress?.let {
            binding.etInputPrice.setText(it.name)
        }

        binding.mapView.getMapAsync(this)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onMapReady(map: GoogleMap) {
        log("on map ready")
        googleMap = map
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(15f))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(LocationUtils.defaultLocation()))

        googleMap.uiSettings.isZoomControlsEnabled = true
        binding.mapView.visibility = View.VISIBLE

        googleMap.setOnCameraIdleListener(cameraMovingListener)
    }

    private fun enableCameraPosListener(isEnable: Boolean) {
        log("enableCameraPosListener $isEnable")
        if (isEnable) {
            googleMap.setOnCameraIdleListener(cameraMovingListener)
        } else {
            googleMap.setOnCameraIdleListener(null)
        }
    }

    override fun getNextFragment(): BaseFragment {
        return CreateEventStepPlayListFragment()
    }

    override fun onValidate(): Boolean {
        return !binding.etInputPrice.text.isNullOrEmpty() && viewModel.eventBuilder().eventAddress != null
    }

    override fun getValidateMessage(): String {
        return getString(R.string.error_validate_step_4)
    }

    override fun onNext() {

        val etText = binding.etInputPrice.text.toString()
        if (etText != viewModel.eventBuilder().eventAddress?.address) {
            viewModel.eventBuilder().eventAddress = EventAddress.build(etText, EMPTY_LATLNG)
        }
    }

}
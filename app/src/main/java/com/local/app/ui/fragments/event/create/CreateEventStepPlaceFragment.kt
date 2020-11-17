package com.local.app.ui.fragments.event.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FetchPlaceResponse
import com.google.android.libraries.places.api.net.PlacesClient
import com.local.app.R
import com.local.app.data.PlaceData
import com.local.app.databinding.FragmentCreateEventStepPlaceBinding
import com.local.app.ui.BaseFragment
import com.local.app.ui.adapters.place.PlaceArrayAdapter
import com.local.app.utils.LocationUtils

class CreateEventStepPlaceFragment : BaseCreateEventFragment<FragmentCreateEventStepPlaceBinding>(),
    OnMapReadyCallback {
    private lateinit var mPlacesClient: PlacesClient
    private lateinit var mPlaceArrayAdapter: PlaceArrayAdapter
    private lateinit var googleMap: GoogleMap
    val AUTOCOMPLETE_REQUEST_CODE = 1
    override fun setBinding(inflater: LayoutInflater) {
        binding = FragmentCreateEventStepPlaceBinding.inflate(inflater)
    }

    override fun initUI(state: Bundle?) {
        super.initUI(state)
        binding.mapView.onCreate(state)

        mPlacesClient = Places.createClient(requireContext())
        binding.etInputPrice.setThreshold(3)

        //autoCompleteTextView.setOnItemClickListener(mAutocompleteClickListener);

        //autoCompleteTextView.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = PlaceArrayAdapter(requireContext(), R.layout.item_place, mPlacesClient)
        binding.etInputPrice.setAdapter(mPlaceArrayAdapter)
        binding.etInputPrice.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                val place = parent.getItemAtPosition(position) as PlaceData
                binding.etInputPrice.apply {
                    setText(place.fullText)
                    setSelection(binding.etInputPrice.length())
                    hideKeyboard()
                    place.placeId?.let { showMarkerAtPlace(it) }

                }
            }
    }

    private fun showMarkerAtPlace(placeId: String) {
        // Specify the fields to return.
        val placeFields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)

        // Construct a request object, passing the place ID and fields array.
        val request = FetchPlaceRequest.newInstance(placeId, placeFields)

        mPlacesClient
            .fetchPlace(request)
            .addOnSuccessListener { response: FetchPlaceResponse ->
                val place = response.place
                log("Place found: ${place.name}")
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(place.latLng))
            }
            .addOnFailureListener { exception: Exception ->
                if (exception is ApiException) {
                    logError("Place not found: ${exception.message}")
                    val statusCode = exception.statusCode
                    TODO("Handle error with given status code")
                }
            }

    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        viewModel.eventBuilder().address?.let {
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

        googleMap.setOnCameraIdleListener {
            val target = googleMap.cameraPosition.target
            log(LocationUtils.getPlaceFormattedByLatLng(target, requireContext()))
            binding.etInputPrice.setText(
                LocationUtils.getPlaceFormattedByLatLng(target, requireContext()))
        }
    }

    private fun enableCameraPosListener (isEnable : Boolean) {

    }

    private fun addMarker(latLng: LatLng) {
        val markerOpt = MarkerOptions()
            .position(latLng)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location))
            .draggable(true)
        googleMap.addMarker(markerOpt)
    }

    override fun getNextFragment(): BaseFragment {
        return CreateEventStepPlayListFragment()
    }

    override fun onNext() {

    }

    override fun onValidate(): Boolean {
        return !binding.etInputPrice.text.isNullOrEmpty() && viewModel.eventBuilder().address != null
    }

    override fun getValidateMessage(): String {
        return getString(R.string.error_validate_step_4)
    }

}
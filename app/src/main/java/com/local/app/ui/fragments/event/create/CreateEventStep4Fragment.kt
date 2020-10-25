package com.local.app.ui.fragments.event.create

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.local.app.R
import com.local.app.data.event.create.EventAddress
import com.local.app.databinding.FragmentCreateEventStep4Binding
import com.local.app.ui.BaseFragment
import java.util.*

class CreateEventStep4Fragment : BaseCreateEventFragment<FragmentCreateEventStep4Binding>() {
    val AUTOCOMPLETE_REQUEST_CODE = 1
    override fun setBinding(inflater: LayoutInflater) {
        binding = FragmentCreateEventStep4Binding.inflate(inflater)
    }

    override fun initUI() {
        super.initUI()
        binding.btnAddress.setOnClickListener {

            // Set the fields to specify which types of place data to
            // return after the user has made a selection.

            // Set the fields to specify which types of place data to
            // return after the user has made a selection.
            val fields: List<Place.Field> = Arrays.asList(Place.Field.ID, Place.Field.NAME)

            // Start the autocomplete intent.

            // Start the autocomplete intent.
            val intent = Autocomplete
                .IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .setCountry("RU")
                .build(requireActivity())
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)
                        place.name?.let {
                            val address = EventAddress(it, it, place.latLng?.latitude ?: 0.0,
                                                       place.latLng?.longitude ?: 0.0, 0)
                            viewModel.eventBuilder().address = address
                            binding.btnAddress.text = it
                        }


                        log("Place: ${place.name}, ${place.id}, ${place.address}")
                    }
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    // TODO: Handle the error.
                    data?.let {
                        val status = Autocomplete.getStatusFromIntent(data)
                        log(status.statusMessage ?: "Unknown status")
                    }
                }
                Activity.RESULT_CANCELED -> {
                    // The user canceled the operation.
                }
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onResume() {
        super.onResume()
        viewModel.eventBuilder().address?.let {
            binding.btnAddress.text = it.name
        }
    }

    override fun getNextFragment(): BaseFragment {
        return CreateEventStep5Fragment()
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
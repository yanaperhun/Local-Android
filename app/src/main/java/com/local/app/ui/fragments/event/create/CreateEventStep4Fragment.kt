package com.local.app.ui.fragments.event.create

import android.view.LayoutInflater
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.local.app.databinding.FragmentCreateEventStep4Binding
import com.local.app.ui.BaseFragment
import java.util.*

class CreateEventStep4Fragment : BaseCreateEventFragment<FragmentCreateEventStep4Binding>() {

    override fun setBinding(inflater: LayoutInflater) {
        binding = FragmentCreateEventStep4Binding.inflate(inflater)
    }

    override fun initUI() {
        super.initUI()
        binding.btnAddress.setOnClickListener {
            val AUTOCOMPLETE_REQUEST_CODE = 1

            // Set the fields to specify which types of place data to
            // return after the user has made a selection.

            // Set the fields to specify which types of place data to
            // return after the user has made a selection.
            val fields: List<Place.Field> = Arrays.asList(Place.Field.ID, Place.Field.NAME)

            // Start the autocomplete intent.

            // Start the autocomplete intent.
            val intent = Autocomplete
                .IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .build(requireActivity())
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
        }

    }

    override fun getNextFragment(): BaseFragment {
        return CreateEventStep5Fragment()
    }

    override fun onNext() {

    }

}
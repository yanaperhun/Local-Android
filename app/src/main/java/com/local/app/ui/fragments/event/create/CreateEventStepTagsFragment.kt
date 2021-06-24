package com.local.app.ui.fragments.event.create

import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.local.app.R
import com.local.app.databinding.FragmentCreateEventStepTagsBinding
import com.local.app.ui.BaseFragment
import com.local.app.utils.SimpleTextWatcher
import com.local.app.utils.ViewUtils


class CreateEventStepTagsFragment : BaseCreateEventFragment<FragmentCreateEventStepTagsBinding>() {
    private val tags = ArrayList<String>()

    override fun setBinding(inflater: LayoutInflater) {
        binding = FragmentCreateEventStepTagsBinding.inflate(inflater)
    }

    override fun initUI(state: Bundle?) {
        super.initUI(state)
    }

    override fun onResume() {
        super.onResume()

        focusET(binding.etInputTags)
        val filter = InputFilter { source, start, end, dest, dstart, dend ->
            for (i in start until end) {
                if (Character.isWhitespace(source[i])) {
                    return@InputFilter ""
                }
            }
            binding.btnAddTag.isEnabled = source.isNotEmpty()
            null
        }
        binding.etInputTags.filters = arrayOf(filter)
        buildTagsView(binding.chipsGroup, tags)
        binding.btnAddTag.setOnClickListener {
            tags.add(binding.etInputTags.text.toString())
            buildTagsView(binding.chipsGroup, tags)
            binding.etInputTags.text.clear()
        }
    }

    fun buildTagsView(chipGroup: ChipGroup, _tags: List<String>?) {
        if (_tags.isNullOrEmpty()) return
        chipGroup.removeAllViews()
        for (i in _tags.indices) {
            val context = chipGroup.context
            val chipView = Chip(context)
            chipView.text = _tags[i]
            chipView.setChipStrokeColorResource(R.color.colorGreen)
            chipView.chipStrokeWidth = ViewUtils.dpToPx(2)
            chipView.closeIcon = resources.getDrawable(R.drawable.ic_cross_grey_16dp)

            chipView.closeIconEndPadding = ViewUtils.dpToPx(4)
            chipView.closeIconStartPadding = ViewUtils.dpToPx(4)
            chipView.setCloseIconVisible(true)
            chipView.setOnCloseIconClickListener {
                chipGroup.removeView(it)
                tags.remove((it as Chip).text)
            }
//                chipView.setChipMinHeightResource(R.dimen.chip_touch_min_size)
            chipGroup.addView(chipView)

        }
    }

    override fun onPause() {
        super.onPause()
        binding.etInputTags.removeTextChangedListener(textListener)
    }

    override fun getNextFragment(): BaseFragment {
        return CreateEventStepDateFragment()
    }

    override fun onValidate(): Boolean {
        if (tags.isNotEmpty()) return true
        binding.etInputTags.showError(true, getValidateMessage())
        return false
    }

    override fun getValidateMessage(): String {
        return getString(R.string.error_validate_step_tags)
    }

    override fun onNext() {
        viewModel.eventBuilder().tags = tags
        super.onNext()
    }

    private val textListener = object : SimpleTextWatcher() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s.isNullOrEmpty()) return
            //            viewModel.eventBuilder().description = binding.etInputDescription.text.toString()
        }
    }

}
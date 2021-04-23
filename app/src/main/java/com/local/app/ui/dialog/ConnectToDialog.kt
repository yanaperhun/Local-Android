package com.local.app.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.local.app.R
import com.local.app.data.event.Event
import com.local.app.databinding.DialogCoonectToBinding
import com.local.app.utils.ContactsManager

class ConnectToDialog : BottomSheetDialogFragment() {
    lateinit var binding: DialogCoonectToBinding
    private val contactsManager = ContactsManager()

    companion object {
        const val ARGS_EVENT = "event"
        fun create(event: Event): ConnectToDialog {
            val args = Bundle()
            args.putParcelable(ARGS_EVENT, event)
            val fragment = ConnectToDialog()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = DialogCoonectToBinding.inflate(inflater)
        val event = arguments?.getParcelable<Event>(ARGS_EVENT)
        event?.let {
            bindUI(it)

        }
        return binding.root
    }

    private fun bindUI(event: Event) {
        binding.btnInstagram.isVisible = !event.instagram.isNullOrEmpty()
        binding.btnPhone.isVisible = !event.phone.isNullOrEmpty()
        binding.btnWhatsup.isVisible = !event.whatsapp.isNullOrEmpty()
        binding.btnTelegram.isVisible = !event.telegram.isNullOrEmpty()

        event.instagram?.let { contact ->
            binding.btnInstagram.setOnClickListener {
                contactsManager.openInstagram(contact, requireActivity())
                dismiss()
            }
        }

        event.phone?.let { contact ->
            binding.btnPhone.setOnClickListener {
                contactsManager.callPhone(contact, requireActivity())
                dismiss()
            }
        }
        event.whatsapp?.let { contact ->
            binding.btnWhatsup.setOnClickListener {
                contactsManager.openWhatsApp(contact, requireActivity())
                dismiss()
            }
        }
        event.telegram?.let { contact ->
            binding.btnTelegram.setOnClickListener {
                contactsManager.openTelegram(contact, requireActivity())
                dismiss()
            }
        }

    }

}

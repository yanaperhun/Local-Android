package com.local.app.ui.fragments.profile

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.res.ResourcesCompat
import androidx.exifinterface.media.ExifInterface
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.viewModels
import com.local.app.BindableFragment
import com.local.app.R
import com.local.app.data.Profile
import com.local.app.databinding.FragmentProfileBinding
import com.local.app.presentation.viewmodel.event.create.LoadProfileState
import com.local.app.presentation.viewmodel.profile.ProfileViewModel
import com.local.app.ui.activities.event.CreateEventActivity
import com.local.app.ui.fragments.feed.profile.LikedEventsFragment
import com.local.app.ui.fragments.feed.profile.MyEventsFragment
import com.local.app.ui.fragments.profile.settings.ProfileSettingsFragment
import gun0912.tedimagepicker.builder.TedImagePicker
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class ProfileFragment : BindableFragment<FragmentProfileBinding>() {
    val viewModel: ProfileViewModel by viewModels()

    override fun setBinding(inflater: LayoutInflater) {
        binding = FragmentProfileBinding.inflate(inflater)
    }

    override fun initUI(state: Bundle?) {
        super.initUI(state)
        binding.motionLayout.addTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {

            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                binding.swipe.isEnabled = p1 == R.id.expanded
                if (p1 == R.id.expanded) {
                    Timber.d("===> expanded")

                }
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
            }

        })
        binding.btnBack.setOnClickListener { backStep() }
        binding.btnCreateEvent.setOnClickListener {
            startActivity(Intent(requireContext(), CreateEventActivity::class.java))
        }

        binding.swipe.setOnRefreshListener { viewModel.loadProfile() }
        binding.btnMenu.setOnClickListener { showMenu(it) }
        binding.btnAddPhoto.setOnClickListener { chooseImage() }
        initViewPager()
        subscribeToViewModel()
    }

    private fun chooseImage() {
        TedImagePicker
            .with(requireContext())
            .start { result ->

                val inputStream = requireContext().contentResolver.openInputStream(result)
                val fileFromInputStream = createFile(requireContext(), ".jpg").absolutePath
                val bitmap = BitmapFactory.decodeStream(inputStream)
                try {
                    FileOutputStream(fileFromInputStream).use { output ->
                        val buffer = ByteArray(4 * 1024) // or other buffer size
                        var read: Int = 0
                        while (inputStream?.read(buffer).also {if (it != null) read = it } != -1) {
                            output.write(buffer, 0, read)
                        }
                        output.flush()
                    }
                } finally {
                    inputStream?.close()
                }

                val oldExif = if (inputStream != null) ExifInterface(inputStream) else null

                val oldExifOrientation =
                    oldExif?.getAttribute(
                        ExifInterface.TAG_ORIENTATION
                    ) ?:""

                Timber.d("exifOrientation $oldExifOrientation")
                val newFilePath = createFile(requireContext(), ".jpg").absolutePath


                if (bitmap != null) {
                    compress(newFilePath, bitmap)

                    val newExif = ExifInterface(newFilePath)
                    newExif.setAttribute(
                        ExifInterface.TAG_ORIENTATION,
                        oldExifOrientation)
                    newExif.saveAttributes()
                    viewModel.uploadPhoto(newFilePath)
                    showRoundImage(binding.ivAvatar, newFilePath)
                }

            }
    }

    private fun createFile(context: Context, extension: String): File {
        val sdf = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS", Locale.US)
        return File(context.filesDir, "IMG_${sdf.format(Date())}$extension")
            .apply { createNewFile() }
    }

    private fun compress(outputFile: String, inputFile: Bitmap) {
        try {

            val out = FileOutputStream(outputFile)
            val result = inputFile.compress(Bitmap.CompressFormat.JPEG, 50, out) //100-best quality
            log("result : $result")
            out.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun showMenu(view: View) {
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.inflate(R.menu.menu_profile)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.btn_settings -> {
                    showSettingsFragment()
                    true
                }
                R.id.btn_logout -> {
                    logout()
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    private fun showSettingsFragment() {
        showFragment(ProfileSettingsFragment(), true)
    }

    private fun logout() {
        viewModel.logout()
        backStep()
    }

    override fun subscribeToViewModel() {
        super.subscribeToViewModel()
        viewModel.loadProfileState.observe(this, {
            when (it) {
                is LoadProfileState.ERROR -> {
                    binding.swipe.isRefreshing = false
                    showErrorAlert(it.error.message)
                }
                is LoadProfileState.LOADING -> {

                }
                is LoadProfileState.SUCCESS -> {
                    binding.swipe.isRefreshing = false
                    initUserData(it.profile)
                }
            }
        })
        viewModel.loadProfile()
    }

    private fun initUserData(profile: Profile) {
        profile.getProfileImage()?.url?.getDefault()?.let {
            showRoundImage(binding.ivAvatar, it)
        }
        binding.tvName.text = profile.getNick()

        var picture = if (profile.instagram.isNullOrEmpty())
            R.drawable.ic_instagram_bw
        else
            R.drawable.ic_instagram
        binding.btnInstagram.setBackground(
            ResourcesCompat.getDrawable(
                resources,
                picture,
                null
            )
        )

        picture = if (profile.phone.isNullOrEmpty())
            R.drawable.ic_phone_bw
        else
            R.drawable.ic_phone
        binding.btnPhone.setBackground(
            ResourcesCompat.getDrawable(
                resources,
                picture,
                null
            )
        )

        picture = if (profile.telegram.isNullOrEmpty())
            R.drawable.ic_telegram_bw
        else
            R.drawable.ic_telegram
        binding.btnTelegram.setBackground(
            ResourcesCompat.getDrawable(
                resources,
                picture,
                null
            )
        )

        picture = if (profile.whatsApp.isNullOrEmpty())
            R.drawable.ic_whatsup_bw
        else
            R.drawable.ic_whatsup
        binding.btnWhatsup.setBackground(
            ResourcesCompat.getDrawable(
                resources,
                picture,
                null
            )
        )
        initTabs()
    }

    private fun initTabs() {
        binding.tabs.setupWithViewPager(binding.viewPager)
        binding.viewPager.adapter = object :
            FragmentPagerAdapter(childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

            override fun getItem(position: Int): Fragment {
                log("Create $position fragment")
                return when (position) {
                    0 -> LikedEventsFragment()
                    1 -> MyEventsFragment()
                    else -> LikedEventsFragment()
                }
            }

            override fun getCount(): Int = 2

            override fun getPageTitle(position: Int): CharSequence? {
                return if (position == 0) getString(R.string.liked) else getString(R.string.created)
            }
        }
    }

    private fun initViewPager() {

    }
}
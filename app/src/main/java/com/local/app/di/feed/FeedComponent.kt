package com.local.app.di.feed

import com.local.app.presentation.viewmodel.PhotoViewerViewModel
import com.local.app.ui.event.EventFragment
import com.local.app.ui.photo.PhotoViewerFragment
import dagger.Subcomponent

@PerFeed
@Subcomponent(modules = [FeedModule::class])
interface FeedComponent {
    fun inject(photoViewerFragment: PhotoViewerFragment)
    fun inject(photoViewerFragment: PhotoViewerViewModel)
    fun inject(eventFragment: EventFragment)

}
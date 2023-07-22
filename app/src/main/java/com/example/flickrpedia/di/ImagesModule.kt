package com.example.flickrpedia.di

import com.example.flickrpedia.data.repo.ImagesImpl
import com.example.flickrpedia.domain.repo.ImagesRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ImagesModule {
    @Binds
    abstract fun bindImages(
        imagesImpl: ImagesImpl
    ): ImagesRepo
}
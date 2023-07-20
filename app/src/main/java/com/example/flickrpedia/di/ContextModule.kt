package com.example.flickrpedia.di

import android.content.Context
import com.example.flickrpedia.MyApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ContextModule {

    @Provides
    @Singleton
    fun provideContext(application: MyApp): Context {
        return application.applicationContext
    }
}
package com.example.flickrpedia.di

import androidx.lifecycle.ViewModel
import com.example.flickrpedia.data.repo.LoginUserImpl
import com.example.flickrpedia.data.repo.RegisterUserImpl
import com.example.flickrpedia.domain.repo.LoginUser
import com.example.flickrpedia.domain.repo.RegisterUser
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class LoginUserModule {
    @Binds
    abstract fun bindLoginUser(
        loginUserImpl: LoginUserImpl
    ): LoginUser
}
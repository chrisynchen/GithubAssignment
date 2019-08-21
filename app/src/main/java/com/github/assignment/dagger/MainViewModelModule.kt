package com.github.assignment.dagger

import com.github.assignment.repository.UserRepository
import com.github.assignment.viewmodel.MainViewModel
import dagger.Module
import dagger.Provides

@Module
class MainViewModelModule {
    @Provides
    fun provideMainViewModel(userRepository: UserRepository): MainViewModel = MainViewModel(userRepository)
}
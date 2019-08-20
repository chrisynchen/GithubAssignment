package com.github.assignment.dagger

import com.github.assignment.activity.MainActivity
import com.github.assignment.network.NetworkModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by chris chen on 2019-08-07.
 */

@Singleton
@Component(modules = [MainViewModelModule::class, NetworkModule::class])
interface MainComponent {
    fun inject(mainActivity: MainActivity)

    @Component.Builder
    interface Builder {
        fun build(): MainComponent
    }
}
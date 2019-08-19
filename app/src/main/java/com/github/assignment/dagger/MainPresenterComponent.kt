package com.github.assignment.dagger

import com.github.assignment.activity.MainActivity
import com.github.assignment.contract.MainView
import com.github.assignment.network.NetworkModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Created by chris chen on 2019-08-07.
 */

@Singleton
@Component(modules = [MainPresenterModule::class, NetworkModule::class])
interface MainPresenterComponent {
    fun inject(mainActivity: MainActivity)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun view(mainView: MainView): Builder

        fun build(): MainPresenterComponent
    }
}
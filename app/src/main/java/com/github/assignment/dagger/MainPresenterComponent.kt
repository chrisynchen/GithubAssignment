package com.github.assignment.dagger

import com.github.assignment.activity.MainActivity
import com.github.assignment.contract.MainView
import com.github.assignment.network.requests.FetchUsersRequest
import dagger.BindsInstance
import dagger.Component

/**
 * Created by chris chen on 2019-08-07.
 */

@Component(modules = [MainPresenterModule::class])
interface MainPresenterComponent {
    fun inject(mainActivity: MainActivity)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun view(mainView: MainView): Builder

        @BindsInstance
        fun userRequest(request: FetchUsersRequest): Builder

        fun build(): MainPresenterComponent
    }
}
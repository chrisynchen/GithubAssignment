package com.github.assignment.dagger

import com.github.assignment.contract.MainPresenter
import com.github.assignment.presenter.MainPresenterImpl
import dagger.Binds
import dagger.Module

/**
 * Created by chris chen on 2019-08-07.
 */

@Module
abstract class MainPresenterModule {
    @Binds
    abstract fun provideMainPresenter(presenter: MainPresenterImpl): MainPresenter
}
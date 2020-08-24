package com.test.news.presentation.main

import moxy.MvpPresenter
import javax.inject.Inject

class MainPresenter @Inject constructor() : MvpPresenter<MainView>() {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showSplashFragment()
    }
}
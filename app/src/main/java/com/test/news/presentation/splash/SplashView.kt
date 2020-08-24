package com.test.news.presentation.splash

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface SplashView: MvpView {
    @AddToEndSingle
    fun openNews()
}
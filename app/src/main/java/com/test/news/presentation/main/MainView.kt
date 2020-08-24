package com.test.news.presentation.main

import androidx.fragment.app.Fragment
import com.test.news.presentation.news.NewsFragment
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution


interface MainView : MvpView {

    @OneExecution
    fun showSplashFragment()

    @OneExecution
    fun showNewsFragment()

    @OneExecution
    fun showDetailsFragment(url: String?)

}

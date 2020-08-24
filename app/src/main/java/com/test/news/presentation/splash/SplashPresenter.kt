package com.test.news.presentation.splash

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moxy.MvpPresenter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SplashPresenter @Inject constructor() : MvpPresenter<SplashView>() {

    private val subscriptions = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        val subscription = Single.timer(10, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _->
                viewState.openNews()
            }
        subscriptions.add(subscription)
    }

    override fun onDestroy() {
        subscriptions.clear()
    }
}
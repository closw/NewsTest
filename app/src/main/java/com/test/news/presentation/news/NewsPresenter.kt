package com.test.news.presentation.news

import com.test.news.domain.NewsInteractor
import io.reactivex.disposables.CompositeDisposable
import moxy.MvpPresenter
import javax.inject.Inject

class NewsPresenter @Inject constructor (var newsInteractor: NewsInteractor):
    MvpPresenter<NewsView>() {

    private val subscriptions = CompositeDisposable()
    var actualPage: Int = 1 //минимальный индекс страницы 1


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setNewsList()
        getNextPage(actualPage)
    }
     fun getNextPage(numOfPage: Int) {
        val subscription = newsInteractor.getNewsList(numOfPage)
            .subscribe({ news ->
                if (news.isNullOrEmpty()) {
                    viewState.showNetworkError()
                } else {
                    viewState.showNews(news)
                    actualPage++
                }
            }, { viewState.showError() })
        subscriptions.add(subscription)
    }

    override fun onDestroy() {
        subscriptions.clear()
    }

}
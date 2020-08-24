package com.test.news.presentation.news

import com.test.news.data.db.entities.ArticlesDbModel
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd
import moxy.viewstate.strategy.alias.OneExecution

interface NewsView : MvpView {
    @AddToEnd
    fun showError()

    @AddToEnd
    fun showNetworkError()

    @AddToEnd
    fun showNews(news: List<ArticlesDbModel>)

    @OneExecution
    fun setNewsList()

}

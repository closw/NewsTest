package com.test.news.domain

import android.accounts.NetworkErrorException
import com.test.news.data.db.NewsDatabase
import com.test.news.data.db.entities.ArticlesDbModel
import com.test.news.data.network.NewsApiService
import com.test.news.data.db.converters.fromApiArticlesToDb
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException
import javax.inject.Inject

class NewsInteractor @Inject constructor(val database: NewsDatabase?, val api: NewsApiService) : NewsRepository {

    override fun getNewsList(page: Int) : Single<List<ArticlesDbModel>> =
        api.getNewsList(page)
            .flatMap { fromApiArticlesToDb(it.articles) }
            .flatMap { list ->
                list.forEach{
                    database?.newsDao()?.insertNew(it)
                }
                return@flatMap Single.just(list)
            }
            .onErrorResumeNext { throwable ->
                if (throwable is NetworkErrorException || throwable is UnknownHostException) {
                    if(database?.newsDao()?.getCount()!! >= 20 * page) { //если сохранено в БД больше, чем отображено на экране
                        return@onErrorResumeNext Single.just(database.newsDao().getNews())
                    } else {
                        return@onErrorResumeNext Single.error(throwable)
                    }
                } else {
                    return@onErrorResumeNext Single.error(throwable)
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

}
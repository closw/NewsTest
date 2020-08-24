package com.test.news.domain

import com.test.news.data.db.entities.ArticlesDbModel
import io.reactivex.Single

interface NewsRepository {
      fun getNewsList(page: Int) : Single<List<ArticlesDbModel>>
}
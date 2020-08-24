package com.test.news.data.db.converters

import com.test.news.data.db.entities.ArticlesDbModel
import com.test.news.data.db.entities.SourcesDbModel
import com.test.news.data.dto.Article
import com.test.news.data.dto.Source
import io.reactivex.Single

fun fromApiNewsToDb(apiNews: Article): ArticlesDbModel =
    ArticlesDbModel(
        id = apiNews.title,
        source = fromApiSourceToDb(apiNews.source),
        author = apiNews.author,
        description = apiNews.description,
        publishedAt = apiNews.publishedAt,
        title = apiNews.title,
        url = apiNews.url,
        urlToImage = apiNews.urlToImage
    )

fun fromApiSourceToDb(source: Source?): SourcesDbModel? = SourcesDbModel(
    id = source?.id,
    name = source?.name
)

fun fromApiArticlesToDb(articles: List<Article>?) : Single<List<ArticlesDbModel>> {
    val dbArticles : MutableList<ArticlesDbModel> = mutableListOf()
    articles?.forEach { it ->
        dbArticles.add(fromApiNewsToDb(it))
    }
    return Single.just(dbArticles)
}

package com.test.news.presentation.news.adapters.itemtypes

import com.test.news.data.db.entities.ArticlesDbModel

fun fromArticleToRow (article : ArticlesDbModel) : RowType.NewsRowType =
    RowType.NewsRowType(article)

fun fromArticleListToRowList (list : List<ArticlesDbModel>): List<RowType> {
    val rowTypeList = mutableListOf<RowType>()
    list.forEach { item ->
        rowTypeList.add(fromArticleToRow(item))
    }
    return rowTypeList
}
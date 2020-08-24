package com.test.news.data.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.test.news.data.db.entities.ArticlesDbModel
import com.test.news.data.db.entities.SourcesDbModel

class TableTypeConverter {
    @TypeConverter
    fun fromArticleListToJson(articles: List<ArticlesDbModel>?): String? = Gson().toJson(articles)

    @TypeConverter
    fun fromJsonToArticleList(articles: String?): List<ArticlesDbModel> {
        val articlesListType = object : TypeToken<List<ArticlesDbModel>>() {}.type
        return Gson().fromJson(articles, articlesListType)
    }

    @TypeConverter
    fun fromSourceToJson(source: SourcesDbModel?): String? = Gson().toJson(source)

    @TypeConverter
    fun fromJsonToSource(source: String?): SourcesDbModel? =
        Gson().fromJson(source, SourcesDbModel::class.java)

}
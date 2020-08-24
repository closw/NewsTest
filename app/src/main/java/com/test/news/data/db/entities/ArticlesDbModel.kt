package com.test.news.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "news")
data class ArticlesDbModel (
    @PrimaryKey(autoGenerate = false)
    val id: String, // id - это title из source, так как в source id всегда null

    val source: SourcesDbModel?,

    val author: String?,

    val title: String?,

    val description: String?,

    val url: String?,

    val urlToImage: String?,

    val publishedAt: Date?
) : Serializable
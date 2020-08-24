package com.test.news.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class SourcesDbModel (
    val id: String?,

    @PrimaryKey(autoGenerate = false)
    val name: String?
) : Serializable
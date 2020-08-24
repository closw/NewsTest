package com.test.news.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.test.news.data.db.converters.DateTypeConverter
import com.test.news.data.db.converters.TableTypeConverter
import com.test.news.data.db.dao.NewsDao
import com.test.news.data.db.entities.ArticlesDbModel

@Database(entities = [ArticlesDbModel::class], version = 1, exportSchema = false)
@TypeConverters(DateTypeConverter::class, TableTypeConverter::class)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao

    companion object {
        var INSTANCE: NewsDatabase? = null

        fun getNewsDataBase(context: Context): NewsDatabase? {
            if (INSTANCE == null) {
                synchronized(NewsDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        NewsDatabase::class.java,
                        "news_database"
                    ).build()

                }
            }
            return INSTANCE
        }

        fun destroyDataBase() {
            INSTANCE = null
        }
    }
}
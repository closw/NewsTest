package com.test.news.data.db.dao

import androidx.room.*
import com.test.news.data.db.entities.ArticlesDbModel

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNew(new : ArticlesDbModel)

    @Update
    fun updateNew(new: ArticlesDbModel)

    @Delete
    fun deleteNew(new : ArticlesDbModel)

    @Query("SELECT COUNT (*) FROM news")
    fun getCount() : Int

//    @Query ("SELECT 1 FROM news WHERE id == :id")
//    fun getNew (id: String) : List<ArticlesDbModel>

    @Query ("SELECT * FROM news")
    fun getNews () : List<ArticlesDbModel>
}
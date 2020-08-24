package com.test.news.data.network

import com.test.news.data.dto.News
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface INewsApiService {
    @GET("everything")
    fun getNewsList(
        @Query("q") q: String,
        @Query("from") from: String,
        @Query("sortBy") sortBy: String,
        @Query ("apiKey") apiKey: String,
        @Query("page") page: Int
    ): Single<News>
}
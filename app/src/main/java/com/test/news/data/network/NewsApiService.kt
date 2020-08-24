package com.test.news.data.network

import com.test.news.Constants
import com.test.news.data.dto.News
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NewsApiService {
    private val api: INewsApiService
    private var interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()

    private val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

    private var retrofitService: Retrofit = Retrofit.Builder()
        .baseUrl(Constants().BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    init {
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        api = retrofitService.create(INewsApiService::class.java)
    }

    fun getNewsList(page: Int): Single<News> = api.getNewsList(
        "android",
        "2019-04-00",
        "publishedAt",
        "26eddb253e7840f988aec61f2ece2907",
        page
    )
}
package com.test.news.di.modules

import android.content.Context
import com.test.news.data.db.NewsDatabase
import com.test.news.data.network.NewsApiService
import com.test.news.domain.NewsInteractor

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext



@Module
@InstallIn(ApplicationComponent::class)
class AppModule() {

    @Provides
    fun providesDataBase(@ApplicationContext context: Context): NewsDatabase =
        NewsDatabase.getNewsDataBase(context)!!

    @Provides
    fun providesApiService(): NewsApiService =
        NewsApiService()

    @Provides
    fun providesNewsRepository(database: NewsDatabase, api: NewsApiService): NewsInteractor =
        NewsInteractor(database, api)
}
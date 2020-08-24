package com.test.news.presentation.news.adapters.itemtypes

import android.os.Parcelable
import com.test.news.data.db.entities.ArticlesDbModel
import kotlinx.android.parcel.Parcelize


sealed class RowType : Parcelable  {

    @Parcelize
    data class NewsRowType(val article: ArticlesDbModel) : RowType(), Parcelable {}

    @Parcelize
    object ReloadButtonType : RowType(), Parcelable {}
    companion object {
        val ARTICLE_TYPE = 0
        val RELOAD_BUTTON_TYPE = 1
    }

}
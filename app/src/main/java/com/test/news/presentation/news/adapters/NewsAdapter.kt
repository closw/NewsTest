package com.test.news.presentation.news.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.news.R
import com.test.news.data.db.entities.ArticlesDbModel
import com.test.news.presentation.news.adapters.itemtypes.RowType
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject
import kotlin.properties.Delegates

class NewsAdapter @Inject constructor (@ActivityContext val context: Context) : RecyclerView.Adapter<NewsViewHolder> () {
    var newsList : MutableList<RowType> = mutableListOf()
    lateinit var listener : ItemClickListener<ArticlesDbModel>
    lateinit var onScrollFinishedListener: OnScrollFinishedListener
    lateinit var onReloadClickListener: ReloadClickListener
    var position by Delegates.notNull<Int>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder =
         when (viewType){
            RowType.ARTICLE_TYPE ->
                NewsViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_article, parent, false), listener, context, onReloadClickListener)
            RowType.RELOAD_BUTTON_TYPE ->
                NewsViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_reload, parent, false), listener, context, onReloadClickListener)
            else ->
                NewsViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_reload, parent, false), listener, context, onReloadClickListener)
        }

    override fun getItemViewType(position: Int): Int =
         when (newsList[position]){
            is RowType.NewsRowType ->
                RowType.ARTICLE_TYPE
            is RowType.ReloadButtonType ->
                RowType.RELOAD_BUTTON_TYPE
        }

    override fun getItemCount(): Int = newsList.size

    fun setItems(list: List<RowType>){
        newsList = list.toMutableList()
        notifyDataSetChanged()
    }

    fun addItems(list: List<RowType>){
        newsList.addAll(list)
        notifyDataSetChanged()
    }

    fun addItem(item : RowType) {
        newsList.add(item)
        notifyDataSetChanged()
    }

    fun removeErrorMessages(){
        newsList.removeAll { type ->
            type is RowType.ReloadButtonType
        }
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position])
        if(position == newsList.size - 1){
            onScrollFinishedListener.onScrollFinished()
        }
        this.position = position
    }
}
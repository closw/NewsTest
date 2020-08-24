package com.test.news.presentation.news.adapters

import android.content.Context
import android.os.Build
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.test.news.R
import com.test.news.data.db.entities.ArticlesDbModel
import com.test.news.presentation.news.adapters.itemtypes.RowType

class NewsViewHolder(
    itemView: View,
    private val itemClickListener: ItemClickListener<ArticlesDbModel>,
    private val context: Context,
    private val reloadClickListener: ReloadClickListener
) : RecyclerView.ViewHolder(itemView) {

    fun bind(data: RowType) {
        when (data) {
            is RowType.NewsRowType -> {
                val photoImg: ImageView = itemView.findViewById(R.id.ivArticle)
                val title: TextView = itemView.findViewById(R.id.tvTitle)
                val description: TextView = itemView.findViewById(R.id.tvDescription)
                itemView.setOnClickListener { itemClickListener.click(data.article) }
                title.text = data.article.title
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    description.text =
                        Html.fromHtml(data.article.description?: "", Html.FROM_HTML_MODE_COMPACT)
                } else {
                    description.text = Html.fromHtml(data.article.description?: "")
                }
                if (data.article.urlToImage != null) {
                    Glide.with(context)
                        .load(data.article.urlToImage)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(null)
                        .into(photoImg)
                }
            }
            is RowType.ReloadButtonType -> {
                val reloadButton: Button = itemView.findViewById(R.id.btnRetry)
                reloadButton.setOnClickListener { reloadClickListener.onReloadClick() }
            }
        }

    }
}
package com.test.news.presentation.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.news.R
import com.test.news.data.db.entities.ArticlesDbModel
import com.test.news.databinding.FragmentNewsBinding
import com.test.news.presentation.BaseFragment
import com.test.news.presentation.main.MainActivity
import com.test.news.presentation.news.adapters.ItemClickListener
import com.test.news.presentation.news.adapters.NewsAdapter
import com.test.news.presentation.news.adapters.OnScrollFinishedListener
import com.test.news.presentation.news.adapters.ReloadClickListener
import com.test.news.presentation.news.adapters.itemtypes.RowType
import com.test.news.presentation.news.adapters.itemtypes.fromArticleListToRowList
import dagger.hilt.android.AndroidEntryPoint
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider
import kotlin.collections.toMutableList as toMutableList

@AndroidEntryPoint
class NewsFragment : BaseFragment(), NewsView,
    ItemClickListener<ArticlesDbModel>,
    OnScrollFinishedListener,
    ReloadClickListener {
    companion object {
        val TAG = "NewsFragmentTag"
        val LIST_STATE = "ListOfNews"
        val LIST_FOCUS = "FocusOfList"
        val ACTUAL_PAGE ="ActualPage"
    }

    private lateinit var binding: FragmentNewsBinding

    @Inject
    lateinit var presenterProvider: Provider<NewsPresenter>

    private val presenter by moxyPresenter { presenterProvider.get() }

    @Inject
    lateinit var adapter: NewsAdapter

    lateinit var newsList: RecyclerView

    fun newInstance(): NewsFragment = NewsFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)
        if(savedInstanceState != null) {
            restoreFragment(savedInstanceState)
        }
        return binding.root
    }


    override fun setNewsList() {
        newsList = binding.rvNews
        newsList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter.listener = this
        adapter.onScrollFinishedListener = this
        adapter.onReloadClickListener = this
        newsList.adapter = adapter
    }

    private fun restoreFragment (state : Bundle){
        setNewsList()
        val news = state.getParcelableArrayList<RowType>(LIST_STATE)
        if(news != null){
           adapter.setItems(news)
        }
        val focusPosition = state.getInt(LIST_FOCUS)
        newsList.scrollToPosition(focusPosition)
        val actualPage = state.getInt(ACTUAL_PAGE)
        presenter.actualPage = actualPage
    }

    override fun showError() {
        if (adapter.itemCount > 0) {
            if (adapter.getItemViewType(adapter.itemCount - 1) != RowType.RELOAD_BUTTON_TYPE) {
                adapter.addItem(RowType.ReloadButtonType)
            }
        } else {
            adapter.addItem(RowType.ReloadButtonType)
        }
    }

    override fun showNetworkError() {
        if (adapter.getItemViewType(adapter.itemCount - 1) != RowType.RELOAD_BUTTON_TYPE) {
            adapter.addItem(RowType.ReloadButtonType)
        }
    }

    override fun showNews(news: List<ArticlesDbModel>) {
        if (adapter.itemCount == 0) {
            adapter.setItems(fromArticleListToRowList(news))
        } else {
            adapter.removeErrorMessages()
            adapter.addItems(fromArticleListToRowList(news).filter { item ->
                !adapter.newsList.contains(item) //чтобы при загрузке из БД элементы из начала списка не дублировались
            })
        }
    }


    override fun click(item: ArticlesDbModel) {
        (activity as MainActivity).showDetailsFragment(item.url)
    }

    override fun onScrollFinished() {
        if (presenter.actualPage <= 5) { //максимальный индекс страницы 5
            (presenter.getNextPage(presenter.actualPage))
        }
    }

    override fun onReloadClick() {
        if (presenter.actualPage <= 5) {//максимальный индекс страницы 5
            (presenter.getNextPage(presenter.actualPage))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val list = arrayListOf<RowType>()
        list.addAll(adapter.newsList)
        outState.putParcelableArrayList(LIST_STATE,list)
        outState.putInt(LIST_FOCUS, adapter.position)
        outState.putInt(ACTUAL_PAGE, presenter.actualPage)
        super.onSaveInstanceState(outState)
    }
}
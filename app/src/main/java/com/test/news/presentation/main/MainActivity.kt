package com.test.news.presentation.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.test.news.R
import com.test.news.data.dto.News
import com.test.news.presentation.details.DetailsFragment
import com.test.news.presentation.news.NewsFragment
import com.test.news.presentation.splash.SplashFragment
import dagger.hilt.android.AndroidEntryPoint
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class MainActivity : MvpAppCompatActivity(R.layout.activity_main), MainView {
    @Inject
    lateinit var presenterProvider: Provider<MainPresenter>


    private val presenter by moxyPresenter { presenterProvider.get() }


    companion object {
        const val DETAILS_URL_KEY = "details_url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if(supportFragmentManager.fragments[count -1] is NewsFragment ){
            finish()
        }
        if (count == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    override fun showSplashFragment() {
        val fragment: SplashFragment = SplashFragment().newInstance()
        supportFragmentManager.beginTransaction()
            .disallowAddToBackStack()
            .replace(R.id.container, fragment, SplashFragment.TAG)
            .commit()
    }

    override fun showNewsFragment() {
        var newsFragment: NewsFragment? =
            supportFragmentManager.findFragmentByTag(NewsFragment.TAG) as NewsFragment?
        if (newsFragment == null) {
            newsFragment = NewsFragment().newInstance()
        }
        supportFragmentManager.beginTransaction()
            .addToBackStack(NewsFragment.TAG)
            .replace(R.id.container, newsFragment, NewsFragment.TAG)
            .commit()
    }

    override fun showDetailsFragment(url: String?) {
        val fragment: DetailsFragment = DetailsFragment().newInstance()
        val args = Bundle()
        args.putString(DETAILS_URL_KEY, url)
        fragment.arguments = args
        supportFragmentManager.beginTransaction()
            .addToBackStack(DetailsFragment.TAG)
            .add(R.id.container, fragment, DetailsFragment.TAG)
            .commit()
    }
}

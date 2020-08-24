package com.test.news.presentation.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.test.news.R
import com.test.news.databinding.FragmentSplashBinding
import com.test.news.presentation.BaseFragment
import com.test.news.presentation.main.MainActivity
import com.test.news.presentation.news.NewsFragment
import dagger.hilt.android.AndroidEntryPoint
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class SplashFragment: BaseFragment(), SplashView {
    private lateinit var binding: FragmentSplashBinding

    @Inject
    lateinit var presenterProvider: Provider<SplashPresenter>

    private val presenter by moxyPresenter { presenterProvider.get() }

    fun newInstance(): SplashFragment = SplashFragment()

    companion object{
        const val TAG = "SplashFragmentTag"
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)
        return binding.root
    }

    override fun openNews() {
        (activity as MainActivity).showNewsFragment()
    }

}
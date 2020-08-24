package com.test.news.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.test.news.R
import com.test.news.databinding.FragmentDetailsBinding
import com.test.news.presentation.BaseFragment
import com.test.news.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_details.*
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class DetailsFragment : BaseFragment(), DetailsView {
    companion object {
        val TAG = "DetailsFragmentTag"
        val URL = "UrlOfArticle"
        val SCROLL_X = "WebViewScrollX"
        val SCROLL_Y = "WebViewScrollY"
    }

    @Inject
    lateinit var presenterProvider: Provider<DetailsPresenter>

    private val presenter by moxyPresenter { presenterProvider.get() }

    private lateinit var binding: FragmentDetailsBinding

    private lateinit var url: String

    private var scrollX: Int = 0

    private var scrollY: Int = 0


    fun newInstance(): DetailsFragment = DetailsFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        if (savedInstanceState == null) {
            url = arguments?.getString(MainActivity.DETAILS_URL_KEY, "") ?: ""
        } else {
            url = savedInstanceState.getString(URL) ?: ""
            scrollX = savedInstanceState.getInt(SCROLL_X)
            scrollY = savedInstanceState.getInt(SCROLL_Y)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (url.isNotEmpty()) {
            binding.wvDetails.settings.apply {
                allowFileAccess = true
                allowFileAccessFromFileURLs = true
                allowUniversalAccessFromFileURLs = true
            }
            binding.wvDetails.loadUrl(url)
            binding.wvDetails.scrollTo(scrollX, scrollY)

        } else {
            Toast.makeText(context, getString(R.string.link_is_empty), Toast.LENGTH_LONG).show()
            (activity as MainActivity).onBackPressed()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        binding.wvDetails.scrollY
        outState.putString(URL, url)
        outState.putInt(SCROLL_X, binding.wvDetails.scrollX)
        outState.putInt(SCROLL_Y, binding.wvDetails.scrollY)
        super.onSaveInstanceState(outState)
    }
}
package com.example.venetatodorova.news.activities

import android.app.ProgressDialog
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.venetatodorova.news.R
import com.example.venetatodorova.news.models.Article
import com.example.venetatodorova.news.utils.DesignManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_article_details.*
import org.jetbrains.anko.indeterminateProgressDialog

class ArticleDetailsActivity : AppCompatActivity() {

    var progressDialog : ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_details)
        setSupportActionBar(toolbar)

        val article = intent.getParcelableExtra<Article>(resources.getString(R.string.intent_message))

        DesignManager.setDetailsToolbarTypeface(toolbar_layout, assets)
        setupWebView(article)

        Picasso.with(this)
                .load(article.urlToImage)
                .into(image_id)

        toolbar_layout.title = article.title

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onPause() {
        super.onPause()
        dismissProgressDialog()
    }

    private fun dismissProgressDialog() {
        if ((progressDialog as ProgressDialog).isShowing) {
            progressDialog?.dismiss()
        }
    }

    private fun setupWebView(article: Article) {
        webView.setOnTouchListener { v, _ ->
            v.parent.requestDisallowInterceptTouchEvent(true)
            false
        }

        progressDialog = indeterminateProgressDialog(resources.getString(R.string.loading_page_message))

        webView.loadUrl(article.url)
        webView.setWebViewClient(object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                dismissProgressDialog()
            }
        })
    }
}

package com.example.venetatodorova.news.activities

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.example.venetatodorova.news.R
import com.example.venetatodorova.news.models.Article
import com.example.venetatodorova.news.utils.DesignManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_article_details.*

class ArticleDetailsActivity : AppCompatActivity() {

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

    private fun setupWebView(article: Article) {
        webView.setOnTouchListener { v, _ ->
            v.parent.requestDisallowInterceptTouchEvent(true)
            false
        }

        webView.loadUrl(article.url)
    }
}

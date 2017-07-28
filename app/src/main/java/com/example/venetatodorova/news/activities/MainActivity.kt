package com.example.venetatodorova.news.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.venetatodorova.news.R
import com.example.venetatodorova.news.adapters.ListViewAdapter
import com.example.venetatodorova.news.models.Article
import com.example.venetatodorova.news.services.APILayer
import com.example.venetatodorova.news.utils.DesignManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var articles: ArrayList<Article> = ArrayList()
    private lateinit var adapter: ListViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        DesignManager.setToolbarTypeface(toolbarLayout, assets)

        adapter = ListViewAdapter(this, articles)
        listView.adapter = adapter
        listView.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, ArticleDetailsActivity::class.java)
            intent.putExtra(resources.getString(R.string.intent_message), adapter.getItem(position))
            startActivity(intent)
        }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Not implemented", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onResume() {
        super.onResume()
        APILayer.requestNews { result ->
            articles.clear()
            articles.addAll(result)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}

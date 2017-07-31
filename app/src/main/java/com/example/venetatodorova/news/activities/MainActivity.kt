package com.example.venetatodorova.news.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.venetatodorova.news.R
import com.example.venetatodorova.news.adapters.ArticlesListViewAdapter
import com.example.venetatodorova.news.fragments.FilterDialogFragment
import com.example.venetatodorova.news.models.Article
import com.example.venetatodorova.news.models.Source
import com.example.venetatodorova.news.services.APILayer
import com.example.venetatodorova.news.utils.DesignManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), FilterDialogFragment.FilterDialogListener {

    private var articles: ArrayList<Article> = ArrayList()
    private lateinit var adapter: ArticlesListViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        DesignManager.setToolbarTypeface(toolbarLayout, assets)

        adapter = ArticlesListViewAdapter(this, articles)
        listView.adapter = adapter
        listView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, ArticleDetailsActivity::class.java)
            intent.putExtra(resources.getString(R.string.intent_message), adapter.getItem(position))
            startActivity(intent)
        }

        filterButton.setOnClickListener { _ ->
            onFilterButtonPressed()
        }
    }

    private fun onFilterButtonPressed() {
        val transaction = fragmentManager.beginTransaction()
        val previous = fragmentManager.findFragmentByTag(getString(R.string.filter_dialog_tag))
        if (previous != null) {
            transaction.remove(previous)
        }
        transaction.addToBackStack(null)

        val filterDialogFragment = FilterDialogFragment()
        filterDialogFragment.setListener(this)
        filterDialogFragment.show(transaction, (getString(R.string.filter_dialog_tag)))
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

    override fun onFilterDialogDismissed(selectedSources: ArrayList<Source>, filter: FilterDialogFragment.Filter) {
        print(selectedSources)
    }
}

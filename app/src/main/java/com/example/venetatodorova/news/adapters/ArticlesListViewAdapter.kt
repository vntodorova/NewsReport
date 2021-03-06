package com.example.venetatodorova.news.adapters

import android.content.Context
import android.graphics.Point
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.venetatodorova.news.R
import com.example.venetatodorova.news.models.Article
import com.example.venetatodorova.news.utils.DesignManager
import com.squareup.picasso.Picasso

class ArticlesListViewAdapter(val context: Context, var itemsList: ArrayList<Article>) : BaseAdapter() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View

        val viewHolder: ViewHolder
        if (convertView == null) {
            view = this.inflater.inflate(R.layout.row_article, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val currentArticle = itemsList[position]

        viewHolder.titleLabel.text = currentArticle.title
        DesignManager.setTypeface(arrayListOf(viewHolder.titleLabel), context.assets)

        Picasso.with(context)
                .load(currentArticle.urlToImage)
                .resize(getScreenSize().x, 400)
                .centerCrop()
                .into(viewHolder.imageView)

        return view
    }

    override fun getItem(position: Int): Article {
        return itemsList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return itemsList.count()
    }

    private class ViewHolder(view: View?) {
        val titleLabel: TextView = view?.findViewById(R.id.titleTextView) as TextView
        val imageView: ImageView = view?.findViewById(R.id.imageView) as ImageView
    }

    private fun getScreenSize(): Point {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val result = Point()
        display.getSize(result)
        return result
    }

}
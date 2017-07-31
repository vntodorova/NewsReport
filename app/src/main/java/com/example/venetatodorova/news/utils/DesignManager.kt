package com.example.venetatodorova.news.utils

import android.content.res.AssetManager
import android.graphics.Typeface
import android.support.design.widget.CollapsingToolbarLayout
import android.widget.TextView

object DesignManager {

    fun setToolbarTypeface(toolbarLayout: CollapsingToolbarLayout, assets: AssetManager?) {
        val type = Typeface.createFromAsset(assets, "fonts/Quicksand-Light.otf")
        toolbarLayout.title = "News"
        toolbarLayout.setExpandedTitleTypeface(type)
        toolbarLayout.setCollapsedTitleTypeface(type)
    }

    fun setDetailsToolbarTypeface(toolbar_layout: net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout, assets: AssetManager?) {
        val type = Typeface.createFromAsset(assets, "fonts/Quicksand-Light.otf")
        toolbar_layout.setExpandedTitleTypeface(type)
        toolbar_layout.setCollapsedTitleTypeface(type)
        toolbar_layout.background
    }

    fun setTypeface(views: ArrayList<TextView>, assets: AssetManager?) {
        views.forEach { view ->
            view.typeface = Typeface.createFromAsset(assets, "fonts/Quicksand-Regular.otf")
        }
    }


}
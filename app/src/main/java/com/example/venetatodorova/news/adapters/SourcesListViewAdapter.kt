package com.example.venetatodorova.news.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.example.venetatodorova.news.R
import com.example.venetatodorova.news.models.Source
import com.example.venetatodorova.news.utils.DesignManager
import kotlinx.android.synthetic.main.header_source.view.*
import kotlinx.android.synthetic.main.row_source.view.*

class SourcesListViewAdapter(var context: Context,
                             var sourcesMap: HashMap<String, ArrayList<Source>>) : BaseExpandableListAdapter() {

    var headersList: ArrayList<String> = arrayListOf(
            "General",
            "Science and nature",
            "Gaming",
            "Music",
            "Politics",
            "Technology",
            "Sport",
            "Business",
            "Entertainment"
    )

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    fun getSelectedSources(): ArrayList<Source> {
        val result = ArrayList<Source>()
        sourcesMap.values.forEach { category ->
            category.forEach { source ->
                if (source.isChosen) {
                    result.add(source)
                }
            }
        }
        return result
    }

    override fun getGroupCount(): Int {
        return headersList.count()
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return sourcesMap[getGroup(groupPosition)]?.count() as Int
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        val view: View

        if (convertView == null) {
            view = this.inflater.inflate(R.layout.header_source, parent, false)
        } else {
            view = convertView
        }

        DesignManager.setTypeface(arrayListOf(
                view.headerTitle
        ), context.assets)

        view.headerTitle.text = getGroup(groupPosition)
        return view
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val currentSource = sourcesMap[headersList[groupPosition]]?.get(childPosition) as Source

        if (convertView == null) {
            view = this.inflater.inflate(R.layout.row_source, parent, false)
        } else {
            view = convertView
            view.checkBox.setOnCheckedChangeListener { _, isChecked ->
                currentSource.isChosen = isChecked
            }
        }

        DesignManager.setTypeface(arrayListOf(
                view.checkBox
        ), context.assets)

        view.checkBox.isChecked = currentSource.isChosen
        view.checkBox.text = currentSource.name

        return view
    }

    override fun getGroup(groupPosition: Int): String {
        return headersList[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Source {
        return sourcesMap[headersList[groupPosition]]?.get(childPosition) as Source
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return true
    }

}
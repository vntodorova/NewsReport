package com.example.venetatodorova.news.fragments

import android.app.DialogFragment
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.ArraySet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.venetatodorova.news.R
import com.example.venetatodorova.news.adapters.SourcesListViewAdapter
import com.example.venetatodorova.news.models.Source
import com.example.venetatodorova.news.services.APILayer
import com.example.venetatodorova.news.utils.DesignManager
import com.example.venetatodorova.news.utils.SystemPreferencesHelper
import kotlinx.android.synthetic.main.fragment_filter_dialog.*

class FilterDialogFragment : DialogFragment() {

    enum class Filter {
        top,
        popular,
        latest
    }

    private var listener: FilterDialogListener? = null

    private var sourcesMap: HashMap<String, ArrayList<Source>> = HashMap()

    private lateinit var adapter: SourcesListViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        APILayer.requestSources { result ->
            categorizeSources(result)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater?.inflate(R.layout.fragment_filter_dialog, container, false)
        val backgroundColorId = ContextCompat.getColor(context, R.color.colorAccentTransparent)
        dialog.window.setBackgroundDrawable(ColorDrawable(backgroundColorId))
        return view as View
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        DesignManager.setTypeface(arrayListOf(
                textView1,
                textView2,
                latestRadioButton,
                topRadioButton,
                popularRadioButton,
                saveButton,
                cancelButton
        ), activity.assets)

        adapter = SourcesListViewAdapter(activity, sourcesMap)
        expandableListView.setAdapter(adapter)

        saveButton.setOnClickListener { onSaveButtonPressed() }
        cancelButton.setOnClickListener { dismiss() }
    }

    public fun setListener(listener: FilterDialogListener) {
        this.listener = listener
    }

    private fun onSaveButtonPressed() {
        val selectedSources = adapter.getSelectedSources()
        SystemPreferencesHelper.write(activity, resources, getSourcesNames(selectedSources))
        listener?.onFilterDialogDismissed(selectedSources, getSelectedFilter())
        dismiss()
    }

    private fun getSourcesNames(sources: ArrayList<Source>): Set<String> {
        val result = ArraySet<String>()
        sources.forEach { source ->
            result.add(source.name)
        }
        return result
    }

    private fun getSelectedFilter(): Filter {
        var filter = Filter.top
        if (latestRadioButton.isChecked) {
            filter = Filter.latest
        }
        if (popularRadioButton.isChecked) {
            filter = Filter.popular
        }
        return filter
    }

    private fun categorizeSources(sources: ArrayList<Source>) {
        val chosenSources = SystemPreferencesHelper.read(activity, resources)
        sources.forEach { source ->
            val categoryTitle = Source.categoryTitles[source.category] as String
            setIsChosen(chosenSources, source)
            addSourceToCategory(categoryTitle, source)
        }
        adapter.notifyDataSetChanged()
    }

    private fun addSourceToCategory(categoryTitle: String, element: Source) {
        if (sourcesMap[categoryTitle] == null) {
            sourcesMap[categoryTitle] = ArrayList()
        }
        sourcesMap[categoryTitle]?.add(element)
    }

    private fun setIsChosen(selectedCategories: Set<String>?, element: Source) {
        if (selectedCategories?.contains(element.name) == true) {
            element.isChosen = true
        }
    }

    interface FilterDialogListener {
        fun onFilterDialogDismissed(selectedSources: ArrayList<Source>, filter: Filter)
    }

}
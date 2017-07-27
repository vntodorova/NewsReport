package com.example.venetatodorova.news

import android.content.res.Resources
import android.net.Uri
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import java.net.URL

object APILayer {

    val apiKey = "38664fe26d3b49948b361e0b5075f527"

    fun buildURL(source: String): URL {
        val url: URL
        val uriBuilder = Uri.Builder()
        uriBuilder.scheme("https")
                .authority("newsapi.org")
                .appendPath("v1")
                .appendPath("articles")
                .appendQueryParameter("source", source)
                .appendQueryParameter("apiKey",apiKey)
        url = URL(uriBuilder.build().toString())
        return url
    }

    fun requestNews(resultHandler: (Result<String, FuelError>) -> (Unit)) {
        buildURL("techcrunch")
                .toString()
                .httpGet()
                .responseString { request, response, result ->
                    resultHandler(result)
                }
    }

}
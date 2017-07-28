package com.example.venetatodorova.news

import android.net.Uri
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.github.kittinunf.fuel.httpGet
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
                .appendQueryParameter("apiKey", apiKey)
        url = URL(uriBuilder.build().toString())
        return url
    }

    fun requestNews(resultHandler: (ArrayList<Article>) -> (Unit)) {
        buildURL("techcrunch")
                .toString()
                .httpGet()
                .responseString { request, response, result ->
                    resultHandler(parse(result.get()))
                }
    }

    @Suppress("UNCHECKED_CAST")
    fun parse(jsonString: String): ArrayList<Article> {
        val jsonObject = Parser().parse(StringBuilder(jsonString)) as JsonObject
        val articles = jsonObject["articles"] as JsonArray<JsonObject>
        val result = ArrayList<Article>()
        articles.forEach { element ->
            result.add(Article(element))
        }
        return result
    }

}
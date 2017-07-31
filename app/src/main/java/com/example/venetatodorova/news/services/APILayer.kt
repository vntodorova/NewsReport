package com.example.venetatodorova.news.services

import android.net.Uri
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.example.venetatodorova.news.models.Article
import com.example.venetatodorova.news.models.Source
import com.github.kittinunf.fuel.httpGet
import java.net.URL

object APILayer {

    private val apiKey = "38664fe26d3b49948b361e0b5075f527"

    fun requestNews(resultHandler: (ArrayList<Article>) -> (Unit)) {
        buildArticleURL("techcrunch")
                .toString()
                .httpGet()
                .responseString { request, response, result ->
                    resultHandler(parseArticle(result.get()))
                }
    }

    fun requestSources(resultHandler: (ArrayList<Source>) -> (Unit)) {
        buildSourceURL()
                .toString()
                .httpGet()
                .responseString { request, response, result ->
                    resultHandler(parseSource(result.get()))
                }
    }

    private fun buildArticleURL(source: String): URL {
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

    private fun buildSourceURL(): URL {
        val url: URL
        val uriBuilder = Uri.Builder()
        uriBuilder.scheme("https")
                .authority("newsapi.org")
                .appendPath("v1")
                .appendPath("sources")
        url = URL(uriBuilder.build().toString())
        return url
    }

    @Suppress("UNCHECKED_CAST")
    private fun parseArticle(jsonString: String): ArrayList<Article> {
        val jsonObject = Parser().parse(StringBuilder(jsonString)) as JsonObject
        val articles = jsonObject["articles"] as JsonArray<JsonObject>
        val result = ArrayList<Article>()
        articles.forEach { element ->
            result.add(Article(element))
        }
        return result
    }

    @Suppress("UNCHECKED_CAST")
    private fun parseSource(jsonString: String): ArrayList<Source> {
        val jsonObject = Parser().parse(StringBuilder(jsonString)) as JsonObject
        val sources = jsonObject["sources"] as JsonArray<JsonObject>
        val result = ArrayList<Source>()
        sources.forEach { element ->
            result.add(Source(element))
        }
        return result
    }

}
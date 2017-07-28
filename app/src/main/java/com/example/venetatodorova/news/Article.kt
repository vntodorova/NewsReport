package com.example.venetatodorova.news

import com.beust.klaxon.JsonObject

class Article(var author: String,
              var title: String,
              var description: String,
              var url: String,
              var urlToImage: String,
              var publishedAt: String) {

    constructor(json: JsonObject) : this(
            json["author"] as String,
            json["title"] as String,
            json["description"] as String,
            json["url"] as String,
            json["urlToImage"] as String,
            json["publishedAt"] as String
    )
}

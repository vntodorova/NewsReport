package com.example.venetatodorova.news.models

import com.beust.klaxon.JsonObject

class Source(var id: String,
             var name: String,
             var description: String,
             var url: String,
             var category: String,
             var language: String,
             var country: String,
             var isChosen: Boolean) {

    constructor(json: JsonObject) : this(
            json["id"] as String,
            json["name"] as String,
            json["description"] as String,
            json["url"] as String,
            json["category"] as String,
            json["language"] as String,
            json["country"] as String,
            false
    )

    companion object {
        val categoryTitles: HashMap<String, String> = hashMapOf(
                "science-and-nature" to "Science and nature",
                "gaming" to "Gaming",
                "music" to "Music",
                "general" to "General",
                "politics" to "Politics",
                "technology" to "Technology",
                "sport" to "Sport",
                "business" to "Business",
                "entertainment" to "Entertainment")
    }
}
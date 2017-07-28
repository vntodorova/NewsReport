package com.example.venetatodorova.news.models

import android.os.Parcel
import android.os.Parcelable
import com.beust.klaxon.JsonObject

class Article(var author: String,
              var title: String,
              var description: String,
              var url: String,
              var urlToImage: String,
              var publishedAt: String) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    constructor(json: JsonObject) : this(
            json["author"] as String,
            json["title"] as String,
            json["description"] as String,
            json["url"] as String,
            json["urlToImage"] as String,
            json["publishedAt"] as String
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(author)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(url)
        parcel.writeString(urlToImage)
        parcel.writeString(publishedAt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Article> {
        override fun createFromParcel(parcel: Parcel): Article {
            return Article(parcel)
        }

        override fun newArray(size: Int): Array<Article?> {
            return arrayOfNulls(size)
        }
    }
}

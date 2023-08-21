package com.max.quotes.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiQuote(
    val _id: String,
    val content: String,
    val author: String,
    val authorSlug: String,
    val length: Int,
    val tags: Array<String>
)

package com.max.quotes.data

import com.max.quotes.data.db.Quote
import com.max.quotes.network.ApiQuote

fun ApiQuote.toQuote() = Quote(_id, content, author)

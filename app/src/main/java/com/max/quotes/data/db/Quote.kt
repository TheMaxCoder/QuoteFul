package com.max.quotes.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Quote(@PrimaryKey val id: String, val content: String, val author: String)

package com.fastnews.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fastKeys")
data class FastKeys(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val after: String?)
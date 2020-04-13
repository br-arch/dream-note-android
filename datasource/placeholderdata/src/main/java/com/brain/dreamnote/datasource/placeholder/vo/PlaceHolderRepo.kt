package com.brain.dreamnote.datasource.placeholder.vo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlaceHolderRepo(
    @PrimaryKey
    val id: Int,
    val name: String
) {
}
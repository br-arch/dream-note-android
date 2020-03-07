package com.brain.dreamnote.vo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TestRepo(
    @PrimaryKey
    val id: Int,
    val name: String
) {
}
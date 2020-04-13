package com.brain.dreamnote.datasource.placeholder.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.brain.dreamnote.datasource.placeholder.vo.PlaceHolderRepo

@Database(
    entities = [PlaceHolderRepo::class], version = 0
)
abstract class DreamDb : RoomDatabase() {
    abstract fun dreamDao(): DreamDao
}
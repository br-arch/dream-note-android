package com.brain.dreamnote.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.brain.dreamnote.vo.TestRepo

@Database(
    entities = [TestRepo::class], version = 0
)
abstract class DreamDb : RoomDatabase() {
    abstract fun dreamDao(): DreamDao
}
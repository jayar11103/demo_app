package com.example.m88demoapp.appDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.m88demoapp.iTuneEntity.MovieDetailsEntity
import com.example.m88demoapp.iTuneEntity.SavedDataEntity
import com.example.m88demoapp.iTunesDao.MovieDetailsDao
import com.example.m88demoapp.iTunesDao.SavedDataDao

/**
 * Room database class that includes entities [MovieDetailsEntity] and [SavedDataEntity].
 * This class provides DAO instances for accessing the corresponding tables.
 *
 * @property movieDetailsDao DAO for interacting with the 'itunes_movie_list' table.
 * @property savedDataDao DAO for interacting with the 'saved_data_table' table.
 */
@Database(entities = [MovieDetailsEntity::class, SavedDataEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDetailsDao(): MovieDetailsDao
    abstract fun savedDataDao(): SavedDataDao
}

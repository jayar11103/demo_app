package com.example.m88demoapp.iTunesDao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.example.m88demoapp.iTuneEntity.SavedDataEntity

/**
 * Data Access Object (DAO) for handling operations related to saved data in the local database.
 */
@Dao
interface SavedDataDao {
    /**
     * Save the provided [SavedDataEntity] to the database.
     *
     * @param savedData The data to be saved.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveData(savedData: SavedDataEntity)

    /**
     * Retrieve the saved data from the database.
     *
     * @return [SavedDataEntity] if available, or null if no data is present.
     */
    @Query("SELECT * FROM saved_data_table")
    suspend fun getData(): SavedDataEntity?

    /**
     * Remove the provided [SavedDataEntity] from the database.
     *
     * @param savedData The data to be removed.
     */
    @Delete
    suspend fun removeData(savedData: SavedDataEntity)
}

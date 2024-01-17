package com.example.m88demoapp.repository

/**
 * Repository interface for managing app preferences using SharedPreferences.
 */
interface PreferenceRepository {

    /**
     * Retrieve the last visit time from preferences.
     * @return Long representing the last visit time.
     */
    suspend fun getLastVisitTime(): Long

    /**
     * Save the last visit time to preferences.
     * @param time Long representing the last visit time to be saved.
     */
    suspend fun saveLastVisitTime(time: Long)

    /**
     * Save the last screen ID to preferences.
     * @param screenId Int representing the last screen ID to be saved.
     */
    suspend fun saveLastScreen(screenId: Int)

    /**
     * Retrieve the last screen ID from preferences.
     * @return Int representing the last screen ID.
     */
    suspend fun getLastScreen(): Int
}

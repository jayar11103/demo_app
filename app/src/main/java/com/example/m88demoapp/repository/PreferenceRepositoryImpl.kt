package com.example.m88demoapp.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.m88demoapp.util.Const.LAST_SCREEN_KEY
import com.example.m88demoapp.util.Const.LAST_VISIT_TIME_KEY

private const val DEFAULT_SCREEN = 0

/**
 * Implementation of [PreferenceRepository] that uses SharedPreferences for storing app preferences.
 */
class PreferenceRepositoryImpl(context: Context) :
    PreferenceRepository {

    // SharedPreferences instance for storing preferences
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "app_preferences",
        Context.MODE_PRIVATE
    )

    /**
     * Retrieve the last visit time from SharedPreferences.
     * @return Long representing the last visit time.
     */
    override suspend fun getLastVisitTime(): Long {
        return sharedPreferences.getLong(LAST_VISIT_TIME_KEY, 0)
    }

    /**
     * Save the last visit time to SharedPreferences.
     * @param time Long representing the last visit time to be saved.
     */
    override suspend fun saveLastVisitTime(time: Long) {
        sharedPreferences.edit().putLong(LAST_VISIT_TIME_KEY, time).apply()
    }

    /**
     * Save the last screen ID to SharedPreferences.
     * @param screenId Int representing the last screen ID to be saved.
     */
    override suspend fun saveLastScreen(screenId: Int) {
        sharedPreferences.edit().putInt(LAST_SCREEN_KEY, screenId).apply()
    }

    /**
     * Retrieve the last screen ID from SharedPreferences.
     * @return Int representing the last screen ID.
     */
    override suspend fun getLastScreen(): Int {
        return sharedPreferences.getInt(LAST_SCREEN_KEY, DEFAULT_SCREEN)
    }
}


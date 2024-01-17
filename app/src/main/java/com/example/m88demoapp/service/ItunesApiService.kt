package com.example.m88demoapp.service

import com.example.m88demoapp.response.ItunesResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface defining the iTunes API service.
 */
interface ItunesApiService {
    /**
     * Suspend function to perform a search for movies on iTunes.
     * @return ItunesResponse representing the API response.
     */
    @GET("search?term=star&country=au&media=movie&all")
    suspend fun searchMovies(): ItunesResponse

    /**
     * Companion object to create instances of the [ItunesApiService].
     */
    companion object {
        /**
         * Factory method to create an instance of [ItunesApiService].
         * @return Created [ItunesApiService] instance.
         */
        fun create(): ItunesApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://itunes.apple.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ItunesApiService::class.java)
        }
    }
}

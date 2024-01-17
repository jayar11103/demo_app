package com.example.m88demoapp.response

import com.example.m88demoapp.model.MovieDetails

data class ItunesResponse(
    val resultCount: Int,
    val results: List<MovieDetails>
)
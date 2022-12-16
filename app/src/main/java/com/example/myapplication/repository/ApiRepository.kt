package com.example.myapplication.repository

import com.example.myapplication.api.ApiServices
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val apiServices: ApiServices,
) {
    suspend fun getPopularMoviesList(page: Int) = apiServices.getPopularMoviesList(page)
    suspend fun getTopMoviesList(page: Int) = apiServices.getTopRatedMovies(page)
    suspend fun getUpcomingMoviesList(page: Int) = apiServices.getUpcomingMovies(page)

    suspend fun getMovieDetails(id: Int) = apiServices.getMovieDetails(id)

    suspend fun searchMovie(page: Int, query: String) = apiServices.getSearchMovies(page, query)
}
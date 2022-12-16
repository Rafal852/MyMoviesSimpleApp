package com.example.myapplication.api

import com.example.myapplication.api.response.MovieDetailsResponse
import com.example.myapplication.api.response.MoviesListResponse
import com.example.myapplication.api.response.TopMovieList
import com.example.myapplication.api.response.UpcomingListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    //    https://api.themoviedb.org/3/movie/550?api_key=***
    //    https://api.themoviedb.org/3/movie/popular?api_key=***
    //    https://api.themoviedb.org/3/

    @GET("movie/popular")
    suspend fun getPopularMoviesList(@Query("page") page: Int): Response<MoviesListResponse>
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("page") page: Int): Response<TopMovieList>
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("page") page: Int): Response<UpcomingListResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") id: Int): Response<MovieDetailsResponse>

    @GET("search/movie")
    suspend fun getSearchMovies(@Query("page") page: Int, @Query("query") query: String) : Response<MoviesListResponse>



}
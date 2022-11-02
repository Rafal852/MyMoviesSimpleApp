package com.example.myapplication

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "2f669c9add29958d2450985ccbd5b65c",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>
}
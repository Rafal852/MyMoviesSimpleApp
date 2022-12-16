package com.example.myapplication.ui.search

import com.example.myapplication.api.response.MoviesListResponse

interface SearchContracts {

        fun loadSearchMoviesList(data : MoviesListResponse)


        fun callSearchMoviesList(page: Int,query: String)

}
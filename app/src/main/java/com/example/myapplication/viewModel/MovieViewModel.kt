package com.example.myapplication.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.myapplication.repository.ApiRepository
import com.example.myapplication.paging.MoviesPagingSource
import com.example.myapplication.paging.TopMoviesPagingSource
import com.example.myapplication.api.response.MovieDetailsResponse
import com.example.myapplication.api.response.MoviesListResponse
import com.example.myapplication.paging.UpcomingMoviesPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.http.Query
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val repository: ApiRepository) : ViewModel() {

    val loading = MutableLiveData<Boolean>()

    val moviesList = Pager(PagingConfig(1)) {
        MoviesPagingSource(repository, null)
    }.flow.cachedIn(viewModelScope)

    val topMoviesList = Pager(PagingConfig(1)) {
        TopMoviesPagingSource(repository)
    }.flow.cachedIn(viewModelScope)

    val upcomingMoviesList = Pager(PagingConfig(1)) {
        UpcomingMoviesPagingSource(repository)
    }.flow.cachedIn(viewModelScope)


    fun searchMovie(query: String) = Pager(PagingConfig(1)) {
        MoviesPagingSource(repository, query)
    }.flow.cachedIn(viewModelScope)


//    val searchMovieList = MutableLiveData<MoviesListResponse>()
//    fun loadSearchMovie(query: String) = viewModelScope.launch {
//        loading.postValue(true)
//        val response = repository.searchMovie(query)
//        if (response.isSuccessful){
//            searchMovieList.postValue(response.body())
//        }
//        loading.postValue(false)
//    }


    //Api
    val detailsMovie = MutableLiveData<MovieDetailsResponse>()
    fun loadDetailsMovie(id: Int) = viewModelScope.launch {
        loading.postValue(true)
        val response = repository.getMovieDetails(id)
        if (response.isSuccessful) {
            detailsMovie.postValue(response.body())
        }
        loading.postValue(false)
    }
}
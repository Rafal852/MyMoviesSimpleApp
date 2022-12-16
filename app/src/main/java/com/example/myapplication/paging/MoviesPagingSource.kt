package com.example.myapplication.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.myapplication.repository.ApiRepository
import com.example.myapplication.api.response.MoviesListResponse
import retrofit2.HttpException
import retrofit2.http.Query

class MoviesPagingSource(
    private val repository: ApiRepository,
    private val query: String?
) : PagingSource<Int, MoviesListResponse.Result>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviesListResponse.Result> {
        return try {
            val currentPage = params.key ?: 1
            val response = if (query!=null) repository.searchMovie(1, query) else repository.getPopularMoviesList(currentPage)
            val data = response.body()!!.results
            val responseData = mutableListOf<MoviesListResponse.Result>()
            responseData.addAll(data)

            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }

    }


    override fun getRefreshKey(state: PagingState<Int, MoviesListResponse.Result>): Int? {
        return null
    }


}
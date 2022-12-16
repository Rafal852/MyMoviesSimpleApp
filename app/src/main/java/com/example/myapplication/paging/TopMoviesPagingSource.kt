package com.example.myapplication.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.myapplication.repository.ApiRepository

import com.example.myapplication.api.response.TopMovieList
import retrofit2.HttpException

class TopMoviesPagingSource(
    private val repository: ApiRepository,
) : PagingSource<Int, TopMovieList.Result>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TopMovieList.Result> {
        return try {
            val currentPage = params.key ?: 1
            val response = repository.getTopMoviesList(currentPage)
            val data = response.body()!!.results
            val responseData = mutableListOf<TopMovieList.Result>()
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


    override fun getRefreshKey(state: PagingState<Int, TopMovieList.Result>): Int? {
        return null
    }


}


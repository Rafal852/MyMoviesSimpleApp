package com.example.myapplication.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.myapplication.api.response.TopMovieList
import com.example.myapplication.api.response.UpcomingListResponse
import com.example.myapplication.repository.ApiRepository
import retrofit2.HttpException

class UpcomingMoviesPagingSource(
    private val repository: ApiRepository,
) : PagingSource<Int, UpcomingListResponse.Result>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UpcomingListResponse.Result> {
        return try {
            val currentPage = params.key ?: 1
            val response = repository.getUpcomingMoviesList(currentPage)
            val data = response.body()!!.results
            val responseData = mutableListOf<UpcomingListResponse.Result>()
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


    override fun getRefreshKey(state: PagingState<Int, UpcomingListResponse.Result>): Int? {
        return null
    }


}

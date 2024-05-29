package `in`.iot.lab.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.Response
import java.io.IOException


class AppPagingSource<T : Any>(
    private val request: suspend (params: LoadParams<Int>) -> Response<List<T>>
) : PagingSource<Int, T>() {

    override fun getRefreshKey(state: PagingState<Int, T>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val page = params.key ?: 0
            val response = request(params)
            if (response.isSuccessful) {
                LoadResult.Page(
                    data = response.body()!!,
                    prevKey = if (page == 0) null else page - 1,
                    nextKey = if (response.body()!!.isEmpty()) null else page + 1
                )
            } else
                LoadResult.Error(Throwable("There are no records in the database! Try again later"))
        } catch (exception: IOException) {
            LoadResult.Error(Throwable("Oh no! Internet error! Try again~"))
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
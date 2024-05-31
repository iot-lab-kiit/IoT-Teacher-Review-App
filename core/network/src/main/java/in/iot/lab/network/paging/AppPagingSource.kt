package `in`.iot.lab.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import `in`.iot.lab.network.data.CustomResponse
import `in`.iot.lab.network.state.ResponseState
import `in`.iot.lab.network.utils.NetworkUtil.checkApiResponseStatusCode
import java.io.IOException


class AppPagingSource<T : Any>(
    private val request: suspend (params: LoadParams<Int>) -> CustomResponse<List<T>>
) : PagingSource<Int, T>() {

    override fun getRefreshKey(state: PagingState<Int, T>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val page = params.key ?: 0
            val response = request(params)

            if (response.checkApiResponseStatusCode() is ResponseState.Success) {
                LoadResult.Page(
                    data = response.data!!,
                    prevKey = if (page == 0) null else page - 1,
                    nextKey = if (response.data.isEmpty()) null else page + 1
                )
            } else
                LoadResult.Error(Throwable(message = response.status.toString() + " " + response.message))
        } catch (exception: IOException) {
            LoadResult.Error(Throwable(message = "405"))
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
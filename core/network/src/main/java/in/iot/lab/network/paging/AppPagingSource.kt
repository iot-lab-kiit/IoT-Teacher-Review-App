package `in`.iot.lab.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import `in`.iot.lab.network.data.CustomResponse
import `in`.iot.lab.network.state.ResponseState
import `in`.iot.lab.network.utils.NetworkStatusCodes.INTERNET_ERROR
import `in`.iot.lab.network.utils.NetworkStatusCodes.SERVER_UNDER_MAINTENANCE_EC2
import `in`.iot.lab.network.utils.NetworkStatusCodes.SERVER_UNDER_MAINTENANCE_NGROK
import `in`.iot.lab.network.utils.NetworkUtil.checkApiResponseStatusCode
import retrofit2.HttpException
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
            } else if (response.checkApiResponseStatusCode() is ResponseState.NoDataFound)
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            else
                LoadResult.Error(Throwable("${response.status} - ${response.message}"))
        } catch (e: HttpException) {

            // Checking if the server is currently under maintenance
            val errorMessage = when (e.code()) {
                SERVER_UNDER_MAINTENANCE_EC2 -> "Server is currently under maintenance. Try again later!"
                SERVER_UNDER_MAINTENANCE_NGROK -> "Server is currently under maintenance. Try again later!"
                else -> e.message().toString()
            }

            LoadResult.Error(Throwable(message = errorMessage))
        } catch (exception: IOException) {
            LoadResult.Error(Throwable(message = INTERNET_ERROR.toString()))
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
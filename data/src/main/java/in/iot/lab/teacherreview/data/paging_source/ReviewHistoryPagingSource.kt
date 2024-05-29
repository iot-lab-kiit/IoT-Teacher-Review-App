package `in`.iot.lab.teacherreview.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import `in`.iot.lab.teacherreview.data.remote.UserApiService
import `in`.iot.lab.teacherreview.domain.models.review.RemoteReviewHistoryResponse

class ReviewHistoryPagingSource (
    private val userUid : String,
    private val authToken : String,
    private val apiService: UserApiService
): PagingSource<Int, RemoteReviewHistoryResponse>(){

    override fun getRefreshKey(state: PagingState<Int, RemoteReviewHistoryResponse>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RemoteReviewHistoryResponse> {
        return try {
            val page = params.key ?: 0
            // add skip if needed to skip the number of items

            val response = apiService.getReviewHistory(
                userUid = userUid,
                authToken = authToken,
//                page = page
                //add other parameters required for paging like page size and page number
            )
            val data = response.body() ?: emptyList()

            LoadResult.Page(
                data = data,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (data.isEmpty()) null else page + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
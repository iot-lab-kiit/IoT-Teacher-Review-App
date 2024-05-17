package `in`.iot.lab.teacherreview.feature_teacherlist.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import `in`.iot.lab.teacherreview.core.utils.Constants
import `in`.iot.lab.teacherreview.feature_authentication.domain.repository.AuthRepository
import `in`.iot.lab.teacherreview.feature_teacherlist.data.remote.ReviewsApi
import `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.remote.Review

class ReviewHistorySource (
    private val studentId: String,
    private val authRepository: AuthRepository,
    private val reviewsApi: ReviewsApi
) : PagingSource<Int, Review>() {
    override fun getRefreshKey(state: PagingState<Int, Review>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Review> {
        return try {
            val page = params.key ?: 0
            val skip = page * Constants.ITEMS_PER_PAGE

            val response = reviewsApi.getStudentReviewHistory(
                token = authRepository.getUserIdToken().getOrDefault(""),
                studentId = studentId,
                limitValue = Constants.ITEMS_PER_PAGE,
                skip = skip
            )

            val data = response.body()!!

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
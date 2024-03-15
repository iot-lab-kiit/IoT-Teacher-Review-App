package `in`.iot.lab.teacherreview.feature_teacherlist.data.repository

import `in`.iot.lab.teacherreview.feature_authentication.domain.repository.AuthRepository
import `in`.iot.lab.teacherreview.feature_teacherlist.data.data_source.remote.RetrofitInstance
import `in`.iot.lab.teacherreview.feature_teacherlist.data.model.ReviewData
import `in`.iot.lab.teacherreview.feature_teacherlist.data.model.ReviewPostData
import `in`.iot.lab.teacherreview.feature_teacherlist.utils.AddReviewApiState
import `in`.iot.lab.teacherreview.feature_teacherlist.utils.GetHistoryApiCallState
import `in`.iot.lab.teacherreview.feature_teacherlist.utils.IndividualTeacherReviewApiCall
import `in`.iot.lab.teacherreview.feature_teacherlist.utils.TeacherListApiCallState
import javax.inject.Inject

/**
 * This is the repository which is going to control flow of every data related to
 * feature_teacher list module
 *
 * @property getTeacherList This Function calls the Server and fetches the Teacher List to
 * be shown to the user
 * @property getIndividualTeacherReviews This Function calls the Server and fetches
 * the Teacher List to be shown to the user
 * @property getStudentReviewHistory This calls the API and fetches detailed Reviews
 * History of a particular Student
 * @property postReviewData This function posts the Review Data to the database
 */
class Repository @Inject constructor(
    private val authRepository: AuthRepository
) {
    private suspend fun getToken() = authRepository.getUserIdToken().getOrDefault("")

    // This Function calls the Server and fetches the Teacher List to be shown to the user
    suspend fun getTeacherList(
        limitValue: Int,
        facultyName: String? = null
    ): TeacherListApiCallState {

        // This is the Response from the Server
        val response = RetrofitInstance.apiInstance.getTeacherList(
            limitValue = limitValue,
            facultyName = facultyName,
            token = getToken()
        )

        // Checking if the Response is successful or a Failure
        return if (response.isSuccessful)
            TeacherListApiCallState.Success(response.body()!!)
        else
            TeacherListApiCallState.Failure(errorMessage = "Error Connecting to the Server")
    }

    // This calls the API and fetches detailed Reviews of a particular Teachers
    suspend fun getIndividualTeacherReviews(
        facultyId: String,
        limitValue: Int
    ): IndividualTeacherReviewApiCall {

        // This is the Response from the Server
        val response = RetrofitInstance.apiInstance.getIndividualTeacherReviews(
            limitValue = limitValue,
            facultyId = facultyId,
            token = getToken()
        )

        // Checking if the Response is successful or a Failure
        return if (response.isSuccessful) {

            val reviewData = response.body()!!

            val sortedReviews = reviewData.individualReviewData?.sortedByDescending {
                it.createdAt
            }

            val sortedResponse = ReviewData(
                avgAttendanceRating = reviewData.avgAttendanceRating,
                avgMarkingRating = reviewData.avgMarkingRating,
                avgTeachingRating = reviewData.avgTeachingRating,
                total = reviewData.total,
                limit = reviewData.limit,
                skip = reviewData.skip,
                individualReviewData = sortedReviews
            )

            IndividualTeacherReviewApiCall.Success(reviewData = sortedResponse)
        } else
            IndividualTeacherReviewApiCall.Failure(errorMessage = "Error Connecting to the Server")
    }

    // This calls the API and fetches detailed Reviews History of a particular Student
    suspend fun getStudentReviewHistory(
        limitValue: Int,
        studentId: String
    ): GetHistoryApiCallState {

        // This is the Response from the Server
        val response = RetrofitInstance.apiInstance.getStudentReviewHistory(
            studentId = studentId,
            limitValue = limitValue,
            token = getToken()
        )

        // Checking if the Response is successful or a Failure
        return if (response.isSuccessful)
            GetHistoryApiCallState.Success(reviewData = response.body()!!)
        else
            GetHistoryApiCallState.Failure(errorMessage = "Error Connecting to the Server")
    }

    // This function posts the Review Data to the database
    suspend fun postReviewData(postData: ReviewPostData): AddReviewApiState {

        // This is the Response from the Server
        val response =
            RetrofitInstance.apiInstance.postTeacherReviews(
                post = postData,
                token = getToken()
            )

        // Checking if the Response is successful or a Failure
        return if (response.isSuccessful)
            AddReviewApiState.Success(response.body()!!)
        else
            AddReviewApiState.Failure(errorMessage = "Error Connecting to the Server")
    }
}
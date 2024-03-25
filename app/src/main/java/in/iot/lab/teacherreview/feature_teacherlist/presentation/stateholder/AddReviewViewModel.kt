package `in`.iot.lab.teacherreview.feature_teacherlist.presentation.stateholder

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.iot.lab.teacherreview.feature_teacherlist.data.model.IndividualFacultyData
import `in`.iot.lab.teacherreview.feature_teacherlist.data.model.RatingData
import `in`.iot.lab.teacherreview.feature_teacherlist.data.model.RatingParameterData
import `in`.iot.lab.teacherreview.feature_teacherlist.data.model.ReviewPostData
import `in`.iot.lab.teacherreview.feature_teacherlist.data.repository.Repository
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.state_action.AddReviewAction
import `in`.iot.lab.teacherreview.feature_teacherlist.presentation.state_action.ReviewState
import `in`.iot.lab.teacherreview.feature_teacherlist.utils.AddReviewApiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class AddReviewViewModel @Inject constructor(
    private val myRepository: Repository
) : ViewModel() {

    private val _userInputReview = MutableStateFlow(ReviewState())
    val userInputReview = _userInputReview.asStateFlow()

    lateinit var selectedTeacherId: IndividualFacultyData
        private set

    var addReviewApiState: AddReviewApiState by mutableStateOf(AddReviewApiState.Initialized)
        private set

    /**
     * This function updates the user Input Marking Rating variable
     *
     * @param flag
     * If the flag is 1 then it increases otherwise it decreases the variable
     */
    fun updateUserInputMarkingRating(flag: Int) {
        if (flag == 1 && userInputReview.value.markingRating < 5)
            _userInputReview.value = _userInputReview.value.copy(markingRating = _userInputReview.value.markingRating + 1)
        if (flag == 0 && userInputReview.value.markingRating > 0)
            _userInputReview.value = _userInputReview.value.copy(markingRating = _userInputReview.value.markingRating - 1)
    }
    /**
     * This function updates the user Input Attendance Rating variable
     *
     * @param flag
     * If the flag is 1 then it increases otherwise it decreases the variable
     */
    fun updateUserInputAttendanceRating(flag: Int) {
        if (flag == 1 && _userInputReview.value.attendanceRating < 5)
            _userInputReview.value = _userInputReview.value.copy(attendanceRating = _userInputReview.value.attendanceRating + 1)
        if (flag == 0 && _userInputReview.value.attendanceRating > 0)
            _userInputReview.value = _userInputReview.value.copy(attendanceRating = _userInputReview.value.attendanceRating - 1)
    }

    /**
     * This function updates the user Input Teaching Rating variable
     *
     * @param flag
     * If the flag is 1 then it increases otherwise it decreases the variable
     */
    fun updateUserInputTeachingRating(flag: Int) {
        if (flag == 1 && _userInputReview.value.teachingRating < 5)
            _userInputReview.value = _userInputReview.value.copy(teachingRating = _userInputReview.value.teachingRating + 1)
        if (flag == 0 && _userInputReview.value.teachingRating > 0)
            _userInputReview.value = _userInputReview.value.copy(teachingRating = _userInputReview.value.teachingRating - 1)
    }

    fun updateOverallReview(newValue: String) {
        _userInputReview.value = _userInputReview.value.copy(overallReview = newValue)
    }

    fun updateMarkingReview(newValue: String) {
        _userInputReview.value = _userInputReview.value.copy(markingReview = newValue)
    }

    fun updateAttendanceReview(newValue: String) {
        _userInputReview.value = _userInputReview.value.copy(attendanceReview = newValue)
    }

    fun updateTeachingReview(newValue: String) {
        _userInputReview.value = _userInputReview.value.copy(teachingReview = newValue)
    }

    fun setTeacherId(teacherId: IndividualFacultyData) {
        selectedTeacherId = teacherId
    }


    // Resets all the values to default
    fun resetToDefault() {
        _userInputReview.value = _userInputReview.value.copy(
            attendanceRating = 1.0,
            markingRating = 1.0,
            teachingRating = 1.0,
            overallReview = "",
            attendanceReview = "",
            markingReview = "",
            teachingReview = ""
        )
        addReviewApiState = AddReviewApiState.Initialized
    }

    // Reset only the Api State to default
    fun resetApiToInitialize() {
        addReviewApiState = AddReviewApiState.Initialized
    }

    // This function posts the Review Data to the database
    fun postReviewData() {

        // Setting the api state to loading
        addReviewApiState = AddReviewApiState.Loading

        // Checking if the Overall Review is given or not
        if (userInputReview.value.overallReview.isEmpty()) {
            addReviewApiState =
                AddReviewApiState.Failure("Need to Fill the Overall Rating at least")
            return
        }

        // posting the data to the Database
        viewModelScope.launch {

            // Creating the RatingData model object to be passed to the retrofit for posting
            val ratingData = RatingData(
                teachingRating = RatingParameterData(
                    ratedPoints = _userInputReview.value.teachingRating,
                    description = _userInputReview.value.teachingReview
                ),
                markingRating = RatingParameterData(
                    ratedPoints = _userInputReview.value.markingRating,
                    description = _userInputReview.value.markingReview
                ),
                attendanceRating = RatingParameterData(
                    ratedPoints = _userInputReview.value.attendanceRating,
                    description = _userInputReview.value.attendanceReview
                )
            )

            // The Actual post data that will be sent to the Database
            val postData = ReviewPostData(
                review = _userInputReview.value.overallReview,
                rating = ratingData,
                faculty = selectedTeacherId._id
            )

            // CHanging the State of the Api Accordingly
            addReviewApiState = try {
                myRepository.postReviewData(postData)
            } catch (_: ConnectException) {
                AddReviewApiState.Failure("No Internet Connection")
            }
        }
    }

    fun action(action : AddReviewAction){
        when(action){
            AddReviewAction.PostReviewData -> postReviewData()
            AddReviewAction.ResetApiToInitialize -> resetApiToInitialize()
            AddReviewAction.ResetToDefault -> resetToDefault()
            is AddReviewAction.SetTeacherId -> setTeacherId(action.teacherId)
            is AddReviewAction.UpdateAttendanceReview -> updateAttendanceReview(action.review)
            is AddReviewAction.UpdateMarkingReview -> updateMarkingReview(action.review)
            is AddReviewAction.UpdateOverallReview -> updateOverallReview(action.review)
            is AddReviewAction.UpdateTeachingReview -> updateTeachingReview(action.review)
            is AddReviewAction.UpdateUserInputAttendanceRating -> updateUserInputAttendanceRating(action.flag)
            is AddReviewAction.UpdateUserInputMarkingRating -> updateUserInputMarkingRating(action.flag)
            is AddReviewAction.UpdateUserInputTeachingRating -> updateUserInputTeachingRating(action.flag)
        }
    }

}
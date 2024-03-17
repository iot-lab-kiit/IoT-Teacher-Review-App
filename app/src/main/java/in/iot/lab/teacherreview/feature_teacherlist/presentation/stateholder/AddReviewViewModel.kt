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
import `in`.iot.lab.teacherreview.feature_teacherlist.utils.AddReviewApiState
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class AddReviewViewModel @Inject constructor(
    private val myRepository: Repository
) : ViewModel() {

    var userInputMarkingRating: Double by mutableStateOf(1.0)
        private set

    var userInputAttendanceRating: Double by mutableStateOf(1.0)
        private set

    var userInputTeachingRating: Double by mutableStateOf(1.0)
        private set

    var userInputOverallReview: String by mutableStateOf("")
        private set

    var userInputMarkingReview: String by mutableStateOf("")
        private set

    var userInputAttendanceReview: String by mutableStateOf("")
        private set

    var userInputTeachingReview: String by mutableStateOf("")
        private set

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
        if (flag == 1 && userInputMarkingRating < 5)
            userInputMarkingRating++
        if (flag == 0 && userInputMarkingRating > 0)
            userInputMarkingRating--
    }

    /**
     * This function updates the user Input Attendance Rating variable
     *
     * @param flag
     * If the flag is 1 then it increases otherwise it decreases the variable
     */
    fun updateUserInputAttendanceRating(flag: Int) {
        if (flag == 1 && userInputAttendanceRating < 5)
            userInputAttendanceRating++
        if (flag == 0 && userInputAttendanceRating > 0)
            userInputAttendanceRating--
    }

    /**
     * This function updates the user Input Teaching Rating variable
     *
     * @param flag
     * If the flag is 1 then it increases otherwise it decreases the variable
     */
    fun updateUserInputTeachingRating(flag: Int) {
        if (flag == 1 && userInputTeachingRating < 5)
            userInputTeachingRating++
        if (flag == 0 && userInputTeachingRating > 0)
            userInputTeachingRating--
    }

    fun updateOverallReview(newValue: String) {
        userInputOverallReview = newValue
    }

    fun updateMarkingReview(newValue: String) {
        userInputMarkingReview = newValue
    }

    fun updateAttendanceReview(newValue: String) {
        userInputAttendanceReview = newValue
    }

    fun updateTeachingReview(newValue: String) {
        userInputTeachingReview = newValue
    }

    fun setTeacherId(teacherId: IndividualFacultyData) {
        selectedTeacherId = teacherId
    }


    // Resets all the values to default
    fun resetToDefault() {
        userInputTeachingRating = 0.0
        userInputMarkingRating = 0.0
        userInputAttendanceRating = 0.0


        userInputOverallReview = ""
        userInputAttendanceReview = ""
        userInputTeachingReview = ""
        userInputMarkingReview = ""

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
        if (userInputOverallReview.isEmpty()) {
            addReviewApiState =
                AddReviewApiState.Failure("Need to Fill the Overall Rating at least")
            return
        }

        // posting the data to the Database
        viewModelScope.launch {

            // Creating the RatingData model object to be passed to the retrofit for posting
            val ratingData = RatingData(
                teachingRating = RatingParameterData(
                    ratedPoints = userInputTeachingRating,
                    description = userInputTeachingReview
                ),
                markingRating = RatingParameterData(
                    ratedPoints = userInputMarkingRating,
                    description = userInputMarkingReview
                ),
                attendanceRating = RatingParameterData(
                    ratedPoints = userInputAttendanceRating,
                    description = userInputAttendanceReview
                )
            )

            // The Actual post data that will be sent to the Database
            val postData = ReviewPostData(
                review = userInputOverallReview,
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
            AddReviewAction.ReturnTeacherName -> selectedTeacherId.name
            AddReviewAction.ReturnUserInputMarkingRating -> userInputMarkingRating
            AddReviewAction.ReturnUserInputAttendanceRating -> userInputAttendanceRating
            AddReviewAction.ReturnUserInputTeachingRating -> userInputTeachingRating
        }
    }

}
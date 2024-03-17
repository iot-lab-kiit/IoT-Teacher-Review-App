package `in`.iot.lab.teacherreview.feature_teacherlist.presentation.state_action
import `in`.iot.lab.teacherreview.feature_teacherlist.data.model.IndividualFacultyData

sealed class AddReviewAction {
    data class UpdateUserInputMarkingRating(val flag : Int) : AddReviewAction()
    data class UpdateUserInputAttendanceRating(val flag : Int) : AddReviewAction()
    data class UpdateUserInputTeachingRating(val flag : Int) : AddReviewAction()
    data class UpdateOverallReview(val review : String) : AddReviewAction()
    data class UpdateMarkingReview(val review : String) : AddReviewAction()
    data class UpdateAttendanceReview(val review : String) : AddReviewAction()
    data class UpdateTeachingReview(val review : String) : AddReviewAction()
    data class SetTeacherId(val teacherId : IndividualFacultyData) : AddReviewAction()
    data object ResetToDefault : AddReviewAction()
    data object ResetApiToInitialize : AddReviewAction()
    data object PostReviewData : AddReviewAction()
    data object ReturnTeacherName : AddReviewAction()
    data object ReturnUserInputMarkingRating : AddReviewAction()
    data object ReturnUserInputAttendanceRating : AddReviewAction()
    data object ReturnUserInputTeachingRating : AddReviewAction()

}
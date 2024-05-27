package `in`.iot.lab.teacherreview.domain.models.review


data class PostReviewBody(
    val id: String,
    val createdBy: String?,
    val createdFor: String?,
    val rating: Double?,
    val feedback: String?
)
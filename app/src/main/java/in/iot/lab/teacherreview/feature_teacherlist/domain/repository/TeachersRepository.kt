package `in`.iot.lab.teacherreview.feature_teacherlist.domain.repository

import `in`.iot.lab.teacherreview.feature_teacherlist.domain.models.remote.Faculty

interface TeachersRepository {
    suspend fun getAllTeachers(limitValue: Int, searchQuery: String?): Result<List<Faculty>>

    // TODO: To be added in the next release (maybe ?) only allowed for admins
    // suspend fun addTeacher(teacher: FacultyData): Result<FacultyData>
    // suspend fun updateTeacher(teacherId: String, teacher: FacultyData): Result<FacultyData>
    // suspend fun deleteTeacher(teacherId: String): Result<Boolean>
}
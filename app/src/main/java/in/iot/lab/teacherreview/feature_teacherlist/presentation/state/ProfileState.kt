package `in`.iot.lab.teacherreview.feature_teacherlist.presentation.state

sealed class ProfileState{

    data object getCurrentUser : ProfileState()

    data object signOut : ProfileState()

}
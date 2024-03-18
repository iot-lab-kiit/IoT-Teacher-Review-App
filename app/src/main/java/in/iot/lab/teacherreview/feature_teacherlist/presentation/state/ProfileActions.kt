package `in`.iot.lab.teacherreview.feature_teacherlist.presentation.state

sealed class ProfileActions{

    data object GetCurrentUser : ProfileActions()
    data object SignOut : ProfileActions()


}
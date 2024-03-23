package `in`.iot.lab.teacherreview.feature_teacherlist.presentation.action

sealed class ProfileActions{

    data object GetCurrentUser : ProfileActions()
    data object SignOut : ProfileActions()


}
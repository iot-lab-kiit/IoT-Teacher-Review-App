package `in`.iot.lab.teacherreview.feature_teacherlist.ui.action

sealed class ProfileActions{

    data object GetCurrentUser : ProfileActions()
    data object SignOut : ProfileActions()


}
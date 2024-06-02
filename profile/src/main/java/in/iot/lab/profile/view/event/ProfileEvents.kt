package `in`.iot.lab.profile.view.event

sealed class ProfileEvents {

    data object FetchUserData : ProfileEvents()
    data object SignOutEvent : ProfileEvents()
    data object DeleteAccountEvent : ProfileEvents()
    data object ResetLogOutState : ProfileEvents()
}
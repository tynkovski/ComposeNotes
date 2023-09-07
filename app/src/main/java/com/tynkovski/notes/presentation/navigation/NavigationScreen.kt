package com.tynkovski.notes.presentation.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

abstract class NavigationScreen(val route: String)

object SignInScreen : NavigationScreen("signIn")

object SignUpScreen : NavigationScreen("signUp")

object RemoteDetailNoteScreen : NavigationScreen("remoteDetail?noteId={noteId}") {
    fun route(noteId: String) = "remoteDetail?${argument.name}=$noteId"

    val argument: NamedNavArgument = navArgument(name = "noteId") {
        nullable = false
        type = NavType.StringType
    }
}

object LocalDetailNoteScreen : NavigationScreen("localDetail?noteId={noteId}") {
    fun route(noteId: String) = "localDetail?${argument.name}=$noteId"

    val argument: NamedNavArgument = navArgument(name = "noteId") {
        nullable = false
        type = NavType.StringType
    }
}


object NewRemoteNoteScreen : NavigationScreen("newRemoteNote")

object NewLocalNoteScreen : NavigationScreen("newLocalNote")
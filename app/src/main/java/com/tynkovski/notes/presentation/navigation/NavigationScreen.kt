package com.tynkovski.notes.presentation.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

abstract class NavigationScreen(val route: String)

object SignInScreen : NavigationScreen("signIn")

object SignUpScreen : NavigationScreen("signUp")

object DetailNoteScreen : NavigationScreen("detail?noteId={noteId}") {
    fun route(noteId: String) = "detail?${argument.name}=$noteId"

    val argument: NamedNavArgument = navArgument(name = "noteId") {
        nullable = false
        type = NavType.StringType
    }
}

object NewNoteScreen : NavigationScreen("newNote")
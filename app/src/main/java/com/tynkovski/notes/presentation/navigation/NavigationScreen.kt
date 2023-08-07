package com.tynkovski.notes.presentation.navigation

abstract class NavigationScreen(val route: String)

object SignInScreen : NavigationScreen("signIn")
object SignUpScreen : NavigationScreen("signUp")
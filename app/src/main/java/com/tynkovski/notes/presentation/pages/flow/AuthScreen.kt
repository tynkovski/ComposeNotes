package com.tynkovski.notes.presentation.pages.flow

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tynkovski.notes.presentation.navigation.SignInScreen
import com.tynkovski.notes.presentation.navigation.SignUpScreen
import com.tynkovski.notes.presentation.pages.authorization.signIn.SignInScreen
import com.tynkovski.notes.presentation.pages.authorization.signUp.SignUpScreen

// region Reusable
private val modifierMaxSize = Modifier.fillMaxSize()
// endregion

@Composable
fun AuthScreen() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SignInScreen.route,
        modifier = modifierMaxSize
    ) {
        composable(route = SignInScreen.route) {
            SignInScreen(
                controller = navController,
                modifier = modifierMaxSize
            )
        }

        composable(route = SignUpScreen.route) {
            SignUpScreen(
                modifier = modifierMaxSize,
                controller = navController,
            )
        }
    }
}
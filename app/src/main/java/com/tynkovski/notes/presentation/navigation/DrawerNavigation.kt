package com.tynkovski.notes.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tynkovski.notes.R

abstract class DrawerNavigation(
    route: String,
    @DrawableRes val image: Int,
    @StringRes val title: Int
) : NavigationScreen(
    route = route,
) {
    companion object {
        val drawerScreens = listOf(RemoteNotesScreen, SettingsScreen)

        fun getFromRoute(route: String?): DrawerNavigation? {
            return drawerScreens.find { it.route == route }
        }
    }
}

object RemoteNotesScreen : DrawerNavigation(
    route = "remoteNotes",
    image = R.drawable.ic_text_snipped,
    title = R.string.remote_notes_title,
)

object SettingsScreen : DrawerNavigation(
    route = "settings",
    image = R.drawable.ic_settings,
    title = R.string.settings_title,
)
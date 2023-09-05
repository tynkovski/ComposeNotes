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
        val drawerScreens = listOf(NotesScreen, SettingsScreen)

        fun getFromRoute(route: String?): DrawerNavigation? {
            return drawerScreens.find { it.route == route }
        }
    }
}

object NotesScreen : DrawerNavigation(
    route = "notes",
    image = R.drawable.ic_text_snipped,
    title = R.string.notes_title,
)

object SettingsScreen : DrawerNavigation(
    route = "settings",
    image = R.drawable.ic_settings,
    title = R.string.settings_title,
)
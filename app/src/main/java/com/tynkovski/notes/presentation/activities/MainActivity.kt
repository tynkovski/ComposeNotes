package com.tynkovski.notes.presentation.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.tynkovski.notes.presentation.components.anim.AnimatedFade
import com.tynkovski.notes.presentation.components.markdown.MarkDownEdit
import com.tynkovski.notes.presentation.pages.flow.AuthScreen
import com.tynkovski.notes.presentation.pages.flow.MainScreen
import com.tynkovski.notes.presentation.theme.NotesTheme
import com.tynkovski.notes.presentation.utils.EdgeToEdgeActivity
import com.tynkovski.notes.presentation.utils.ext.clearingFocusClickable
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.orbitmvi.orbit.compose.collectAsState

class MainActivity : EdgeToEdgeActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel = getViewModel<MainViewModel>()
            val state by viewModel.collectAsState()

            NotesTheme {
                AnimatedFade(state.hasToken) {
                    if (it) {
                        MainScreen(
                            logout = viewModel::logout
                        )
                    } else {
                        AuthScreen(
                            login = viewModel::login
                        )
                    }
                }
            }
        }
    }
}
package com.tynkovski.notes.presentation.pages.authorization.signIn

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.tynkovski.notes.R
import com.tynkovski.notes.presentation.components.button.ButtonState
import com.tynkovski.notes.presentation.components.button.DefaultButton
import com.tynkovski.notes.presentation.components.button.DefaultOutlinedButton
import com.tynkovski.notes.presentation.navigation.SignUpScreen
import com.tynkovski.notes.presentation.pages.authorization.components.AuthField
import com.tynkovski.notes.presentation.pages.authorization.components.SignBase
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

// region Reusable
private val modifierMaxSize = Modifier.fillMaxSize()
private val modifierMaxWidth = Modifier.fillMaxWidth()
private val paddingModifier = modifierMaxWidth.padding(horizontal = 16.dp)
// endregion

private const val LOGIN_TAB = 0
private const val PASSWORD_TAB = 1

@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
fun SignInScreen(
    controller: NavController,
    modifier: Modifier,
    login: (token: String) -> Unit
) {
    val viewModel = getViewModel<SignInViewModel>()
    val state by viewModel.collectAsState()

    val input by viewModel.input.collectAsState()
    val keyboardVisible = WindowInsets.isImeVisible

    val coroutineScope = rememberCoroutineScope()

    val pagerState = rememberPagerState()

    HorizontalPager(
        modifier = modifierMaxSize,
        state = pagerState,
        pageCount = 2,
        userScrollEnabled = false,
    ) {
        fun nextPage() {
            coroutineScope.launch { pagerState.animateScrollToPage(it + 1) }
        }

        fun previousPage() {
            coroutineScope.launch { pagerState.animateScrollToPage(it - 1) }
        }

        fun signUp() {
            controller.navigate(route = SignUpScreen.route) {
                popUpTo(
                    controller.graph.findStartDestination().id
                ) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }

        when (it) {
            LOGIN_TAB -> SignInLogin(
                modifier = modifier,
                input = input.login,
                inputChanged = viewModel::inputLoginChanged,
                nextScreenClick = ::nextPage,
                signUpClick = ::signUp,
                requestFocus = keyboardVisible
            )

            PASSWORD_TAB -> SignInPassword(
                modifier = modifier,
                input = input.password,
                inputChanged = viewModel::inputPasswordChanged,
                backClick = ::previousPage,
                signInClick = viewModel::signIn,
                requestFocus = keyboardVisible,
                buttonState = state.buttonState
            )
        }
    }

    viewModel.collectSideEffect {
        when (it) {
            is SignInViewModel.SideEffect.UserLogin -> login(it.token)
        }
    }
}

@Composable
private fun SignInLogin(
    modifier: Modifier,
    input: TextFieldValue,
    inputChanged: (TextFieldValue) -> Unit,
    nextScreenClick: () -> Unit,
    signUpClick: () -> Unit,
    requestFocus: Boolean
) = SignBase(
    modifier = modifier,
    showIcon = false,
    backClick = {}
) {
    Spacer(
        modifier = Modifier
            .heightIn(min = 56.dp)
            .weight(1f)
    )

    Text(
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(horizontal = 16.dp),
        style = MaterialTheme.typography.displaySmall.copy(
            fontWeight = FontWeight.Thin,
        ),
        text = stringResource(R.string.sign_in_login_title),
    )

    Spacer(
        modifier = Modifier
            .heightIn(min = 56.dp)
            .weight(1f)
    )

    Column(
        modifier = paddingModifier,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = modifierMaxWidth,
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Light
            ),
            text = stringResource(R.string.sign_in_login_description),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.outline
        )

        Spacer(modifier = Modifier.height(8.dp))

        AuthField(
            modifier = modifierMaxWidth,
            value = input,
            onValueChange = inputChanged,
            hint = stringResource(R.string.auth_login_hint),
            leading = ImageVector.vectorResource(id = R.drawable.ic_person_search),
            requestFocus = requestFocus
        )

        Spacer(modifier = Modifier.height(28.dp))

        val enabledButton by remember(input) {
            derivedStateOf { input.text.isNotEmpty() }
        }

        DefaultButton(
            modifier = modifierMaxWidth,
            onClick = nextScreenClick,
            text = stringResource(R.string.auth_button_continue),
            enabled = enabledButton,
        )
        Text(
            modifier = modifierMaxWidth,
            style = MaterialTheme.typography.bodyLarge,
            text = stringResource(R.string.auth_title_variant),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.outline
        )
        DefaultOutlinedButton(
            modifier = modifierMaxWidth,
            onClick = signUpClick,
            text = stringResource(R.string.auth_title_sign_up),
            enabled = true
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun SignInPassword(
    modifier: Modifier,
    input: TextFieldValue,
    inputChanged: (TextFieldValue) -> Unit,
    backClick: () -> Unit,
    signInClick: () -> Unit,
    requestFocus: Boolean,
    buttonState: ButtonState,
) = SignBase(
    modifier = modifier,
    backClick = backClick,
    showIcon = true
) {
    BackHandler(enabled = true, onBack = backClick)

    Spacer(
        modifier = Modifier
            .weight(1f)
            .heightIn(min = 56.dp)
    )

    Column(
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(vertical = 56.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.End
    ) {
        Text(
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.Thin,
            ),
            text = stringResource(R.string.sign_in_password_title),
            textAlign = TextAlign.Center
        )

        Text(
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Light),
            text = stringResource(R.string.sign_in_password_title_second),
        )
    }

    Spacer(
        modifier = Modifier
            .weight(1f)
            .heightIn(min = 56.dp)
    )

    Column(
        modifier = paddingModifier,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = modifierMaxWidth,
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Light
            ),
            text = stringResource(R.string.sign_in_password_description),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.outline
        )

        Spacer(modifier = Modifier.height(8.dp))

        val (hiddenInput, hiddenInputChanged) = remember {
            mutableStateOf(true)
        }
        val visible = ImageVector.vectorResource(R.drawable.ic_visibility)
        val invisible = ImageVector.vectorResource(R.drawable.ic_visibility_off)

        AuthField(
            modifier = modifierMaxWidth,
            value = input,
            onValueChange = inputChanged,
            hint = stringResource(R.string.auth_password_hint),
            hiddenEnter = hiddenInput,
            trailing = if (hiddenInput) visible else invisible,
            onTrailingIconClick = {
                hiddenInputChanged(!hiddenInput)
            },
            leading = ImageVector.vectorResource(id = R.drawable.ic_lock),
            requestFocus = requestFocus
        )

        Spacer(modifier = Modifier.height(28.dp))

        val enabledButton by remember(input) {
            derivedStateOf { input.text.isNotEmpty() }
        }

        DefaultButton(
            modifier = modifierMaxWidth,
            onClick = signInClick,
            text = stringResource(R.string.sign_in_button_enter),
            enabled = enabledButton,
            state = buttonState
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}
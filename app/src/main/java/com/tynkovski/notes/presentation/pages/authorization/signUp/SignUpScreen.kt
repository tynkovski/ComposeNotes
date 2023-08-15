package com.tynkovski.notes.presentation.pages.authorization.signUp

import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
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
import com.tynkovski.notes.presentation.components.button.DefaultButton
import com.tynkovski.notes.presentation.components.button.DefaultOutlinedButton
import com.tynkovski.notes.presentation.navigation.SignInScreen
import com.tynkovski.notes.presentation.pages.authorization.components.AuthField
import com.tynkovski.notes.presentation.pages.authorization.components.SignBase
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import org.orbitmvi.orbit.compose.collectAsState

// region Reusable
private val maxModifier = Modifier.fillMaxSize()
private val widthModifier = Modifier.fillMaxWidth()
private val paddingModifier = widthModifier.padding(horizontal = 16.dp)
// endregion

private const val LOGIN_TAB = 0
private const val NAME_TAB = 1
private const val PASSWORD_TAB = 2

@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
fun SignUpScreen(
    controller: NavController,
    modifier: Modifier,
) {
    val viewModel = getViewModel<SignUpViewModel>()
    val state by viewModel.collectAsState()

    val input by viewModel.input.collectAsState()
    val keyboardVisible = WindowInsets.isImeVisible
    val coroutineScope = rememberCoroutineScope()

    val pagerState = rememberPagerState()

    fun navigateNext(index: Int) = coroutineScope.launch {
        pagerState.animateScrollToPage(index + 1)
    }

    fun navigateBack(index: Int) = coroutineScope.launch {
        pagerState.animateScrollToPage(index - 1)
    }

    HorizontalPager(
        modifier = maxModifier,
        state = pagerState,
        pageCount = 3,
        userScrollEnabled = false,
    ) {
        when (it) {
            LOGIN_TAB -> {
                SignUpLogin(
                    modifier = modifier,
                    backClick = { controller.popBackStack() },
                    nextScreenClick = { navigateNext(it) },
                    signInClick = {
                        controller.navigate(route = SignInScreen.route) {
                            popUpTo(
                                controller.graph.findStartDestination().id
                            ) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    loginInput = input.login,
                    loginInputChanged = viewModel::inputLoginChanged,
                    requestFocus = keyboardVisible
                )
            }

            NAME_TAB -> {
                SignUpName(
                    modifier = modifier,
                    backClick = { navigateBack(it) },
                    nextScreenClick = { navigateNext(it) },
                    nameInput = input.name,
                    nameInputChanged = viewModel::inputNameChanged,
                    requestFocus = keyboardVisible
                )
            }

            PASSWORD_TAB -> {
                SignUpPassword(
                    modifier = modifier,
                    backClick = { navigateBack(it) },
                    signUpClick = viewModel::signUp,
                    passwordInput = input.password,
                    passwordRepeatInput = input.repeatedPassword,
                    passwordInputChanged = viewModel::inputPasswordChanged,
                    passwordRepeatInputChanged = viewModel::inputRepeatedPasswordChanged,
                    requestFocus = keyboardVisible
                )
            }
        }
    }
}

@Composable
private fun SignUpLogin(
    modifier: Modifier,
    loginInput: TextFieldValue,
    loginInputChanged: (TextFieldValue) -> Unit,
    backClick: () -> Unit,
    nextScreenClick: () -> Unit,
    signInClick: () -> Unit,
    requestFocus: Boolean
) = SignBase(
    modifier = modifier,
    showIcon = false,
    backClick = backClick
) {
    Spacer(
        modifier = Modifier
            .weight(1f)
            .heightIn(min = 56.dp)
    )

    Text(
        modifier = paddingModifier,
        style = MaterialTheme.typography.displaySmall.copy(
            fontWeight = FontWeight.Thin,
        ),
        text = stringResource(R.string.sign_up_login_title),
        textAlign = TextAlign.Center,
    )

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
            modifier = widthModifier,
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Light
            ),
            text = stringResource(R.string.sign_up_login_description),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.outline,
        )

        Spacer(modifier = Modifier.height(8.dp))

        val supportTextDefault = stringResource(R.string.sign_up_login_field_description)
        val supportTextLength = stringResource(R.string.sign_up_login_field_description_length)

        fun getSupportText(value: TextFieldValue): String? {
            if (value.text.isEmpty()) return null
            if (!Regex(".{2,16}").matches(value.text)) return supportTextLength

            return if (Regex("[a-zA-Z0-9]+").matches(value.text)) null else supportTextDefault
        }

        val supportText by remember(loginInput) {
            derivedStateOf { getSupportText(loginInput) }
        }

        AuthField(
            modifier = widthModifier,
            value = loginInput,
            onValueChange = loginInputChanged,
            hint = stringResource(R.string.auth_login_hint),
            leading = ImageVector.vectorResource(id = R.drawable.ic_person_search),
            supportText = supportText,
            requestFocus = requestFocus
        )

        Spacer(modifier = Modifier.height(28.dp))

        val enabledButton by remember(loginInput) {
            derivedStateOf { loginInput.text.isNotEmpty() && getSupportText(loginInput) == null }
        }

        DefaultButton(
            modifier = widthModifier,
            onClick = nextScreenClick,
            text = stringResource(R.string.sign_up_login_button),
            enabled = enabledButton,
        )
        Text(
            modifier = widthModifier,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.outline,
            text = stringResource(R.string.auth_title_variant),
            textAlign = TextAlign.Center
        )
        DefaultOutlinedButton(
            modifier = widthModifier,
            onClick = signInClick,
            text = stringResource(R.string.auth_title_sign_in),
            enabled = true
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun SignUpName(
    modifier: Modifier,
    backClick: () -> Unit,
    nameInput: TextFieldValue,
    nameInputChanged: (TextFieldValue) -> Unit,
    nextScreenClick: () -> Unit,
    requestFocus: Boolean
) = SignBase(
    modifier = modifier,
    showIcon = true,
    backClick = backClick
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
            text = stringResource(R.string.sign_up_name_title),
        )

        Text(
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Light
            ),
            text = stringResource(R.string.sign_up_name_title_second),
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
            modifier = widthModifier,
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Light
            ),
            text = stringResource(R.string.sign_up_name_description),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.outline
        )

        Spacer(modifier = Modifier.height(8.dp))

        val supportTextDefault = stringResource(R.string.sign_up_name_field_description)

        val supportText by remember(nameInput) {
            derivedStateOf { if (nameInput.text.isNotEmpty()) null else supportTextDefault }
        }

        AuthField(
            modifier = widthModifier,
            value = nameInput,
            onValueChange = nameInputChanged,
            hint = stringResource(R.string.auth_name_hint),
            leading = ImageVector.vectorResource(id = R.drawable.ic_person),
            requestFocus = requestFocus,
            supportText = supportText
        )

        Spacer(modifier = Modifier.height(28.dp))

        DefaultButton(
            modifier = widthModifier,
            onClick = nextScreenClick,
            text = stringResource(R.string.sign_up_name_button),
            enabled = true,
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}


@Composable
private fun SignUpPassword(
    modifier: Modifier,
    backClick: () -> Unit,
    passwordInput: TextFieldValue,
    passwordRepeatInput: TextFieldValue,
    passwordInputChanged: (TextFieldValue) -> Unit,
    passwordRepeatInputChanged: (TextFieldValue) -> Unit,
    signUpClick: () -> Unit,
    requestFocus: Boolean
) = SignBase(
    modifier = modifier,
    showIcon = true,
    backClick = backClick
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
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.Thin,
            ),
            text = stringResource(R.string.sign_up_password_title),
            textAlign = TextAlign.End
        )

        Text(
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Light),
            text = stringResource(R.string.sign_up_password_title_second),
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
            modifier = widthModifier,
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Light
            ),
            text = stringResource(R.string.sign_up_password_description),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.outline,
        )

        Spacer(modifier = Modifier.height(8.dp))
    }

    val supportTextSize = stringResource(R.string.sign_up_password_field_description_size)
    val supportTextDigits = stringResource(R.string.sign_up_password_field_description_digits)
    val supportTextCapitalized =
        stringResource(R.string.sign_up_password_field_description_capitalized)
    val supportTextWhitespaces =
        stringResource(R.string.sign_up_password_field_description_whitespaces)
    val supportMismatch = stringResource(R.string.sign_up_password_field_description_mismatch)

    val visible = ImageVector.vectorResource(R.drawable.ic_visibility)
    val invisible = ImageVector.vectorResource(R.drawable.ic_visibility_off)

    fun getSupportText(value: TextFieldValue): String? {
        if (value.text.isEmpty()) return null

        if (!Regex(".{8,32}").matches(value.text)) return supportTextSize
        if (Regex("\\D+").matches(value.text)) return supportTextDigits
        if (Regex("[^A-Z]+").matches(value.text)) return supportTextCapitalized
        if (!Regex("\\S+").matches(value.text)) return supportTextWhitespaces

        return null
    }

    val supportText by remember(passwordInput) {
        derivedStateOf { getSupportText(passwordInput) }
    }

    val (hiddenPasswordInput, hiddenPasswordInputChanged) = remember {
        mutableStateOf(true)
    }

    AuthField(
        modifier = paddingModifier,
        value = passwordInput,
        onValueChange = passwordInputChanged,
        hint = stringResource(R.string.auth_password_hint),
        leading = ImageVector.vectorResource(id = R.drawable.ic_lock),
        supportText = supportText,
        hiddenEnter = hiddenPasswordInput,
        trailing = if (hiddenPasswordInput) visible else invisible,
        onTrailingIconClick = {
            hiddenPasswordInputChanged(!hiddenPasswordInput)
        },
        requestFocus = requestFocus
    )

    Spacer(modifier = Modifier.height(12.dp))

    val (hiddenPasswordRepeatInput, hiddenPasswordRepeatInputChanged) = remember {
        mutableStateOf(true)
    }

    val passwordsAreEqual by remember(passwordInput, passwordRepeatInput) {
        derivedStateOf {
            passwordInput.text.isNotEmpty()
                    && passwordRepeatInput.text.isNotEmpty()
                    && passwordInput.text == passwordRepeatInput.text
        }
    }

    val repeatPasswordSupportText by remember(passwordInput, passwordRepeatInput) {
        derivedStateOf {
            if (passwordsAreEqual || passwordRepeatInput.text.isEmpty())
                null
            else
                supportMismatch
        }
    }

    AuthField(
        modifier = paddingModifier,
        value = passwordRepeatInput,
        onValueChange = passwordRepeatInputChanged,
        hint = stringResource(R.string.auth_repeat_password_hint),
        leading = ImageVector.vectorResource(id = R.drawable.ic_password),
        hiddenEnter = hiddenPasswordRepeatInput,
        trailing = if (hiddenPasswordRepeatInput) visible else invisible,
        onTrailingIconClick = {
            hiddenPasswordRepeatInputChanged(!hiddenPasswordRepeatInput)
        },
        supportText = repeatPasswordSupportText,
    )

    Spacer(modifier = Modifier.height(28.dp))

    DefaultButton(
        modifier = paddingModifier,
        onClick = signUpClick,
        text = stringResource(R.string.sign_up_password_button),
        enabled = passwordsAreEqual,
    )

    Spacer(modifier = Modifier.height(24.dp))
}
package com.tynkovski.notes.presentation.components.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tynkovski.notes.presentation.components.helpers.DisableMinimumTouch
import com.tynkovski.notes.presentation.theme.typography.defaultTypography

enum class ButtonState {
    Loading, Success, Error
}

@Composable
fun buttonColors(state: ButtonState) = when (state) {
    ButtonState.Loading -> ButtonDefaults.buttonColors()
    ButtonState.Success -> ButtonDefaults.buttonColors()
    ButtonState.Error -> ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.error,
    )
}

@Composable
fun DefaultButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = defaultTypography.labelMedium,
    textAlign: TextAlign = TextAlign.Center,
    shape: CornerBasedShape = MaterialTheme.shapes.large,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    disableMinimumTouch: Boolean = false,
    enabled: Boolean = true,
    state: ButtonState = ButtonState.Success,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    colors: ButtonColors = buttonColors(state),
) = DisableMinimumTouch(disableMinimumTouch) {
    Button(
        enabled = enabled,
        modifier = modifier,
        onClick = onClick,
        shape = shape,
        contentPadding = contentPadding,
        colors = colors,
    ) {
        when (state) {
            ButtonState.Loading -> {
                CircularProgressIndicator()
                //SpinningCircleProgressIndicator(durationMillis = 800)
            }

            ButtonState.Success -> {
                leadingIcon?.let {
                    Icon(
                        modifier = Modifier,
                        imageVector = it,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }

                Text(
                    modifier = Modifier,
                    text = text,
                    style = textStyle,
                    textAlign = textAlign
                )

                trailingIcon?.let {
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        modifier = Modifier,
                        imageVector = it,
                        contentDescription = null
                    )
                }
            }

            ButtonState.Error -> {
                leadingIcon?.let {
                    Icon(
                        modifier = Modifier,
                        imageVector = it,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }

                Text(
                    modifier = Modifier,
                    text = text,
                    style = textStyle,
                    textAlign = textAlign
                )
            }
        }
    }
}

@Composable
fun outlinedButtonColors(state: ButtonState) = when (state) {
    ButtonState.Loading -> ButtonDefaults.outlinedButtonColors()
    ButtonState.Success -> ButtonDefaults.outlinedButtonColors()
    ButtonState.Error -> ButtonDefaults.outlinedButtonColors(
        contentColor = MaterialTheme.colorScheme.error,
    )
}

@Composable
fun outlinedButtonBorder(state: ButtonState) = when (state) {
    ButtonState.Loading -> ButtonDefaults.outlinedButtonBorder
    ButtonState.Success -> ButtonDefaults.outlinedButtonBorder
    ButtonState.Error -> ButtonDefaults.outlinedButtonBorder.copy(
        brush = SolidColor(MaterialTheme.colorScheme.error)
    )
}

@Composable
fun DefaultOutlinedButton(
    modifier: Modifier,
    onClick: () -> Unit,
    text: String,
    textStyle: TextStyle = defaultTypography.labelMedium,
    textAlign: TextAlign = TextAlign.Center,
    shape: CornerBasedShape = MaterialTheme.shapes.large,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    disableMinimumTouch: Boolean = false,
    enabled: Boolean = true,
    state: ButtonState = ButtonState.Success,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    colors: ButtonColors = outlinedButtonColors(state),
) = DisableMinimumTouch(disableMinimumTouch) {
    OutlinedButton(
        enabled = enabled,
        modifier = modifier,
        onClick = onClick,
        shape = shape,
        contentPadding = contentPadding,
        colors = colors,
        border = outlinedButtonBorder(state),
    ) {
        when (state) {
            ButtonState.Loading -> {
                CircularProgressIndicator()
                // SpinningCircleProgressIndicator(durationMillis = 800)
            }

            ButtonState.Success -> {
                leadingIcon?.let {
                    Icon(
                        modifier = Modifier,
                        imageVector = it,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }

                Text(
                    modifier = Modifier,
                    text = text,
                    style = textStyle,
                    textAlign = textAlign
                )

                trailingIcon?.let {
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        modifier = Modifier,
                        imageVector = it,
                        contentDescription = null
                    )
                }
            }

            ButtonState.Error -> {
                Text(
                    modifier = Modifier,
                    text = text,
                    style = textStyle,
                    textAlign = textAlign
                )
            }
        }
    }
}
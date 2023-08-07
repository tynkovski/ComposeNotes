package com.tynkovski.notes.presentation.pages.authorization.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.tynkovski.notes.R
import com.tynkovski.notes.presentation.theme.typography.defaultTypography

// region Reusable
private val widthModifier = Modifier.fillMaxWidth()
private val fieldStyle = defaultTypography.labelLarge
// endregion

@Composable
fun AuthField(
    modifier: Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    hint: String,
    leading: ImageVector? = null,
    trailing: ImageVector? = null,
    onTrailingIconClick: () -> Unit = {},
    hiddenEnter: Boolean = false,
    enabled: Boolean = true,
    supportText: String? = null,
    isError: Boolean = false,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = MaterialTheme.colorScheme.primary,
        unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
        errorTextColor = MaterialTheme.colorScheme.error,
        errorSupportingTextColor = MaterialTheme.colorScheme.error,
        errorTrailingIconColor = MaterialTheme.colorScheme.error,
        errorLeadingIconColor = MaterialTheme.colorScheme.error,
    ),
    requestFocus: Boolean = false,
) {
    val hasText by remember(value) {
        derivedStateOf { value.text.isNotEmpty() }
    }

    val leadingIcon: (@Composable () -> Unit)? = leading?.let {
        {
            Icon(
                imageVector = it,
                contentDescription = null
            )
        }
    }

    val removeTextButton: (@Composable () -> Unit)? = if (hasText) {
        {
            IconButton(
                onClick = { onValueChange(TextFieldValue("")) }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_close),
                    contentDescription = null
                )
            }
        }
    } else null

    val trailingIcon: (@Composable () -> Unit)? = trailing?.let {
        {
            IconButton(
                onClick = onTrailingIconClick
            ) {
                Icon(
                    imageVector = it,
                    contentDescription = null
                )
            }
        }
    }

    val support: (@Composable () -> Unit)? = supportText?.let {
        {
            Text(
                modifier = widthModifier,
                text = supportText,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }

    val keyboardOptions = if (hiddenEnter) {
        KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
    } else {
        KeyboardOptions.Default
    }

    val visualTransformation = if (hiddenEnter) {
        PasswordVisualTransformation('●')
    } else {
        VisualTransformation.None
    }

    val focusRequester = remember { FocusRequester() }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.focusRequester(focusRequester),
        singleLine = true,
        visualTransformation = visualTransformation,
        shape = RoundedCornerShape(20.dp),
        textStyle = fieldStyle,
        placeholder = {
            Text(
                modifier = widthModifier,
                style = fieldStyle,
                text = hint,
                color = MaterialTheme.colorScheme.outline
            )
        },
        colors = colors,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon ?: removeTextButton,
        keyboardOptions = keyboardOptions,
        enabled = enabled,
        supportingText = support,
        isError = isError
    )

    LaunchedEffect(Unit) {
        if (requestFocus) {
            focusRequester.requestFocus()
        }
    }
}
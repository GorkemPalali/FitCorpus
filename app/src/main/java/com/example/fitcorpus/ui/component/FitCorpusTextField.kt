package com.example.fitcorpus.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.fitcorpus.ui.designsystem.theme.FitCorpusColors

@Composable
fun FitCorpusTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    isError: Boolean = false,
    errorMessage: String? = null,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        label?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            placeholder = placeholder?.let {
                {
                    Text(
                        text = it,
                        color = FitCorpusColors.Gray400
                    )
                }
            },
            singleLine = singleLine,
            enabled = enabled,
            isError = isError,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                focusedBorderColor = FitCorpusColors.Primary,
                unfocusedBorderColor = FitCorpusColors.Gray700,
                focusedContainerColor = FitCorpusColors.Surface.copy(alpha = 0.5f),
                unfocusedContainerColor = FitCorpusColors.Surface.copy(alpha = 0.5f),
                cursorColor = FitCorpusColors.Primary,
                errorBorderColor = MaterialTheme.colorScheme.error,
                errorContainerColor = MaterialTheme.colorScheme.errorContainer
            ),
            shape = MaterialTheme.shapes.medium
        )
        
        errorMessage?.let {
            if (isError) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}
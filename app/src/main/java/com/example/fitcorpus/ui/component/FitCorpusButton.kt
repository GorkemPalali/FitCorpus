package com.example.fitcorpus.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fitcorpus.ui.designsystem.theme.FitCorpusColors

enum class FitCorpusButtonType {
    Primary,
    Secondary,
    Outlined,
    Text
}

@Composable
fun FitCorpusButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    type: FitCorpusButtonType = FitCorpusButtonType.Primary,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    contentPadding: PaddingValues = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
) {
    when (type) {
        FitCorpusButtonType.Primary -> {
            Button(
                onClick = onClick,
                modifier = modifier
                    .height(56.dp),
                enabled = enabled && !isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = FitCorpusColors.Primary,
                    contentColor = FitCorpusColors.OnPrimary,
                    disabledContainerColor = FitCorpusColors.Primary.copy(alpha = 0.6f),
                    disabledContentColor = FitCorpusColors.OnPrimary.copy(alpha = 0.6f)
                ),
                shape = MaterialTheme.shapes.medium,
                contentPadding = contentPadding
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = FitCorpusColors.OnPrimary
                    )
                } else {
                    Text(text = text)
                }
            }
        }
        FitCorpusButtonType.Secondary -> {
            Button(
                onClick = onClick,
                modifier = modifier
                    .height(56.dp),
                enabled = enabled && !isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = FitCorpusColors.Secondary,
                    contentColor = FitCorpusColors.OnSecondary,
                    disabledContainerColor = FitCorpusColors.Secondary.copy(alpha = 0.6f),
                    disabledContentColor = FitCorpusColors.OnSecondary.copy(alpha = 0.6f)
                ),
                shape = MaterialTheme.shapes.medium,
                contentPadding = contentPadding
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = FitCorpusColors.OnSecondary
                    )
                } else {
                    Text(text = text)
                }
            }
        }
        FitCorpusButtonType.Outlined -> {
            OutlinedButton(
                onClick = onClick,
                modifier = modifier
                    .height(56.dp),
                enabled = enabled && !isLoading,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                border = ButtonDefaults.outlinedButtonBorder,
                shape = MaterialTheme.shapes.medium,
                contentPadding = contentPadding
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                } else {
                    Text(text = text)
                }
            }
        }
        FitCorpusButtonType.Text -> {
            TextButton(
                onClick = onClick,
                modifier = modifier,
                enabled = enabled && !isLoading,
                contentPadding = contentPadding
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                } else {
                    Text(text = text)
                }
            }
        }
    }
}
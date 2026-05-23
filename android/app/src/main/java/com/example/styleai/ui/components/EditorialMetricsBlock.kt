package com.example.styleai.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

data class EditorialMetricUi(
    val value: String,
    val label: String
)

@Composable
fun EditorialMetricsBlock(
    metrics: List<EditorialMetricUi>,
    modifier: Modifier = Modifier
) {
    if (metrics.isEmpty()) return

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(EditorBackground)
            .padding(vertical = 18.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            metrics.forEachIndexed { index, metric ->
                MetricColumn(
                    metric = metric,
                    modifier = Modifier.weight(1f)
                )
                if (index < metrics.lastIndex) {
                    Spacer(
                        modifier = Modifier
                            .width(1.dp)
                            .height(40.dp)
                            .background(EditorDivider)
                    )
                }
            }
        }
    }
}

@Composable
private fun MetricColumn(metric: EditorialMetricUi, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = metric.value,
            color = EditorPrimaryText,
            fontSize = 31.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = (-0.2).sp,
            maxLines = 1,
            textAlign = TextAlign.Center
        )
        Text(
            text = metric.label.uppercase(Locale.getDefault()),
            color = EditorSecondaryText,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 1.35.sp,
            maxLines = 1,
            textAlign = TextAlign.Center
        )
    }
}

private val EditorBackground = Color(0xFFF5F5F7)
private val EditorPrimaryText = Color(0xFF222222)
private val EditorSecondaryText = Color(0xFF737373)
private val EditorDivider = Color(0xFFE5E5E5)

package com.example.styleai.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LuxuryTopAppBar(
    title: String? = null,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = LuxuryBarBackground
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .statusBarsPadding()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LuxuryBackButton(onClick = onBackClick)
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            if (!title.isNullOrBlank()) {
                Text(
                    text = title,
                    color = LuxuryBarText,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Spacer(modifier = Modifier.width(48.dp))
    }
}

@Composable
private fun LuxuryBackButton(onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()

    Box(
        modifier = Modifier
            .size(48.dp)
            .graphicsLayer { alpha = if (pressed) 0.45f else 1f }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(24.dp)) {
            val strokeWidth = 1.45.dp.toPx()
            drawLine(
                color = LuxuryBarText,
                start = Offset(size.width * 0.62f, size.height * 0.2f),
                end = Offset(size.width * 0.34f, size.height * 0.5f),
                strokeWidth = strokeWidth,
                cap = StrokeCap.Square
            )
            drawLine(
                color = LuxuryBarText,
                start = Offset(size.width * 0.34f, size.height * 0.5f),
                end = Offset(size.width * 0.62f, size.height * 0.8f),
                strokeWidth = strokeWidth,
                cap = StrokeCap.Square
            )
        }
    }
}

private val LuxuryBarBackground = Color(0xFFF5F5F7)
private val LuxuryBarText = Color(0xFF222222)

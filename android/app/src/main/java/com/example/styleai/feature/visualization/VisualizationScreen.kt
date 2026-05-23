package com.example.styleai.feature.visualization

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.styleai.data.mock.OutfitBoardAsset
import com.example.styleai.data.mock.VisualAssets
import com.example.styleai.domain.model.AppLanguage
import java.util.Locale

@Composable
fun VisualizationScreen(
    viewModel: VisualizationViewModel,
    onNavigateToPaywall: () -> Unit
) {
    val language by viewModel.selectedLanguage.collectAsState()
    var tab by remember { mutableStateOf("Today") }
    val tabs = listOf("Today", "Office", "Weekend", "Date", "Travel", "Saved")
    val boards = VisualAssets.outfitBoards.filter {
        tab == "Today" ||
            tab == "Saved" ||
            it.occasion.contains(tab, ignoreCase = true) ||
            (tab == "Date" && it.occasion.contains("Dinner", ignoreCase = true))
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(LuxuryBackground)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(22.dp)
    ) {
        item {
            LooksHeader(language = language)
        }
        item {
            MinimalTabRow(
                tabs = tabs,
                selectedTab = tab,
                language = language,
                onSelect = { tab = it }
            )
        }
        items(boards, key = { it.id }) { board ->
            OutfitBoardCard(
                board = board,
                language = language,
                onVisualize = onNavigateToPaywall
            )
        }
        item {
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
private fun LooksHeader(language: AppLanguage) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = if (language == AppLanguage.RU) "Образы" else "Looks",
            color = LuxuryPrimaryText,
            fontSize = 32.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = (-0.2).sp
        )
        Text(
            text = if (language == AppLanguage.RU) {
                "Идеи образов на основе вашего гардероба."
            } else {
                "Outfit ideas built from your wardrobe."
            },
            color = LuxurySecondaryText,
            fontSize = 15.sp,
            lineHeight = 22.sp
        )
    }
}

@Composable
private fun MinimalTabRow(
    tabs: List<String>,
    selectedTab: String,
    language: AppLanguage,
    onSelect: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(tabs) { item ->
            MinimalTabText(
                text = tabLabel(item, language),
                selected = selectedTab == item,
                onClick = { onSelect(item) }
            )
        }
    }
}

@Composable
private fun MinimalTabText(text: String, selected: Boolean, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = text,
            color = if (selected) LuxuryPrimaryText else LuxurySecondaryText,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Box(
            modifier = Modifier
                .height(1.dp)
                .width(if (selected) 28.dp else 0.dp)
                .background(if (selected) LuxuryPrimaryText else Color.Transparent)
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun OutfitBoardCard(board: OutfitBoardAsset, language: AppLanguage, onVisualize: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(BorderStroke(1.dp, LuxuryDivider), RectangleTwoDp)
            .background(LuxuryBackground),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        Image(
            painter = painterResource(board.drawableRes),
            contentDescription = if (language == AppLanguage.RU) board.titleRu else board.titleEn,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.8f)
                .clip(RectangleTwoDp),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = if (language == AppLanguage.RU) board.titleRu else board.titleEn.toEditorialTitle(),
                color = LuxuryPrimaryText,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                lineHeight = 25.sp
            )
            Text(
                text = "${board.occasion} · ${board.season} · ${board.formality}".uppercase(Locale.getDefault()),
                color = LuxurySecondaryText,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 1.15.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = board.itemIds.take(4).joinToString(" · ") { it.toEditorialTitle() },
                color = LuxurySecondaryText,
                fontSize = 13.sp,
                lineHeight = 19.sp
            )
            FlowRow(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                LuxuryOutlinedButton(
                    text = if (language == AppLanguage.RU) "Сохранить" else "Save",
                    onClick = {}
                )
                LuxuryOutlinedButton(
                    text = if (language == AppLanguage.RU) "Надеть сегодня" else "Wear today",
                    onClick = {}
                )
                LuxuryPrimaryButton(
                    text = if (language == AppLanguage.RU) "Визуальный референс" else "Visual reference",
                    onClick = onVisualize
                )
            }
            Text(
                text = if (language == AppLanguage.RU) "1 создание" else "1 reference use",
                color = LuxurySecondaryText,
                fontSize = 11.sp,
                letterSpacing = 0.6.sp
            )
        }
    }
}

@Composable
private fun LuxuryPrimaryButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RectangleTwoDp,
        colors = ButtonDefaults.buttonColors(
            containerColor = LuxuryPrimaryText,
            contentColor = Color.White
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            focusedElevation = 0.dp,
            hoveredElevation = 0.dp,
            disabledElevation = 0.dp
        )
    ) {
        Text(text = text, fontWeight = FontWeight.Medium, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}

@Composable
private fun LuxuryOutlinedButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        shape = RectangleTwoDp,
        border = BorderStroke(1.dp, LuxuryDivider),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = LuxuryPrimaryText
        )
    ) {
        Text(text = text, fontWeight = FontWeight.Medium, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}

private fun tabLabel(tab: String, language: AppLanguage): String {
    if (language != AppLanguage.RU) return tab
    return when (tab) {
        "Today" -> "Сегодня"
        "Office" -> "Офис"
        "Weekend" -> "Выходные"
        "Date" -> "Свидание"
        "Travel" -> "Поездка"
        "Saved" -> "Сохраненные"
        else -> tab
    }
}

private fun String.toEditorialTitle(): String {
    return split("-", "_", " ")
        .filter { it.isNotBlank() }
        .joinToString(" ") { word ->
            word.lowercase(Locale.getDefault()).replaceFirstChar { char ->
                if (char.isLowerCase()) char.titlecase(Locale.getDefault()) else char.toString()
            }
        }
}

private val LuxuryBackground = Color(0xFFF5F5F7)
private val LuxuryPrimaryText = Color(0xFF222222)
private val LuxurySecondaryText = Color(0xFF737373)
private val LuxuryDivider = Color(0xFFE5E5E5)
private val RectangleTwoDp = RoundedCornerShape(2.dp)

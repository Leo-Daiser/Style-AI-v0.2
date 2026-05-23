package com.example.styleai.feature.report

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.styleai.core.localization.AppLocalization
import com.example.styleai.core.localization.AppStrings
import com.example.styleai.domain.model.AppLanguage
import com.example.styleai.domain.model.ColorPalette
import com.example.styleai.domain.model.ShoppingItem
import com.example.styleai.domain.model.SilhouetteRecommendation
import com.example.styleai.domain.model.StyleDirection
import com.example.styleai.domain.model.StyleReport
import com.example.styleai.domain.model.WardrobeGap
import com.example.styleai.ui.components.LuxuryTopAppBar
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ReportScreen(
    reportState: StateFlow<StyleReport?>,
    selectedLanguage: StateFlow<AppLanguage>,
    onToggleGapCompleted: (String) -> Unit,
    onNavigateBack: () -> Unit
) {
    val report by reportState.collectAsState()
    val currentLanguage by selectedLanguage.collectAsState()
    val strings = AppLocalization.getStrings(currentLanguage)

    if (report == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(LuxuryBackground),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = LuxuryPrimaryText, strokeWidth = 2.dp)
        }
        return
    }

    val activeReport = report!!

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(LuxuryBackground)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            LuxuryTopAppBar(
                title = null,
                onBackClick = onNavigateBack,
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            ReportHeader(strings = strings, currentLanguage = currentLanguage)
        }
        item {
            PaletteSection(palette = activeReport.colorPalette, currentLanguage = currentLanguage, strings = strings)
        }
        item {
            SilhouettesSection(silhouettes = activeReport.silhouettes, currentLanguage = currentLanguage, strings = strings)
        }
        item {
            StyleDirectionsSection(directions = activeReport.styleDirections, strings = strings)
        }
        item {
            GapsChecklistSection(gaps = activeReport.wardrobeGaps, onToggleComplete = onToggleGapCompleted, strings = strings)
        }
        item {
            ShoppingListSection(items = activeReport.shoppingList, strings = strings)
        }
        item {
            AvoidSection(avoids = activeReport.whatToAvoid, strings = strings)
        }
        item {
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
private fun ReportHeader(strings: AppStrings, currentLanguage: AppLanguage) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = strings.reportTitle,
            color = LuxuryPrimaryText,
            fontFamily = FontFamily.Serif,
            fontSize = 34.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 39.sp
        )
        Text(
            text = if (currentLanguage == AppLanguage.EN) {
                "A private style dossier for palette, silhouettes, wardrobe gaps, and buying priorities."
            } else {
                "Персональное стиль-досье: палитра, силуэты, пробелы гардероба и приоритеты покупок."
            },
            color = LuxurySecondaryText,
            fontSize = 15.sp,
            lineHeight = 22.sp
        )
    }
}

@Composable
fun PaletteSection(palette: ColorPalette, currentLanguage: AppLanguage, strings: AppStrings) {
    DossierSection(label = strings.reportPaletteSection) {
        Text(
            text = palette.name,
            color = LuxuryPrimaryText,
            fontFamily = FontFamily.Serif,
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 36.sp
        )
        Text(
            text = palette.description,
            color = LuxurySecondaryText,
            fontSize = 14.sp,
            lineHeight = 21.sp
        )
        PaletteSwatches(colors = palette.swatchColors.take(6))
        Divider(color = LuxuryDivider, thickness = 1.dp)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(20.dp)) {
            EditorialMiniList(
                title = if (currentLanguage == AppLanguage.EN) "Prioritize" else "Приоритет",
                items = palette.priorityColors,
                modifier = Modifier.weight(1f)
            )
            EditorialMiniList(
                title = if (currentLanguage == AppLanguage.EN) "Use Carefully" else "С осторожностью",
                items = palette.avoidColors,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun PaletteSwatches(colors: List<String>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        colors.forEachIndexed { index, hexColor ->
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .offset(x = if (index == 0) 0.dp else (-4).dp)
                    .background(parseHexColor(hexColor), CircleShape)
                    .border(BorderStroke(1.dp, LuxuryDivider), CircleShape)
            )
        }
    }
}

@Composable
fun SilhouettesSection(
    silhouettes: List<SilhouetteRecommendation>,
    currentLanguage: AppLanguage,
    strings: AppStrings
) {
    DossierSection(label = strings.reportSilhouettesSection) {
        Text(
            text = if (currentLanguage == AppLanguage.EN) {
                "Proportional recommendations for a balanced wardrobe line."
            } else {
                "Рекомендации по пропорциям для сбалансированной линии гардероба."
            },
            color = LuxurySecondaryText,
            fontSize = 14.sp,
            lineHeight = 21.sp
        )
        EditorialList(
            items = silhouettes.map { EditorialListItem(it.title, it.description, it.category) }
        )
    }
}

@Composable
fun StyleDirectionsSection(directions: List<StyleDirection>, strings: AppStrings) {
    DossierSection(label = strings.reportDirectionsSection) {
        directions.forEachIndexed { index, direction ->
            EditorialDirectionItem(direction = direction)
            if (index < directions.lastIndex) {
                Divider(color = LuxuryDivider, thickness = 1.dp)
            }
        }
    }
}

@Composable
private fun EditorialDirectionItem(direction: StyleDirection) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = direction.title,
            color = LuxuryPrimaryText,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 22.sp
        )
        Text(
            text = direction.description,
            color = LuxurySecondaryText,
            fontSize = 14.sp,
            lineHeight = 21.sp
        )
        Text(
            text = direction.bestUseCases,
            color = LuxurySecondaryText,
            fontSize = 13.sp,
            lineHeight = 19.sp
        )
        Text(
            text = direction.recommendedItems.joinToString(" · "),
            color = LuxuryPrimaryText,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 19.sp
        )
    }
}

@Composable
fun GapsChecklistSection(gaps: List<WardrobeGap>, onToggleComplete: (String) -> Unit, strings: AppStrings) {
    DossierSection(label = strings.reportGapsSection) {
        gaps.forEachIndexed { index, gap ->
            var isChecked by remember(gap.id) { mutableStateOf(gap.isAcquired) }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        isChecked = !isChecked
                        onToggleComplete(gap.id)
                    }
                    .padding(vertical = 2.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MinimalCheckMark(checked = isChecked, modifier = Modifier.padding(top = 2.dp))
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = gap.itemName,
                        color = LuxuryPrimaryText,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 22.sp
                    )
                    Text(
                        text = "${gap.importance} · ${gap.description}",
                        color = LuxurySecondaryText,
                        fontSize = 14.sp,
                        lineHeight = 21.sp
                    )
                }
            }
            if (index < gaps.lastIndex) {
                Divider(color = LuxuryDivider, thickness = 1.dp)
            }
        }
    }
}

@Composable
private fun MinimalCheckMark(checked: Boolean, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.size(18.dp)) {
        val strokeWidth = 1.dp.toPx()
        drawCircle(
            color = LuxuryPrimaryText,
            radius = size.minDimension / 2f - strokeWidth,
            center = Offset(size.width / 2f, size.height / 2f),
            style = Stroke(width = strokeWidth)
        )
        if (checked) {
            drawCircle(
                color = LuxuryPrimaryText,
                radius = size.minDimension * 0.24f,
                center = Offset(size.width / 2f, size.height / 2f)
            )
        }
    }
}

@Composable
fun ShoppingListSection(items: List<ShoppingItem>, strings: AppStrings) {
    DossierSection(label = strings.reportShoppingSection) {
        items.forEachIndexed { index, shop ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "%02d".format(shop.priority),
                    color = LuxurySecondaryText,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.width(28.dp)
                )
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = shop.title,
                        color = LuxuryPrimaryText,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 22.sp
                    )
                    Text(
                        text = shop.stylingNotes,
                        color = LuxurySecondaryText,
                        fontSize = 14.sp,
                        lineHeight = 21.sp
                    )
                }
                Text(
                    text = shop.expectedPriceRange,
                    color = LuxurySecondaryText,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    textAlign = TextAlign.End,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.width(82.dp)
                )
            }
            if (index < items.lastIndex) {
                Divider(color = LuxuryDivider, thickness = 1.dp)
            }
        }
    }
}

@Composable
fun AvoidSection(avoids: List<String>, strings: AppStrings) {
    DossierSection(label = strings.reportAvoidSection) {
        Text(
            text = strings.reportBodySafetyNotice,
            color = LuxurySecondaryText,
            fontSize = 14.sp,
            lineHeight = 21.sp
        )
        Divider(color = LuxuryDivider, thickness = 1.dp)
        avoids.forEach { item ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "—",
                    color = LuxuryPrimaryText,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = item,
                    color = LuxurySecondaryText,
                    fontSize = 14.sp,
                    lineHeight = 21.sp,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun DossierSection(label: String, content: @Composable ColumnScope.() -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.Transparent,
        shape = RectangleTwoDp,
        tonalElevation = 0.dp,
        shadowElevation = 0.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(BorderStroke(1.dp, LuxuryDivider), RectangleTwoDp)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = label.uppercase(),
                color = LuxurySecondaryText,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 1.2.sp
            )
            Divider(color = LuxuryDivider, thickness = 1.dp)
            content()
        }
    }
}

@Composable
private fun EditorialList(items: List<EditorialListItem>) {
    items.forEachIndexed { index, item ->
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
                text = item.title,
                color = LuxuryPrimaryText,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 22.sp
            )
            Text(
                text = item.body,
                color = LuxurySecondaryText,
                fontSize = 14.sp,
                lineHeight = 21.sp
            )
            Text(
                text = item.meta.uppercase(),
                color = LuxurySecondaryText,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 1.1.sp
            )
        }
        if (index < items.lastIndex) {
            Divider(color = LuxuryDivider, thickness = 1.dp)
        }
    }
}

@Composable
private fun EditorialMiniList(title: String, items: List<String>, modifier: Modifier = Modifier) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            text = title,
            color = LuxuryPrimaryText,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 18.sp
        )
        items.forEach { item ->
            Text(
                text = item,
                color = LuxurySecondaryText,
                fontSize = 13.sp,
                lineHeight = 19.sp
            )
        }
    }
}

private data class EditorialListItem(
    val title: String,
    val body: String,
    val meta: String
)

private fun parseHexColor(hex: String): Color {
    return runCatching { Color(android.graphics.Color.parseColor(hex)) }.getOrDefault(LuxuryDivider)
}

private val LuxuryBackground = Color(0xFFF5F5F7)
private val LuxuryPrimaryText = Color(0xFF222222)
private val LuxurySecondaryText = Color(0xFF737373)
private val LuxuryDivider = Color(0xFFE5E5E5)
private val RectangleTwoDp = RoundedCornerShape(2.dp)

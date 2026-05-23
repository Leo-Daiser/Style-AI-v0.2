package com.example.styleai.feature.wardrobe

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.styleai.domain.audit.AuditRecommendation
import com.example.styleai.domain.audit.WardrobeAuditItem
import com.example.styleai.domain.audit.WardrobeAuditResult
import com.example.styleai.domain.audit.label
import com.example.styleai.domain.capsule.CapsulePlan
import com.example.styleai.domain.capsule.WardrobeGap
import com.example.styleai.domain.capsule.label
import com.example.styleai.domain.model.AppLanguage
import com.example.styleai.domain.shopping.label
import com.example.styleai.domain.wardrobe.WardrobeInsightSummary
import com.example.styleai.domain.wardrobe.WardrobeItem
import com.example.styleai.domain.wardrobe.label
import com.example.styleai.domain.wishlist.WishlistItem
import com.example.styleai.domain.wishlist.label
import com.example.styleai.ui.components.EditorialMetricUi
import com.example.styleai.ui.components.EditorialMetricsBlock
import java.util.Locale

private enum class WardrobeSection {
    ITEMS,
    CAPSULE,
    AUDIT,
    QUEUE
}

private val wardrobeFilters = listOf(
    "All",
    "Tops",
    "Bottoms",
    "Dresses",
    "Outerwear",
    "Shoes",
    "Bags",
    "Accessories",
    "Rarely used",
    "Wishlist"
)

@Composable
fun WardrobeScreen(
    viewModel: WardrobeViewModel,
    onCheckSimilarItem: () -> Unit
) {
    val language by viewModel.selectedLanguage.collectAsState()
    val selectedFilter by viewModel.selectedFilter.collectAsState()
    val allItems by viewModel.items.collectAsState()
    val visibleItems by viewModel.visibleItems.collectAsState()
    val summary by viewModel.summary.collectAsState()
    val capsulePlan by viewModel.capsulePlan.collectAsState()
    val auditResult by viewModel.auditResult.collectAsState()
    val queueItems by viewModel.queueItems.collectAsState()

    var selectedSectionName by rememberSaveable { mutableStateOf(WardrobeSection.ITEMS.name) }
    var headerOffsetPx by rememberSaveable { mutableStateOf(0f) }
    var selectedItem by remember { mutableStateOf<WardrobeItem?>(null) }
    var showAddItemDialog by remember { mutableStateOf(false) }
    val selectedSection = WardrobeSection.valueOf(selectedSectionName)
    val density = LocalDensity.current
    val expandedHeaderHeight = if (selectedSection == WardrobeSection.ITEMS) 338.dp else 250.dp
    val collapsedHeaderHeight = 58.dp
    val maxCollapsePx = with(density) { (expandedHeaderHeight - collapsedHeaderHeight).toPx() }
    val collapseFraction = if (maxCollapsePx <= 0f) 0f else (headerOffsetPx / maxCollapsePx).coerceIn(0f, 1f)
    val animatedCollapseFraction by animateFloatAsState(
        targetValue = collapseFraction,
        animationSpec = tween(durationMillis = 180),
        label = "wardrobeHeaderCollapse"
    )
    val headerHeight by animateDpAsState(
        targetValue = collapsedHeaderHeight + ((expandedHeaderHeight - collapsedHeaderHeight) * (1f - animatedCollapseFraction)),
        animationSpec = tween(durationMillis = 180),
        label = "wardrobeHeaderHeight"
    )
    val nestedScrollConnection = remember(maxCollapsePx) {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = -available.y
                headerOffsetPx = (headerOffsetPx + delta).coerceIn(0f, maxCollapsePx)
                return Offset.Zero
            }
        }
    }

    Surface(
        color = LuxuryBackground,
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(LuxuryBackground)
        ) {
            when (selectedSection) {
                WardrobeSection.ITEMS -> ItemsSection(
                    items = visibleItems,
                    language = language,
                    topContentPadding = headerHeight + 16.dp,
                    onItemClick = { selectedItem = it }
                )

                WardrobeSection.CAPSULE -> ScrollContent(topContentPadding = headerHeight + 16.dp) {
                    CapsulePlanSection(
                        plan = capsulePlan,
                        language = language,
                        onCheckSimilarItem = onCheckSimilarItem,
                        onAddGapToQueue = viewModel::addGapToPurchaseQueue
                    )
                }

                WardrobeSection.AUDIT -> ScrollContent(topContentPadding = headerHeight + 16.dp) {
                    WardrobeAuditSection(
                        result = auditResult,
                        wardrobeItems = allItems,
                        language = language,
                        onKeep = viewModel::keepAuditItem,
                        onReplace = viewModel::replaceAuditItem,
                        onArchive = viewModel::archiveAuditItem,
                        onWishlistSimilar = viewModel::wishlistSimilarAuditItem
                    )
                }

                WardrobeSection.QUEUE -> ScrollContent(topContentPadding = headerHeight + 16.dp) {
                    QueueSection(
                        items = queueItems,
                        language = language,
                        onCheckPurchase = onCheckSimilarItem
                    )
                }
            }

            CollapsibleWardrobeHeader(
                language = language,
                summary = summary,
                totalItems = allItems.size,
                selectedSection = selectedSection,
                selectedFilter = selectedFilter,
                collapseFraction = animatedCollapseFraction,
                headerHeight = headerHeight,
                onAddItem = { showAddItemDialog = true },
                onCheckPurchase = onCheckSimilarItem,
                onSectionSelected = {
                    selectedSectionName = it.name
                    headerOffsetPx = 0f
                },
                onFilterSelected = viewModel::selectFilter,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }

    selectedItem?.let { item ->
        WardrobeItemDetailDialog(
            item = item,
            language = language,
            compatibleItems = viewModel.compatibleItems(item),
            formulas = viewModel.styleFormulas(item, language),
            risks = viewModel.riskTexts(item, language),
            onDismiss = { selectedItem = null }
        )
    }

    if (showAddItemDialog) {
        AddItemPlaceholderDialog(language = language, onDismiss = { showAddItemDialog = false })
    }
}

@Composable
private fun CollapsibleWardrobeHeader(
    language: AppLanguage,
    summary: WardrobeInsightSummary,
    totalItems: Int,
    selectedSection: WardrobeSection,
    selectedFilter: String,
    collapseFraction: Float,
    headerHeight: Dp,
    onAddItem: () -> Unit,
    onCheckPurchase: () -> Unit,
    onSectionSelected: (WardrobeSection) -> Unit,
    onFilterSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val expandedAlpha = (1f - collapseFraction * 1.35f).coerceIn(0f, 1f)
    val collapsedAlpha = ((collapseFraction - 0.55f) / 0.45f).coerceIn(0f, 1f)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(headerHeight)
            .clipToBounds()
            .background(LuxuryBackground)
            .animateContentSize(animationSpec = tween(durationMillis = 280))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer { alpha = expandedAlpha }
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            WardrobeTitleBlock(language = language)
            WardrobeActions(
                language = language,
                onAddItem = onAddItem,
                onCheckPurchase = onCheckPurchase
            )
            WardrobeSummaryRow(summary = summary, totalItems = totalItems, language = language)
            MinimalSectionSelector(
                selectedSection = selectedSection,
                language = language,
                onSelect = onSectionSelected
            )
            if (selectedSection == WardrobeSection.ITEMS) {
                MinimalFilterSelector(
                    selectedFilter = selectedFilter,
                    language = language,
                    onSelect = onFilterSelected
                )
            }
        }

        CollapsedIconHeader(
            selectedSection = selectedSection,
            selectedFilter = selectedFilter,
            onSectionSelected = onSectionSelected,
            onFilterSelected = onFilterSelected,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .graphicsLayer { alpha = collapsedAlpha }
                .padding(horizontal = 20.dp, vertical = 9.dp)
        )
    }
}

@Composable
private fun WardrobeTitleBlock(language: AppLanguage) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = if (language == AppLanguage.RU) "Мой гардероб" else "My Wardrobe",
            color = LuxuryPrimaryText,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = if (language == AppLanguage.RU) {
                "Управляйте вещами и понимайте, что действительно работает."
            } else {
                "Manage your items and understand what actually works."
            },
            color = LuxurySecondaryText,
            style = MaterialTheme.typography.bodyMedium,
            lineHeight = 20.sp
        )
    }
}

@Composable
private fun CollapsedIconHeader(
    selectedSection: WardrobeSection,
    selectedFilter: String,
    onSectionSelected: (WardrobeSection) -> Unit,
    onFilterSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            WardrobeSection.values().forEach { section ->
                MinimalIconAction(
                    selected = selectedSection == section,
                    onClick = { onSectionSelected(section) }
                ) { iconColor ->
                    SectionIcon(section = section, color = iconColor)
                }
            }
        }
        Divider(
            modifier = Modifier
                .height(24.dp)
                .width(1.dp),
            color = LuxuryDivider,
            thickness = 1.dp
        )
        if (selectedSection == WardrobeSection.ITEMS) {
            LazyRow(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(wardrobeFilters) { filter ->
                    MinimalIconAction(
                        selected = selectedFilter == filter,
                        onClick = { onFilterSelected(filter) }
                    ) { iconColor ->
                        FilterIcon(filter = filter, color = iconColor)
                    }
                }
            }
        } else {
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun MinimalIconAction(selected: Boolean, onClick: () -> Unit, icon: @Composable (Color) -> Unit) {
    val iconColor = if (selected) LuxuryPrimaryText else LuxurySecondaryText
    Column(
        modifier = Modifier
            .width(28.dp)
            .clickable(onClick = onClick)
            .padding(vertical = 2.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        icon(iconColor)
        Box(
            modifier = Modifier
                .height(1.dp)
                .width(if (selected) 18.dp else 0.dp)
                .background(if (selected) LuxuryPrimaryText else Color.Transparent)
        )
    }
}

@Composable
private fun SectionIcon(section: WardrobeSection, color: Color) {
    Canvas(modifier = Modifier.size(20.dp)) {
        val strokeWidth = 1.5.dp.toPx()
        val stroke = Stroke(width = strokeWidth, cap = StrokeCap.Square)
        when (section) {
            WardrobeSection.ITEMS -> {
                drawRect(color, topLeft = Offset(size.width * 0.22f, size.height * 0.18f), size = Size(size.width * 0.56f, size.height * 0.64f), style = stroke)
                drawLine(color, Offset(size.width * 0.38f, size.height * 0.18f), Offset(size.width * 0.38f, size.height * 0.82f), strokeWidth)
                drawLine(color, Offset(size.width * 0.62f, size.height * 0.18f), Offset(size.width * 0.62f, size.height * 0.82f), strokeWidth)
            }
            WardrobeSection.CAPSULE -> {
                drawCircle(color, radius = size.minDimension * 0.34f, center = Offset(size.width * 0.5f, size.height * 0.5f), style = stroke)
                drawLine(color, Offset(size.width * 0.5f, size.height * 0.16f), Offset(size.width * 0.5f, size.height * 0.84f), strokeWidth)
                drawLine(color, Offset(size.width * 0.16f, size.height * 0.5f), Offset(size.width * 0.84f, size.height * 0.5f), strokeWidth)
            }
            WardrobeSection.AUDIT -> {
                drawRect(color, topLeft = Offset(size.width * 0.24f, size.height * 0.16f), size = Size(size.width * 0.52f, size.height * 0.68f), style = stroke)
                drawLine(color, Offset(size.width * 0.34f, size.height * 0.36f), Offset(size.width * 0.66f, size.height * 0.36f), strokeWidth)
                drawLine(color, Offset(size.width * 0.34f, size.height * 0.52f), Offset(size.width * 0.66f, size.height * 0.52f), strokeWidth)
                drawLine(color, Offset(size.width * 0.34f, size.height * 0.68f), Offset(size.width * 0.56f, size.height * 0.68f), strokeWidth)
            }
            WardrobeSection.QUEUE -> {
                drawLine(color, Offset(size.width * 0.24f, size.height * 0.26f), Offset(size.width * 0.76f, size.height * 0.26f), strokeWidth)
                drawLine(color, Offset(size.width * 0.24f, size.height * 0.5f), Offset(size.width * 0.76f, size.height * 0.5f), strokeWidth)
                drawLine(color, Offset(size.width * 0.24f, size.height * 0.74f), Offset(size.width * 0.62f, size.height * 0.74f), strokeWidth)
            }
        }
    }
}

@Composable
private fun FilterIcon(filter: String, color: Color) {
    Canvas(modifier = Modifier.size(20.dp)) {
        val strokeWidth = 1.45.dp.toPx()
        val stroke = Stroke(width = strokeWidth, cap = StrokeCap.Square)
        when (filter) {
            "All" -> {
                val side = size.width * 0.28f
                listOf(0.18f to 0.18f, 0.54f to 0.18f, 0.18f to 0.54f, 0.54f to 0.54f).forEach { (x, y) ->
                    drawRect(color, Offset(size.width * x, size.height * y), Size(side, side), style = stroke)
                }
            }
            "Tops" -> {
                drawLine(color, Offset(size.width * 0.28f, size.height * 0.26f), Offset(size.width * 0.5f, size.height * 0.14f), strokeWidth)
                drawLine(color, Offset(size.width * 0.5f, size.height * 0.14f), Offset(size.width * 0.72f, size.height * 0.26f), strokeWidth)
                drawRect(color, Offset(size.width * 0.28f, size.height * 0.26f), Size(size.width * 0.44f, size.height * 0.52f), style = stroke)
            }
            "Bottoms" -> {
                drawLine(color, Offset(size.width * 0.36f, size.height * 0.18f), Offset(size.width * 0.28f, size.height * 0.82f), strokeWidth)
                drawLine(color, Offset(size.width * 0.64f, size.height * 0.18f), Offset(size.width * 0.72f, size.height * 0.82f), strokeWidth)
                drawLine(color, Offset(size.width * 0.36f, size.height * 0.18f), Offset(size.width * 0.64f, size.height * 0.18f), strokeWidth)
                drawLine(color, Offset(size.width * 0.5f, size.height * 0.18f), Offset(size.width * 0.5f, size.height * 0.82f), strokeWidth)
            }
            "Dresses" -> {
                drawLine(color, Offset(size.width * 0.42f, size.height * 0.16f), Offset(size.width * 0.58f, size.height * 0.16f), strokeWidth)
                drawLine(color, Offset(size.width * 0.42f, size.height * 0.16f), Offset(size.width * 0.24f, size.height * 0.84f), strokeWidth)
                drawLine(color, Offset(size.width * 0.58f, size.height * 0.16f), Offset(size.width * 0.76f, size.height * 0.84f), strokeWidth)
                drawLine(color, Offset(size.width * 0.24f, size.height * 0.84f), Offset(size.width * 0.76f, size.height * 0.84f), strokeWidth)
            }
            "Outerwear" -> {
                drawRect(color, Offset(size.width * 0.26f, size.height * 0.16f), Size(size.width * 0.48f, size.height * 0.68f), style = stroke)
                drawLine(color, Offset(size.width * 0.5f, size.height * 0.16f), Offset(size.width * 0.5f, size.height * 0.84f), strokeWidth)
                drawLine(color, Offset(size.width * 0.26f, size.height * 0.36f), Offset(size.width * 0.12f, size.height * 0.62f), strokeWidth)
                drawLine(color, Offset(size.width * 0.74f, size.height * 0.36f), Offset(size.width * 0.88f, size.height * 0.62f), strokeWidth)
            }
            "Shoes" -> {
                drawLine(color, Offset(size.width * 0.2f, size.height * 0.68f), Offset(size.width * 0.78f, size.height * 0.68f), strokeWidth)
                drawLine(color, Offset(size.width * 0.2f, size.height * 0.68f), Offset(size.width * 0.34f, size.height * 0.48f), strokeWidth)
                drawLine(color, Offset(size.width * 0.34f, size.height * 0.48f), Offset(size.width * 0.66f, size.height * 0.58f), strokeWidth)
            }
            "Bags" -> {
                drawRect(color, Offset(size.width * 0.24f, size.height * 0.38f), Size(size.width * 0.52f, size.height * 0.42f), style = stroke)
                drawLine(color, Offset(size.width * 0.38f, size.height * 0.38f), Offset(size.width * 0.38f, size.height * 0.24f), strokeWidth)
                drawLine(color, Offset(size.width * 0.62f, size.height * 0.38f), Offset(size.width * 0.62f, size.height * 0.24f), strokeWidth)
                drawLine(color, Offset(size.width * 0.38f, size.height * 0.24f), Offset(size.width * 0.62f, size.height * 0.24f), strokeWidth)
            }
            "Accessories" -> {
                drawCircle(color, radius = size.width * 0.24f, center = Offset(size.width * 0.42f, size.height * 0.48f), style = stroke)
                drawLine(color, Offset(size.width * 0.58f, size.height * 0.62f), Offset(size.width * 0.8f, size.height * 0.78f), strokeWidth)
            }
            "Rarely used" -> {
                drawCircle(color, radius = size.width * 0.3f, center = Offset(size.width * 0.5f, size.height * 0.5f), style = stroke)
                drawLine(color, Offset(size.width * 0.5f, size.height * 0.5f), Offset(size.width * 0.5f, size.height * 0.3f), strokeWidth)
                drawLine(color, Offset(size.width * 0.5f, size.height * 0.5f), Offset(size.width * 0.66f, size.height * 0.58f), strokeWidth)
            }
            "Wishlist" -> {
                drawLine(color, Offset(size.width * 0.32f, size.height * 0.18f), Offset(size.width * 0.68f, size.height * 0.18f), strokeWidth)
                drawLine(color, Offset(size.width * 0.32f, size.height * 0.18f), Offset(size.width * 0.32f, size.height * 0.82f), strokeWidth)
                drawLine(color, Offset(size.width * 0.68f, size.height * 0.18f), Offset(size.width * 0.68f, size.height * 0.82f), strokeWidth)
                drawLine(color, Offset(size.width * 0.32f, size.height * 0.82f), Offset(size.width * 0.5f, size.height * 0.68f), strokeWidth)
                drawLine(color, Offset(size.width * 0.68f, size.height * 0.82f), Offset(size.width * 0.5f, size.height * 0.68f), strokeWidth)
            }
        }
    }
}

@Composable
private fun WardrobeActions(language: AppLanguage, onAddItem: () -> Unit, onCheckPurchase: () -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
        LuxuryOutlinedButton(
            text = if (language == AppLanguage.RU) "Добавить вещь" else "Add item",
            onClick = onAddItem,
            modifier = Modifier.weight(1f)
        )
        LuxuryPrimaryButton(
            text = if (language == AppLanguage.RU) "Проверить покупку" else "Check purchase",
            onClick = onCheckPurchase,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun WardrobeSummaryRow(summary: WardrobeInsightSummary, totalItems: Int, language: AppLanguage) {
    MinimalMetricsStrip(
        metrics = listOf(
            EditorialMetricUi(totalItems.toString(), if (language == AppLanguage.RU) "Вещей" else "Items"),
            EditorialMetricUi(summary.highVersatility.toString(), if (language == AppLanguage.RU) "Польза" else "Useful"),
            EditorialMetricUi(summary.duplicateRisks.toString(), if (language == AppLanguage.RU) "Дубли" else "Duplicates")
        )
    )
}

@Composable
private fun MinimalMetricsStrip(metrics: List<EditorialMetricUi>) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Divider(color = LuxuryDivider, thickness = 1.dp)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            metrics.forEach { metric ->
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text = metric.value,
                        color = LuxuryPrimaryText,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = (-0.2).sp
                    )
                    Text(
                        text = metric.label.uppercase(Locale.getDefault()),
                        color = LuxurySecondaryText,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 1.3.sp,
                        maxLines = 1
                    )
                }
            }
        }
        Divider(color = LuxuryDivider, thickness = 1.dp)
    }
}

@Composable
private fun MinimalSectionSelector(
    selectedSection: WardrobeSection,
    language: AppLanguage,
    onSelect: (WardrobeSection) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(WardrobeSection.values().toList()) { section ->
            MinimalSelectorItem(
                text = section.label(language),
                selected = selectedSection == section,
                onClick = { onSelect(section) }
            )
        }
    }
}

@Composable
private fun MinimalFilterSelector(
    selectedFilter: String,
    language: AppLanguage,
    onSelect: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(wardrobeFilters) { filter ->
            MinimalSelectorItem(
                text = filterLabel(filter, language),
                selected = selectedFilter == filter,
                onClick = { onSelect(filter) }
            )
        }
    }
}

@Composable
private fun MinimalSelectorItem(text: String, selected: Boolean, onClick: () -> Unit) {
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
            fontSize = 13.sp,
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

@Composable
private fun ItemsSection(
    items: List<WardrobeItem>,
    language: AppLanguage,
    topContentPadding: Dp,
    onItemClick: (WardrobeItem) -> Unit
) {
    if (items.isEmpty()) {
        EmptyWardrobeState(language = language, topContentPadding = topContentPadding)
        return
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .background(LuxuryBackground),
        contentPadding = PaddingValues(start = 20.dp, top = topContentPadding, end = 20.dp, bottom = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(22.dp)
    ) {
        items(items, key = { it.id }) { item ->
            WardrobeItemCard(item = item, language = language, onClick = { onItemClick(item) })
        }
    }
}

@Composable
private fun EmptyWardrobeState(language: AppLanguage, topContentPadding: Dp) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = topContentPadding + 24.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = if (language == AppLanguage.RU) "В гардеробе пока нет вещей." else "No wardrobe items yet.",
            color = LuxuryPrimaryText,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = if (language == AppLanguage.RU) {
                "Добавьте вещь или сохраните покупку в гардероб."
            } else {
                "Add an item or save a purchase into your wardrobe."
            },
            color = LuxurySecondaryText,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun ScrollContent(topContentPadding: Dp, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LuxuryBackground)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Spacer(modifier = Modifier.height(topContentPadding))
        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
        content()
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun CapsulePlanSection(
    plan: CapsulePlan,
    language: AppLanguage,
    onCheckSimilarItem: () -> Unit,
    onAddGapToQueue: (WardrobeGap) -> Unit
) {
    LuxurySection(
        title = if (language == AppLanguage.RU) "Покрытие капсулы" else "Capsule coverage",
        subtitle = if (language == AppLanguage.RU) {
            "Пробелы, дубли и следующие покупки."
        } else {
            "Gaps, duplicates, and next purchase priorities."
        }
    ) {
        EditorialMetricsBlock(
            metrics = listOf(
                EditorialMetricUi("${plan.capsuleCoverageScore}", if (language == AppLanguage.RU) "Оценка" else "Score"),
                EditorialMetricUi("${plan.highPriorityGaps.size}", if (language == AppLanguage.RU) "Пробелы" else "Gaps"),
                EditorialMetricUi("${plan.avoidBuyingAgain.size}", if (language == AppLanguage.RU) "Избегать" else "Avoid")
            )
        )

        if (plan.highPriorityGaps.isNotEmpty()) {
            LuxurySubheading(if (language == AppLanguage.RU) "Главные пробелы" else "High-priority gaps")
            plan.highPriorityGaps.forEach { gap ->
                GapCard(
                    gap = gap,
                    language = language,
                    onCheckSimilarItem = onCheckSimilarItem,
                    onAddGapToQueue = { onAddGapToQueue(gap) }
                )
            }
        }

        if (plan.suggestedNextPurchases.isNotEmpty()) {
            LuxurySubheading(if (language == AppLanguage.RU) "Что стоит купить следующим" else "Suggested next purchases")
            plan.suggestedNextPurchases.forEach { gap ->
                GapCompactLine(gap = gap, language = language)
            }
        }

        WarningList(
            title = if (language == AppLanguage.RU) "Не стоит покупать снова" else "Avoid buying again",
            items = plan.avoidBuyingAgain
        )
    }
}

@Composable
private fun GapCard(
    gap: WardrobeGap,
    language: AppLanguage,
    onCheckSimilarItem: () -> Unit,
    onAddGapToQueue: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(BorderStroke(1.dp, LuxuryDivider), RectangleTwoDp)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                text = gap.title.localizedGapTitle(language),
                modifier = Modifier.weight(1f),
                color = LuxuryPrimaryText,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = gap.priority.label(language).uppercase(Locale.getDefault()),
                color = LuxurySecondaryText,
                fontSize = 10.sp,
                letterSpacing = 1.2.sp
            )
        }
        Text(
            text = "${gap.category.label(language)} · ${gap.suggestedColorDirection.label(language)} · ${gap.expectedOutfitGain}",
            color = LuxurySecondaryText,
            fontSize = 12.sp
        )
        Text(
            text = gap.reason.localizedGapReason(language),
            color = LuxurySecondaryText,
            style = MaterialTheme.typography.bodySmall,
            lineHeight = 18.sp
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            LuxuryPrimaryButton(
                text = if (language == AppLanguage.RU) "Проверить" else "Check",
                onClick = onCheckSimilarItem,
                modifier = Modifier.weight(1f)
            )
            LuxuryOutlinedButton(
                text = if (language == AppLanguage.RU) "В очередь" else "Add queue",
                onClick = onAddGapToQueue,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun GapCompactLine(gap: WardrobeGap, language: AppLanguage) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = gap.title.localizedGapTitle(language),
            color = LuxuryPrimaryText,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = "${gap.category.label(language)} · ${gap.expectedOutfitGain}",
            color = LuxurySecondaryText,
            fontSize = 12.sp
        )
        Divider(color = LuxuryDivider, thickness = 1.dp)
    }
}

@Composable
private fun WarningList(title: String, items: List<String>) {
    if (items.isEmpty()) return
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        LuxurySubheading(title)
        items.take(4).forEach { item ->
            Text(
                text = item,
                color = LuxurySecondaryText,
                style = MaterialTheme.typography.bodySmall,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
private fun WardrobeAuditSection(
    result: WardrobeAuditResult,
    wardrobeItems: List<WardrobeItem>,
    language: AppLanguage,
    onKeep: (WardrobeAuditItem) -> Unit,
    onReplace: (WardrobeAuditItem) -> Unit,
    onArchive: (WardrobeAuditItem) -> Unit,
    onWishlistSimilar: (WardrobeAuditItem) -> Unit
) {
    LuxurySection(
        title = if (language == AppLanguage.RU) "Ревизия гардероба" else "Wardrobe Audit",
        subtitle = if (language == AppLanguage.RU) {
            "Найдите дубли, редко используемые вещи и вещи для замены."
        } else {
            "Find duplicates, underused items, and pieces worth replacing."
        }
    ) {
        EditorialMetricsBlock(
            metrics = listOf(
                EditorialMetricUi(result.keepCount.toString(), if (language == AppLanguage.RU) "Оставить" else "Keep"),
                EditorialMetricUi(result.replaceCount.toString(), if (language == AppLanguage.RU) "Заменить" else "Replace"),
                EditorialMetricUi(result.donateSellCount.toString(), if (language == AppLanguage.RU) "Отдать" else "Donate"),
                EditorialMetricUi(result.archiveCount.toString(), if (language == AppLanguage.RU) "Архив" else "Archive")
            )
        )
        Text(
            text = if (language == AppLanguage.RU) {
                "Средняя универсальность: ${result.averageVersatilityScore}/100"
            } else {
                "Average versatility: ${result.averageVersatilityScore}/100"
            },
            color = LuxurySecondaryText,
            fontSize = 12.sp
        )
        result.auditItems.take(6).forEach { auditItem ->
            val wardrobeItem = wardrobeItems.firstOrNull { it.id == auditItem.wardrobeItemId }
            AuditItemCard(
                auditItem = auditItem,
                wardrobeItem = wardrobeItem,
                language = language,
                onKeep = { onKeep(auditItem) },
                onReplace = { onReplace(auditItem) },
                onArchive = { onArchive(auditItem) },
                onWishlistSimilar = { onWishlistSimilar(auditItem) }
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AuditItemCard(
    auditItem: WardrobeAuditItem,
    wardrobeItem: WardrobeItem?,
    language: AppLanguage,
    onKeep: () -> Unit,
    onReplace: () -> Unit,
    onArchive: () -> Unit,
    onWishlistSimilar: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(BorderStroke(1.dp, LuxuryDivider), RectangleTwoDp)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
            wardrobeItem?.let {
                WardrobeImage(item = it, modifier = Modifier.width(72.dp).aspectRatio(1f), language = language)
            }
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(
                    text = auditItem.itemTitle.localizedItemTitle(language),
                    color = LuxuryPrimaryText,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${auditItem.recommendation.label(language)} · ${auditItem.auditScore}/100",
                    color = auditColor(auditItem.recommendation),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
        DetailSection(title = if (language == AppLanguage.RU) "Причины" else "Reasons", items = auditItem.reasons.take(2))
        DetailSection(title = if (language == AppLanguage.RU) "Риски" else "Risks", items = auditItem.risks.take(2))
        Text(
            text = auditItem.suggestedAction,
            color = LuxurySecondaryText,
            style = MaterialTheme.typography.bodySmall,
            lineHeight = 18.sp
        )
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            LuxuryTextAction(if (language == AppLanguage.RU) "Оставить" else "Keep", onKeep)
            LuxuryTextAction(if (language == AppLanguage.RU) "Заменить" else "Replace", onReplace)
            LuxuryTextAction(if (language == AppLanguage.RU) "Архивировать" else "Archive", onArchive)
            LuxuryTextAction(if (language == AppLanguage.RU) "Найти похожую" else "Wishlist similar", onWishlistSimilar)
        }
    }
}

@Composable
private fun QueueSection(items: List<WishlistItem>, language: AppLanguage, onCheckPurchase: () -> Unit) {
    val activeItems = items.filterNot { it.isArchived }
    if (activeItems.isEmpty()) {
        LuxurySection(
            title = if (language == AppLanguage.RU) "Пока нет запланированных покупок." else "No planned purchases yet.",
            subtitle = if (language == AppLanguage.RU) {
                "Проверяйте вещи перед покупкой и добавляйте полезные в очередь."
            } else {
                "Check items before buying and add useful ones to your queue."
            }
        ) {
            LuxuryPrimaryButton(
                text = if (language == AppLanguage.RU) "Проверить покупку" else "Check purchase",
                onClick = onCheckPurchase,
                modifier = Modifier.fillMaxWidth()
            )
        }
    } else {
        LuxurySection(
            title = if (language == AppLanguage.RU) "Очередь покупок" else "Purchase Queue",
            subtitle = if (language == AppLanguage.RU) {
                "Планируемые покупки по пользе для гардероба."
            } else {
                "Planned purchases ranked by wardrobe value."
            }
        ) {
            activeItems.forEach { item ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(BorderStroke(1.dp, LuxuryDivider), RectangleTwoDp)
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(item.title.localizedItemTitle(language), color = LuxuryPrimaryText, fontWeight = FontWeight.SemiBold)
                    Text(
                        "${item.category.label(language)} · ${item.colorDirection.label(language)} · ${item.wardrobeValueScore}/100",
                        color = LuxurySecondaryText,
                        fontSize = 12.sp
                    )
                    Text(item.reason, color = LuxurySecondaryText, style = MaterialTheme.typography.bodySmall, lineHeight = 18.sp)
                    Text(
                        item.priority.label(language).uppercase(Locale.getDefault()),
                        color = LuxuryPrimaryText,
                        fontSize = 10.sp,
                        letterSpacing = 1.2.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun WardrobeItemCard(item: WardrobeItem, language: AppLanguage, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        WardrobeImage(item = item, modifier = Modifier.fillMaxWidth().aspectRatio(1f), language = language)
        Text(
            text = item.localizedTitle(language),
            color = LuxuryPrimaryText,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            lineHeight = 18.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = "${item.category.label(language)} · ${item.colorDirection.label(language)}",
            color = LuxurySecondaryText,
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = if (language == AppLanguage.RU) {
                "${item.versatilityScore}/100 · ${item.outfitCount} образов"
            } else {
                "${item.versatilityScore}/100 · ${item.outfitCount} outfits"
            }.uppercase(Locale.getDefault()),
            color = LuxurySecondaryText,
            fontSize = 10.sp,
            letterSpacing = 1.1.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            if (item.isRarelyUsed) InlineFlag(if (language == AppLanguage.RU) "Редко" else "Rarely used")
            if (item.isDuplicateRisk) InlineFlag(if (language == AppLanguage.RU) "Дубль" else "Duplicate")
            if (item.isWishlist) InlineFlag(if (language == AppLanguage.RU) "Wishlist" else "Wishlist")
        }
    }
}

@Composable
private fun InlineFlag(text: String) {
    Text(
        text = text.uppercase(Locale.getDefault()),
        color = LuxurySecondaryText,
        fontSize = 10.sp,
        letterSpacing = 1.1.sp
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun WardrobeItemDetailDialog(
    item: WardrobeItem,
    language: AppLanguage,
    compatibleItems: List<WardrobeItem>,
    formulas: List<String>,
    risks: List<String>,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(contentColor = LuxuryPrimaryText)
            ) {
                Text(if (language == AppLanguage.RU) "Закрыть" else "Close")
            }
        },
        shape = RectangleTwoDp,
        containerColor = LuxuryBackground,
        titleContentColor = LuxuryPrimaryText,
        textContentColor = LuxurySecondaryText,
        title = { Text(item.localizedTitle(language), fontWeight = FontWeight.SemiBold) },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                WardrobeImage(item = item, modifier = Modifier.fillMaxWidth().aspectRatio(1f), language = language)
                DetailLine(if (language == AppLanguage.RU) "Категория" else "Category", item.category.label(language))
                DetailLine(if (language == AppLanguage.RU) "Цвет" else "Color", item.colorDirection.label(language))
                DetailLine(if (language == AppLanguage.RU) "Сезоны" else "Seasons", item.seasons.joinToString { it.label(language) })
                DetailLine(if (language == AppLanguage.RU) "Поводы" else "Occasions", item.occasionTags.joinToString { it.label(language) })
                DetailLine(if (language == AppLanguage.RU) "Польза" else "Versatility", "${item.versatilityScore}/100")
                DetailLine(if (language == AppLanguage.RU) "Роль в капсуле" else "Capsule role", item.capsuleRole.label(language))
                DetailLine(
                    if (language == AppLanguage.RU) "Потенциал образов" else "Outfit count",
                    if (language == AppLanguage.RU) "${item.outfitCount} образов" else "${item.outfitCount} outfits"
                )
                DetailSection(
                    title = if (language == AppLanguage.RU) "Сочетаемость" else "Compatibility",
                    items = compatibleItems.map {
                        if (language == AppLanguage.RU) "Сочетается с ${it.localizedTitle(language)}" else "Works with ${it.localizedTitle(language)}"
                    }
                )
                DetailSection(if (language == AppLanguage.RU) "Формулы образов" else "Style formulas", formulas)
                DetailSection(if (language == AppLanguage.RU) "Риски" else "Risks", risks)
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(
                        if (language == AppLanguage.RU) "Оставить" else "Keep",
                        if (language == AppLanguage.RU) "Возможно заменить" else "Maybe replace",
                        if (language == AppLanguage.RU) "Архивировать" else "Archive",
                        if (language == AppLanguage.RU) "Найти похожую" else "Wishlist similar"
                    ).forEach { label ->
                        LuxuryOutlinedButton(text = label, onClick = {})
                    }
                }
            }
        }
    )
}

@Composable
private fun WardrobeImage(item: WardrobeItem, modifier: Modifier, language: AppLanguage) {
    Box(
        modifier = modifier
            .clip(RectangleTwoDp)
            .background(LuxuryBackground)
            .border(BorderStroke(1.dp, LuxuryDivider), RectangleTwoDp),
        contentAlignment = Alignment.Center
    ) {
        val drawableRes = item.drawableRes
        if (drawableRes != null) {
            Image(
                painter = painterResource(drawableRes),
                contentDescription = item.localizedTitle(language),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                contentScale = ContentScale.Fit
            )
        } else {
            Text(
                text = if (language == AppLanguage.RU) "Без изображения" else "No image",
                color = LuxurySecondaryText,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
private fun AddItemPlaceholderDialog(language: AppLanguage, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            LuxuryPrimaryButton(
                text = if (language == AppLanguage.RU) "Понятно" else "Got it",
                onClick = onDismiss
            )
        },
        shape = RectangleTwoDp,
        containerColor = LuxuryBackground,
        titleContentColor = LuxuryPrimaryText,
        textContentColor = LuxurySecondaryText,
        title = { Text(if (language == AppLanguage.RU) "Добавление вещи" else "Add item") },
        text = {
            Text(
                text = if (language == AppLanguage.RU) {
                    "Локальная форма добавления вещи будет следующим шагом: категория, цвет, сезон и сценарии носки."
                } else {
                    "Local item creation is coming next: category, color, season, and occasions."
                },
                lineHeight = 20.sp
            )
        }
    )
}

@Composable
private fun LuxurySection(title: String, subtitle: String, content: @Composable ColumnScope.() -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp), modifier = Modifier.fillMaxWidth()) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(title, color = LuxuryPrimaryText, fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
            Text(subtitle, color = LuxurySecondaryText, style = MaterialTheme.typography.bodyMedium, lineHeight = 20.sp)
        }
        Divider(color = LuxuryDivider, thickness = 1.dp)
        content()
    }
}

@Composable
private fun LuxurySubheading(text: String) {
    Text(
        text = text,
        color = LuxuryPrimaryText,
        fontWeight = FontWeight.SemiBold,
        style = MaterialTheme.typography.titleSmall
    )
}

@Composable
private fun DetailSection(title: String, items: List<String>) {
    if (items.isEmpty()) return
    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Text(title, color = LuxuryPrimaryText, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
        items.forEach { item ->
            Text(item, color = LuxurySecondaryText, style = MaterialTheme.typography.bodySmall, lineHeight = 18.sp)
        }
    }
}

@Composable
private fun DetailLine(label: String, value: String) {
    Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
        Text(
            text = label.uppercase(Locale.getDefault()),
            color = LuxurySecondaryText,
            fontSize = 10.sp,
            letterSpacing = 1.1.sp
        )
        Text(value, color = LuxuryPrimaryText, style = MaterialTheme.typography.bodyMedium)
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

@Composable
private fun LuxuryTextAction(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        modifier = Modifier
            .border(BorderStroke(1.dp, LuxuryDivider), RectangleTwoDp)
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 7.dp),
        color = LuxuryPrimaryText,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium
    )
}

private fun WardrobeSection.label(language: AppLanguage): String = when (this) {
    WardrobeSection.ITEMS -> if (language == AppLanguage.RU) "Вещи" else "Items"
    WardrobeSection.CAPSULE -> if (language == AppLanguage.RU) "Капсула" else "Capsule"
    WardrobeSection.AUDIT -> if (language == AppLanguage.RU) "Ревизия" else "Audit"
    WardrobeSection.QUEUE -> if (language == AppLanguage.RU) "Очередь" else "Queue"
}

private fun filterLabel(filter: String, language: AppLanguage): String {
    if (language != AppLanguage.RU) return filter
    return when (filter) {
        "All" -> "Все"
        "Tops" -> "Верх"
        "Bottoms" -> "Низ"
        "Dresses" -> "Платья"
        "Outerwear" -> "Верхняя одежда"
        "Shoes" -> "Обувь"
        "Bags" -> "Сумки"
        "Accessories" -> "Аксессуары"
        "Rarely used" -> "Редко"
        "Wishlist" -> "Wishlist"
        else -> filter
    }
}

private fun collapsedStateText(section: WardrobeSection, filter: String, language: AppLanguage): String {
    return if (section == WardrobeSection.ITEMS) {
        "${section.label(language)} · ${filterLabel(filter, language)}"
    } else {
        section.label(language)
    }
}

private fun WardrobeItem.localizedTitle(language: AppLanguage): String = title.localizedItemTitle(language)

private fun String.localizedItemTitle(language: AppLanguage): String {
    if (language != AppLanguage.RU) return this
    return when (this) {
        "Cream structured blazer" -> "Кремовый структурированный жакет"
        "White cotton shirt" -> "Белая хлопковая рубашка"
        "Dark blue straight jeans" -> "Темно-синие прямые джинсы"
        "Beige tailored trousers" -> "Бежевые классические брюки"
        "Olive midi dress" -> "Оливковое миди-платье"
        "Oatmeal merino knit" -> "Овсяный мериносовый трикотаж"
        "Camel trench coat" -> "Кэмел тренч"
        "White leather sneakers" -> "Белые кожаные кеды"
        "Black leather loafers" -> "Черные кожаные лоферы"
        "Tan ankle boots" -> "Рыжевато-коричневые ботильоны"
        "Brown leather handbag" -> "Коричневая кожаная сумка"
        "Brown leather belt" -> "Коричневый кожаный ремень"
        else -> this
    }
}

private fun String.localizedGapTitle(language: AppLanguage): String {
    if (language != AppLanguage.RU) return this
    return when {
        contains("Neutral everyday shoes", ignoreCase = true) -> "Универсальная повседневная обувь"
        contains("Practical outerwear", ignoreCase = true) -> "Практичный слой верхней одежды"
        contains("Neutral bottom", ignoreCase = true) -> "Нейтральная база низа"
        else -> this
    }
}

private fun String.localizedGapReason(language: AppLanguage): String {
    if (language != AppLanguage.RU) return this
    return when {
        contains("neutral shoes", ignoreCase = true) -> "Не хватает универсальной повседневной обуви."
        contains("outerwear", ignoreCase = true) -> "Не хватает нейтрального слоя верхней одежды."
        contains("neutral bottom", ignoreCase = true) -> "Не хватает нейтральной базы для повседневных образов."
        else -> this
    }
}

private fun auditColor(recommendation: AuditRecommendation): Color = when (recommendation) {
    AuditRecommendation.KEEP -> Color(0xFF222222)
    AuditRecommendation.WISHLIST_SIMILAR -> Color(0xFF5F6255)
    AuditRecommendation.REPLACE -> Color(0xFF6B604F)
    AuditRecommendation.DONATE_SELL -> Color(0xFF737373)
    AuditRecommendation.ARCHIVE -> Color(0xFF737373)
}

private val LuxuryBackground = Color(0xFFF5F5F7)
private val LuxuryPrimaryText = Color(0xFF222222)
private val LuxurySecondaryText = Color(0xFF737373)
private val LuxuryDivider = Color(0xFFE5E5E5)
private val RectangleTwoDp = RoundedCornerShape(2.dp)

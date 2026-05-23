package com.example.styleai.feature.decisions

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.styleai.domain.decisions.SavedDecision
import com.example.styleai.domain.model.AppLanguage
import com.example.styleai.domain.shopping.ShoppingVerdict
import com.example.styleai.domain.shopping.label
import com.example.styleai.domain.wishlist.PurchasePriority
import com.example.styleai.domain.wishlist.WishlistItem
import com.example.styleai.domain.wishlist.label
import java.util.Locale

@Composable
fun DecisionsScreen(
    viewModel: DecisionsViewModel,
    onCheckItem: () -> Unit
) {
    val language by viewModel.selectedLanguage.collectAsState()
    val allDecisions by viewModel.decisions.collectAsState()
    val wishlist by viewModel.wishlist.collectAsState()
    var selectedFilter by remember { mutableStateOf<ShoppingVerdict?>(null) }
    var addedDecisionId by remember { mutableStateOf<String?>(null) }
    var boughtWishlistItemId by remember { mutableStateOf<String?>(null) }
    val decisions = allDecisions.filter { selectedFilter == null || it.verdict == selectedFilter }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(LuxuryBackground)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(22.dp)
    ) {
        item {
            DecisionsHeader(language = language)
        }

        item {
            VerdictFilterRow(
                language = language,
                selectedFilter = selectedFilter,
                onSelect = { selectedFilter = it }
            )
        }

        item {
            PurchaseQueueSection(
                items = wishlist,
                language = language,
                boughtItemId = boughtWishlistItemId,
                onCheckItem = onCheckItem,
                onAddManual = { viewModel.addManualWishlistCandidate() },
                onMarkBought = {
                    viewModel.markWishlistItemBought(it)
                    boughtWishlistItemId = it.id
                },
                onArchive = { viewModel.archiveWishlistItem(it.id) },
                onDelete = { viewModel.deleteWishlistItem(it.id) }
            )
        }

        if (decisions.isEmpty()) {
            item {
                EmptyDecisionsState(language = language, onCheckItem = onCheckItem)
            }
        } else {
            items(decisions, key = { it.id }) { decision ->
                DecisionCard(
                    decision = decision,
                    language = language,
                    addedToWardrobe = addedDecisionId == decision.id,
                    onAddToWardrobe = {
                        viewModel.addToWardrobe(decision)
                        addedDecisionId = decision.id
                    },
                    onDelete = { viewModel.deleteDecision(decision.id) }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
private fun DecisionsHeader(language: AppLanguage) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = if (language == AppLanguage.RU) "Решения" else "Decisions",
            color = LuxuryPrimaryText,
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = (-0.2).sp
        )
        Text(
            text = if (language == AppLanguage.RU) {
                "Ваши сохраненные решения о покупках и гардеробе."
            } else {
                "Your saved shopping decisions and wardrobe choices."
            },
            color = LuxurySecondaryText,
            fontSize = 15.sp,
            lineHeight = 22.sp
        )
    }
}

@Composable
private fun VerdictFilterRow(
    language: AppLanguage,
    selectedFilter: ShoppingVerdict?,
    onSelect: (ShoppingVerdict?) -> Unit
) {
    MinimalFilterRow {
        item {
            MinimalFilterText(
                text = if (language == AppLanguage.RU) "Все" else "All",
                selected = selectedFilter == null,
                onClick = { onSelect(null) }
            )
        }
        items(listOf(ShoppingVerdict.BUY, ShoppingVerdict.MAYBE, ShoppingVerdict.SKIP)) { verdict ->
            MinimalFilterText(
                text = verdict.label(language),
                selected = selectedFilter == verdict,
                onClick = { onSelect(verdict) }
            )
        }
    }
}

@Composable
private fun PriorityFilterRow(
    language: AppLanguage,
    selectedPriority: PurchasePriority?,
    onSelect: (PurchasePriority?) -> Unit
) {
    MinimalFilterRow {
        item {
            MinimalFilterText(
                text = if (language == AppLanguage.RU) "Все" else "All",
                selected = selectedPriority == null,
                onClick = { onSelect(null) }
            )
        }
        items(PurchasePriority.values().toList()) { priority ->
            MinimalFilterText(
                text = when {
                    language == AppLanguage.RU && priority == PurchasePriority.HIGH -> "Высокий"
                    else -> priority.label(language)
                },
                selected = selectedPriority == priority,
                onClick = { onSelect(priority) }
            )
        }
    }
}

@Composable
private fun MinimalFilterRow(content: androidx.compose.foundation.lazy.LazyListScope.() -> Unit) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalAlignment = Alignment.CenterVertically,
        content = content
    )
}

@Composable
private fun MinimalFilterText(text: String, selected: Boolean, onClick: () -> Unit) {
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

@Composable
private fun PurchaseQueueSection(
    items: List<WishlistItem>,
    language: AppLanguage,
    boughtItemId: String?,
    onCheckItem: () -> Unit,
    onAddManual: () -> Unit,
    onMarkBought: (WishlistItem) -> Unit,
    onArchive: (WishlistItem) -> Unit,
    onDelete: (WishlistItem) -> Unit
) {
    var selectedPriority by remember { mutableStateOf<PurchasePriority?>(null) }
    val activeItems = items.filterNot { it.isArchived }
    val visibleItems = activeItems.filter { selectedPriority == null || it.priority == selectedPriority }

    SectionBlock(
        title = if (language == AppLanguage.RU) "Очередь покупок" else "Purchase Queue",
        subtitle = if (language == AppLanguage.RU) {
            "Вещи, которые стоит рассмотреть следующими, по пользе для гардероба."
        } else {
            "Items worth considering next, ranked by wardrobe value."
        }
    ) {
        LuxuryOutlinedButton(
            text = if (language == AppLanguage.RU) "Добавить ручной кандидат" else "Add manual candidate",
            onClick = onAddManual
        )
        PriorityFilterRow(
            language = language,
            selectedPriority = selectedPriority,
            onSelect = { selectedPriority = it }
        )
        if (visibleItems.isEmpty()) {
            Text(
                text = if (language == AppLanguage.RU) {
                    "Очередь пока пуста. Добавьте Maybe/Buy решение или пробел капсулы."
                } else {
                    "Queue is empty. Add a Maybe/Buy decision or a capsule gap."
                },
                color = LuxurySecondaryText,
                fontSize = 13.sp,
                lineHeight = 19.sp
            )
        } else {
            visibleItems.take(4).forEach { item ->
                WishlistCard(
                    item = item,
                    language = language,
                    bought = boughtItemId == item.id,
                    onCheckItem = onCheckItem,
                    onMarkBought = { onMarkBought(item) },
                    onArchive = { onArchive(item) },
                    onDelete = { onDelete(item) }
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun WishlistCard(
    item: WishlistItem,
    language: AppLanguage,
    bought: Boolean,
    onCheckItem: () -> Unit,
    onMarkBought: () -> Unit,
    onArchive: () -> Unit,
    onDelete: () -> Unit
) {
    OutlinedContentBlock {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.Top) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(
                    text = item.title,
                    color = LuxuryPrimaryText,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    lineHeight = 21.sp
                )
                Text(
                    text = "${item.category.label(language)} · ${item.colorDirection.label(language)} · ${item.wardrobeValueScore}/100",
                    color = LuxurySecondaryText,
                    fontSize = 12.sp
                )
            }
            PriorityLabel(priority = item.priority, language = language)
        }
        Text(
            text = item.reason,
            color = LuxurySecondaryText,
            fontSize = 13.sp,
            lineHeight = 19.sp
        )
        Text(
            text = if (item.duplicateRiskLevel != null) {
                if (language == AppLanguage.RU) {
                    "Потенциал образов: ${item.expectedOutfitGain} · риск дубля: ${item.duplicateRiskLevel.label(language)}"
                } else {
                    "Outfit gain: ${item.expectedOutfitGain} · duplicate risk: ${item.duplicateRiskLevel.label(language)}"
                }
            } else {
                if (language == AppLanguage.RU) "Потенциал образов: ${item.expectedOutfitGain}" else "Outfit gain: ${item.expectedOutfitGain}"
            },
            color = LuxurySecondaryText,
            fontSize = 12.sp,
            lineHeight = 18.sp
        )
        if (bought) {
            Text(
                text = if (language == AppLanguage.RU) "Добавлено в гардероб." else "Added to wardrobe.",
                color = LuxuryPrimaryText,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
        }
        FlowRow(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            LuxuryTextAction(if (language == AppLanguage.RU) "Проверить вещь" else "Check item", onCheckItem)
            LuxuryTextAction(if (language == AppLanguage.RU) "Отметить купленной" else "Mark as bought", onMarkBought)
            LuxuryTextAction(if (language == AppLanguage.RU) "Архивировать" else "Archive", onArchive)
            LuxuryTextAction(if (language == AppLanguage.RU) "Удалить" else "Delete", onDelete, secondary = true)
        }
    }
}

@Composable
private fun EmptyDecisionsState(language: AppLanguage, onCheckItem: () -> Unit) {
    OutlinedContentBlock(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = if (language == AppLanguage.RU) "Пока нет сохраненных решений." else "No saved decisions yet.",
            color = LuxuryPrimaryText,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
        Text(
            text = if (language == AppLanguage.RU) {
                "Проверьте вещь перед покупкой и сохраните результат."
            } else {
                "Check an item before buying and save the result."
            },
            color = LuxurySecondaryText,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            textAlign = TextAlign.Center
        )
        LuxuryPrimaryButton(
            text = if (language == AppLanguage.RU) "Проверить вещь" else "Check an item",
            onClick = onCheckItem
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun DecisionCard(
    decision: SavedDecision,
    language: AppLanguage,
    addedToWardrobe: Boolean,
    onAddToWardrobe: () -> Unit,
    onDelete: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    OutlinedContentBlock {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.Top) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                VerdictLabel(decision.verdict, language)
                Text(
                    text = "${decision.category.label(language)} · ${decision.colorDirection.label(language)} · ${decision.occasion.label(language)}",
                    color = LuxuryPrimaryText,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    lineHeight = 21.sp
                )
            }
            Text(
                text = "${decision.score}/100",
                color = LuxuryPrimaryText,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        }
        Text(
            text = decision.mainReason,
            color = LuxurySecondaryText,
            fontSize = 14.sp,
            lineHeight = 20.sp
        )
        Text(
            text = "${decision.estimatedOutfitCount} · ${decision.capsuleImpact}",
            color = LuxurySecondaryText,
            fontSize = 12.sp,
            lineHeight = 18.sp
        )
        decision.duplicateRiskLevel?.let { level ->
            Text(
                text = if (language == AppLanguage.RU) {
                    "Риск дублирования: ${level.label(language)} · похожих вещей: ${decision.similarOwnedItems.size}"
                } else {
                    "Duplicate risk: ${level.label(language)} · similar items: ${decision.similarOwnedItems.size}"
                },
                color = LuxurySecondaryText,
                fontSize = 12.sp,
                lineHeight = 18.sp
            )
        }
        Text(
            text = decision.createdDateLabel.uppercase(Locale.getDefault()),
            color = LuxurySecondaryText,
            fontSize = 10.sp,
            letterSpacing = 1.1.sp
        )

        if (addedToWardrobe) {
            Text(
                text = if (language == AppLanguage.RU) "Добавлено в гардероб." else "Added to wardrobe.",
                color = LuxuryPrimaryText,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
        }

        if (expanded) {
            Divider(color = LuxuryDivider, thickness = 1.dp)
            DetailSection(
                title = if (language == AppLanguage.RU) "Параметры" else "Inputs",
                items = listOf(
                    decision.category.label(language),
                    decision.colorDirection.label(language),
                    decision.occasion.label(language),
                    decision.season.label(language)
                )
            )
            DetailSection(
                title = if (language == AppLanguage.RU) "Почему может сработать" else "Positive reasons",
                items = decision.positiveReasons
            )
            DetailSection(
                title = if (language == AppLanguage.RU) "Риски" else "Risks",
                items = decision.risks
            )
            ResultLine(if (language == AppLanguage.RU) "Рекомендация" else "Recommendation", decision.recommendation)
            decision.comparisonReason?.let { reason ->
                ResultLine(
                    if (language == AppLanguage.RU) "Сравнение с гардеробом" else "Wardrobe comparison",
                    reason
                )
            }
            if (decision.similarOwnedItems.isNotEmpty()) {
                DetailSection(
                    title = if (language == AppLanguage.RU) "Похожие вещи" else "Similar owned items",
                    items = decision.similarOwnedItems
                )
            }
            decision.betterAlternativeHint?.let { hint ->
                ResultLine(
                    if (language == AppLanguage.RU) "Более сильная альтернатива" else "Better alternative",
                    hint
                )
            }
            ResultLine(if (language == AppLanguage.RU) "Влияние на капсулу" else "Capsule impact", decision.capsuleImpact)
            ResultLine(if (language == AppLanguage.RU) "Оценка образов" else "Estimated outfit count", decision.estimatedOutfitCount)
            ResultLine(if (language == AppLanguage.RU) "Дата" else "Created", decision.createdDateLabel)
        }

        FlowRow(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            if (decision.verdict == ShoppingVerdict.BUY) {
                LuxuryTextAction(if (language == AppLanguage.RU) "Добавить в гардероб" else "Add to wardrobe", onAddToWardrobe)
            }
            LuxuryTextAction(
                text = if (language == AppLanguage.RU) "Детали" else "Details",
                onClick = { expanded = !expanded }
            )
            LuxuryTextAction(if (language == AppLanguage.RU) "Удалить" else "Delete", onDelete, secondary = true)
        }
    }
}

@Composable
private fun SectionBlock(
    title: String,
    subtitle: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(title, color = LuxuryPrimaryText, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            Text(subtitle, color = LuxurySecondaryText, fontSize = 13.sp, lineHeight = 19.sp)
        }
        Divider(color = LuxuryDivider, thickness = 1.dp)
        content()
    }
}

@Composable
private fun OutlinedContentBlock(
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = LuxuryCardBackground,
        shape = RectangleTwoDp,
        border = BorderStroke(1.dp, LuxuryDivider),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = horizontalAlignment,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            content = content
        )
    }
}

@Composable
private fun VerdictLabel(verdict: ShoppingVerdict, language: AppLanguage) {
    Text(
        text = verdict.label(language).uppercase(Locale.getDefault()),
        color = LuxurySecondaryText,
        fontSize = 10.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 1.2.sp
    )
}

@Composable
private fun PriorityLabel(priority: PurchasePriority, language: AppLanguage) {
    Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .width(5.dp)
                .height(5.dp)
                .background(LuxurySecondaryText, RectangleTwoDp)
        )
        Text(
            text = "${priority.label(language)} ${if (language == AppLanguage.RU) "приоритет" else "priority"}"
                .uppercase(Locale.getDefault()),
            color = LuxurySecondaryText,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 1.1.sp
        )
    }
}

@Composable
private fun DetailSection(title: String, items: List<String>) {
    if (items.isEmpty()) return
    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Text(
            text = title,
            color = LuxuryPrimaryText,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
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

@Composable
private fun ResultLine(title: String, value: String) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = title,
            color = LuxuryPrimaryText,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(value, color = LuxurySecondaryText, fontSize = 13.sp, lineHeight = 19.sp)
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
private fun LuxuryTextAction(text: String, onClick: () -> Unit, secondary: Boolean = false) {
    TextButton(
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(
            contentColor = if (secondary) LuxurySecondaryText else LuxuryPrimaryText
        )
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

private val LuxuryBackground = Color(0xFFF5F5F7)
private val LuxuryCardBackground = Color.White
private val LuxuryPrimaryText = Color(0xFF222222)
private val LuxurySecondaryText = Color(0xFF737373)
private val LuxuryDivider = Color(0xFFE5E5E5)
private val RectangleTwoDp = RoundedCornerShape(2.dp)

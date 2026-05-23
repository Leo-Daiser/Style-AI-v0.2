package com.example.styleai.feature.wardrobe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.styleai.domain.audit.WardrobeAuditEngine
import com.example.styleai.domain.audit.WardrobeAuditItem
import com.example.styleai.domain.audit.WardrobeAuditResult
import com.example.styleai.domain.capsule.CapsuleAnalyzer
import com.example.styleai.domain.capsule.CapsulePlan
import com.example.styleai.domain.capsule.WardrobeGap
import com.example.styleai.domain.model.AppLanguage
import com.example.styleai.domain.repository.StyleRepository
import com.example.styleai.domain.shopping.ShoppingSeason
import com.example.styleai.domain.shopping.ShoppingCategory
import com.example.styleai.domain.shopping.ShoppingColorDirection
import com.example.styleai.domain.shopping.ShoppingOccasion
import com.example.styleai.domain.wishlist.PurchasePriorityEngine
import com.example.styleai.domain.wishlist.WishlistItem
import com.example.styleai.domain.wishlist.WishlistRepository
import com.example.styleai.domain.wishlist.WishlistSource
import com.example.styleai.domain.wardrobe.CapsuleRole
import com.example.styleai.domain.wardrobe.WardrobeCategory
import com.example.styleai.domain.wardrobe.WardrobeInsightSummary
import com.example.styleai.domain.wardrobe.WardrobeItem
import com.example.styleai.domain.wardrobe.WardrobeRepository
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WardrobeViewModel(
    styleRepository: StyleRepository,
    private val wardrobeRepository: WardrobeRepository,
    private val wishlistRepository: WishlistRepository
) : ViewModel() {
    val selectedLanguage: StateFlow<AppLanguage> = styleRepository.getSelectedLanguage()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AppLanguage.EN)

    private val _selectedFilter = MutableStateFlow("All")
    val selectedFilter: StateFlow<String> = _selectedFilter.asStateFlow()
    private val hiddenAuditItemIds = MutableStateFlow<Set<String>>(emptySet())

    val items: StateFlow<List<WardrobeItem>> = wardrobeRepository.observeItems()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val queueItems: StateFlow<List<WishlistItem>> = wishlistRepository.observeWishlist()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val summary: StateFlow<WardrobeInsightSummary> = items.map { wardrobeItems ->
        WardrobeInsightSummary(
            coreItems = wardrobeItems.count { it.capsuleRole == CapsuleRole.CORE_BASIC },
            highVersatility = wardrobeItems.count { it.versatilityScore >= 80 },
            duplicateRisks = wardrobeItems.count { it.isDuplicateRisk },
            wishlistGaps = wardrobeItems.count { it.isWishlist }
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), WardrobeInsightSummary(0, 0, 0, 0))

    val capsulePlan: StateFlow<CapsulePlan> = combine(items, selectedLanguage) { wardrobeItems, language ->
        CapsuleAnalyzer.analyze(wardrobeItems, language)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        CapsuleAnalyzer.analyze(emptyList(), AppLanguage.EN)
    )

    val auditResult: StateFlow<WardrobeAuditResult> = combine(items, selectedLanguage, hiddenAuditItemIds) { wardrobeItems, language, hiddenIds ->
        WardrobeAuditEngine.audit(wardrobeItems, language, hiddenIds)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        WardrobeAuditEngine.audit(emptyList(), AppLanguage.EN)
    )

    val visibleItems: StateFlow<List<WardrobeItem>> = combine(items, selectedFilter) { wardrobeItems, filter ->
        wardrobeItems.filter { item ->
            when (filter) {
                "All" -> true
                "Tops" -> item.category == WardrobeCategory.TOPS
                "Bottoms" -> item.category == WardrobeCategory.BOTTOMS
                "Dresses" -> item.category == WardrobeCategory.DRESSES
                "Outerwear" -> item.category == WardrobeCategory.OUTERWEAR
                "Shoes" -> item.category == WardrobeCategory.SHOES
                "Bags" -> item.category == WardrobeCategory.BAGS
                "Accessories" -> item.category == WardrobeCategory.ACCESSORIES
                "Rarely used" -> item.isRarelyUsed
                "Wishlist" -> item.isWishlist
                else -> true
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun selectFilter(filter: String) {
        _selectedFilter.value = filter
    }

    fun addGapToPurchaseQueue(gap: WardrobeGap) {
        viewModelScope.launch {
            val now = System.currentTimeMillis()
            val score = 85
            wishlistRepository.addWishlistItem(
                WishlistItem(
                    id = "gap_${gap.id}",
                    title = gap.title,
                    category = gap.category,
                    colorDirection = gap.suggestedColorDirection,
                    occasion = gap.suggestedOccasions.firstOrNull() ?: ShoppingOccasion.EVERYDAY,
                    season = ShoppingSeason.ALL_SEASON,
                    source = WishlistSource.CAPSULE_GAP,
                    priority = PurchasePriorityEngine.priority(score),
                    wardrobeValueScore = score,
                    duplicateRiskLevel = null,
                    expectedOutfitGain = gap.expectedOutfitGain,
                    reason = gap.reason,
                    createdAtMillis = now,
                    createdDateLabel = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date(now)),
                    isArchived = false
                )
            )
        }
    }

    fun keepAuditItem(auditItem: WardrobeAuditItem) {
        hiddenAuditItemIds.value = hiddenAuditItemIds.value + auditItem.wardrobeItemId
    }

    fun archiveAuditItem(auditItem: WardrobeAuditItem) {
        hiddenAuditItemIds.value = hiddenAuditItemIds.value + auditItem.wardrobeItemId
    }

    fun replaceAuditItem(auditItem: WardrobeAuditItem) {
        createWishlistFromAudit(auditItem, WishlistSource.DUPLICATE_ALTERNATIVE)
        hiddenAuditItemIds.value = hiddenAuditItemIds.value + auditItem.wardrobeItemId
    }

    fun wishlistSimilarAuditItem(auditItem: WardrobeAuditItem) {
        createWishlistFromAudit(auditItem, WishlistSource.DUPLICATE_ALTERNATIVE)
    }

    private fun createWishlistFromAudit(auditItem: WardrobeAuditItem, source: WishlistSource) {
        val sourceItem = items.value.firstOrNull { it.id == auditItem.wardrobeItemId } ?: return
        viewModelScope.launch {
            val now = System.currentTimeMillis()
            val score = (auditItem.auditScore + 20).coerceIn(35, 85)
            wishlistRepository.addWishlistItem(
                WishlistItem(
                    id = "audit_${auditItem.wardrobeItemId}_$now",
                    title = auditItem.replacementHint ?: auditItem.itemTitle,
                    category = sourceItem.category.toShoppingCategory(),
                    colorDirection = replacementColor(sourceItem.colorDirection),
                    occasion = sourceItem.occasionTags.firstOrNull() ?: ShoppingOccasion.EVERYDAY,
                    season = sourceItem.seasons.firstOrNull() ?: ShoppingSeason.ALL_SEASON,
                    source = source,
                    priority = PurchasePriorityEngine.priority(score),
                    wardrobeValueScore = score,
                    duplicateRiskLevel = null,
                    expectedOutfitGain = if (selectedLanguage.value == AppLanguage.RU) "3-5 образов" else "3-5 outfits",
                    reason = auditItem.suggestedAction,
                    createdAtMillis = now,
                    createdDateLabel = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date(now)),
                    isArchived = false
                )
            )
        }
    }

    fun compatibleItems(item: WardrobeItem): List<WardrobeItem> {
        return items.value
            .filterNot { it.id == item.id }
            .sortedWith(
                compareByDescending<WardrobeItem> { candidate ->
                    compatibilityScore(item, candidate)
                }.thenByDescending { it.versatilityScore }
            )
            .take(4)
    }

    fun styleFormulas(item: WardrobeItem, language: AppLanguage): List<String> {
        return when (item.category) {
            WardrobeCategory.TOPS -> listOf(
                if (language == AppLanguage.RU) "Верх + прямые джинсы + лоферы" else "Top + straight jeans + loafers",
                if (language == AppLanguage.RU) "Верх + классические брюки + тренч" else "Top + tailored trousers + trench"
            )
            WardrobeCategory.BOTTOMS -> listOf(
                if (language == AppLanguage.RU) "Брюки/джинсы + рубашка + жакет" else "Bottom + shirt + blazer",
                if (language == AppLanguage.RU) "Брюки/джинсы + трикотаж + ботинки" else "Bottom + knit + ankle boots"
            )
            WardrobeCategory.DRESSES -> listOf(
                if (language == AppLanguage.RU) "Платье + тренч + лоферы" else "Dress + trench + loafers",
                if (language == AppLanguage.RU) "Платье + жакет + сумка" else "Dress + blazer + handbag"
            )
            WardrobeCategory.OUTERWEAR -> listOf(
                if (language == AppLanguage.RU) "Верхняя одежда + рубашка + джинсы" else "Outerwear + shirt + jeans",
                if (language == AppLanguage.RU) "Верхняя одежда + платье + ботинки" else "Outerwear + dress + boots"
            )
            WardrobeCategory.SHOES -> listOf(
                if (language == AppLanguage.RU) "Обувь + джинсы + рубашка" else "Shoes + jeans + shirt",
                if (language == AppLanguage.RU) "Обувь + брюки + трикотаж" else "Shoes + trousers + knit"
            )
            WardrobeCategory.BAGS, WardrobeCategory.ACCESSORIES -> listOf(
                if (language == AppLanguage.RU) "Аксессуар + базовый верх + нейтральный низ" else "Accessory + core top + neutral bottom",
                if (language == AppLanguage.RU) "Аксессуар + жакет + лоферы" else "Accessory + blazer + loafers"
            )
        }
    }

    fun riskTexts(item: WardrobeItem, language: AppLanguage): List<String> {
        val risks = buildList {
            if (item.isDuplicateRisk) {
                add(if (language == AppLanguage.RU) "Есть риск дублировать похожую вещь." else "May duplicate a similar item.")
            }
            if (item.isRarelyUsed) {
                add(if (language == AppLanguage.RU) "Низкая текущая частота использования." else "Currently has low wear frequency.")
            }
            if (item.isWishlist) {
                add(if (language == AppLanguage.RU) "Пока находится в списке желаний." else "Still marked as a wishlist gap.")
            }
            if (item.colorDirection == ShoppingColorDirection.BRIGHT) {
                add(if (language == AppLanguage.RU) "Яркий цвет может ограничить повторяемость." else "Bright color may reduce repeatability.")
            }
        }
        return risks.ifEmpty {
            listOf(if (language == AppLanguage.RU) "Критичных рисков не отмечено." else "No major risks flagged.")
        }
    }

    private fun compatibilityScore(item: WardrobeItem, candidate: WardrobeItem): Int {
        var score = 0
        if (candidate.colorDirection in neutralColors) score += 3
        if (item.occasionTags.any { it in candidate.occasionTags }) score += 3
        if (item.category != candidate.category) score += 2
        if (isComplementaryCategory(item.category, candidate.category)) score += 3
        return score
    }

    private fun isComplementaryCategory(first: WardrobeCategory, second: WardrobeCategory): Boolean {
        return when (first) {
            WardrobeCategory.TOPS -> second in listOf(WardrobeCategory.BOTTOMS, WardrobeCategory.OUTERWEAR, WardrobeCategory.SHOES)
            WardrobeCategory.BOTTOMS -> second in listOf(WardrobeCategory.TOPS, WardrobeCategory.SHOES, WardrobeCategory.OUTERWEAR)
            WardrobeCategory.DRESSES -> second in listOf(WardrobeCategory.OUTERWEAR, WardrobeCategory.SHOES, WardrobeCategory.BAGS)
            WardrobeCategory.OUTERWEAR -> second in listOf(WardrobeCategory.TOPS, WardrobeCategory.BOTTOMS, WardrobeCategory.DRESSES)
            WardrobeCategory.SHOES -> second in listOf(WardrobeCategory.TOPS, WardrobeCategory.BOTTOMS, WardrobeCategory.DRESSES)
            WardrobeCategory.BAGS, WardrobeCategory.ACCESSORIES -> second != first
        }
    }

    private fun WardrobeCategory.toShoppingCategory(): ShoppingCategory = when (this) {
        WardrobeCategory.TOPS -> ShoppingCategory.TOP
        WardrobeCategory.BOTTOMS -> ShoppingCategory.BOTTOM
        WardrobeCategory.DRESSES -> ShoppingCategory.DRESS
        WardrobeCategory.OUTERWEAR -> ShoppingCategory.OUTERWEAR
        WardrobeCategory.SHOES -> ShoppingCategory.SHOES
        WardrobeCategory.BAGS -> ShoppingCategory.BAG
        WardrobeCategory.ACCESSORIES -> ShoppingCategory.ACCESSORY
    }

    private fun replacementColor(color: ShoppingColorDirection): ShoppingColorDirection {
        return when (color) {
            ShoppingColorDirection.BRIGHT,
            ShoppingColorDirection.ACCENT,
            ShoppingColorDirection.DARK,
            ShoppingColorDirection.LIGHT -> ShoppingColorDirection.NEUTRAL
            else -> color
        }
    }

    private val neutralColors = setOf(
        ShoppingColorDirection.NEUTRAL,
        ShoppingColorDirection.WARM_NEUTRAL,
        ShoppingColorDirection.COOL_NEUTRAL,
        ShoppingColorDirection.DARK,
        ShoppingColorDirection.LIGHT
    )
}

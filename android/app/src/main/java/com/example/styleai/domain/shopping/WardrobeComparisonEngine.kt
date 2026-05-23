package com.example.styleai.domain.shopping

import com.example.styleai.domain.capsule.CapsuleAnalyzer
import com.example.styleai.domain.model.AppLanguage
import com.example.styleai.domain.wardrobe.WardrobeCategory
import com.example.styleai.domain.wardrobe.WardrobeItem

object WardrobeComparisonEngine {
    fun compare(
        draft: ShoppingItemDraft,
        wardrobeItems: List<WardrobeItem>,
        language: AppLanguage
    ): ShoppingComparisonResult {
        val similarItems = wardrobeItems.filter { item ->
            item.category.toShoppingCategory() == draft.category &&
                item.colorDirection.isSimilarTo(draft.colorDirection) &&
                item.occasionTags.any { it == draft.occasion }
        }
        val exactItems = similarItems.filter { item ->
            item.colorDirection == draft.colorDirection
        }
        val wardrobeGapMatch = CapsuleAnalyzer.matchesHighPriorityGap(
            items = wardrobeItems,
            category = draft.category,
            colorDirection = draft.colorDirection,
            occasion = draft.occasion,
            language = language
        )

        val duplicateRisk = when {
            exactItems.size >= 2 -> DuplicateRisk(
                level = DuplicateRiskLevel.HIGH,
                score = 85,
                matchedItems = exactItems.map { it.title },
                reason = if (language == AppLanguage.RU) {
                    "В гардеробе уже есть ${exactItems.size} похожие вещи той же категории и цветового направления."
                } else {
                    "You already own ${exactItems.size} similar items in the same category and color direction."
                }
            )
            similarItems.isNotEmpty() -> DuplicateRisk(
                level = DuplicateRiskLevel.MEDIUM,
                score = 55,
                matchedItems = similarItems.map { it.title },
                reason = if (language == AppLanguage.RU) {
                    "Есть похожая вещь с пересекающимся сценарием использования."
                } else {
                    "You own a similar item with overlapping use cases."
                }
            )
            else -> DuplicateRisk(
                level = DuplicateRiskLevel.LOW,
                score = if (wardrobeGapMatch) 10 else 20,
                matchedItems = emptyList(),
                reason = if (wardrobeGapMatch) {
                    if (language == AppLanguage.RU) {
                        "Эта категория сейчас слабо представлена в гардеробе."
                    } else {
                        "This category is currently underrepresented in your wardrobe."
                    }
                } else {
                    if (language == AppLanguage.RU) {
                        "Близких дублей по категории, цвету и сценарию не найдено."
                    } else {
                        "No close duplicate was found by category, color, and occasion."
                    }
                }
            )
        }

        val duplicateAdjustment = when (duplicateRisk.level) {
            DuplicateRiskLevel.HIGH -> -20
            DuplicateRiskLevel.MEDIUM -> -8
            DuplicateRiskLevel.LOW -> 0
        }
        val gapAdjustment = if (wardrobeGapMatch) 15 else 0

        return ShoppingComparisonResult(
            duplicateRisk = duplicateRisk,
            similarOwnedItems = duplicateRisk.matchedItems,
            betterAlternativeHint = betterAlternativeHint(duplicateRisk.level, draft, language),
            wardrobeGapMatch = wardrobeGapMatch,
            recommendationAdjustment = duplicateAdjustment + gapAdjustment
        )
    }

    private fun betterAlternativeHint(
        riskLevel: DuplicateRiskLevel,
        draft: ShoppingItemDraft,
        language: AppLanguage
    ): String? {
        if (riskLevel == DuplicateRiskLevel.LOW) return null
        val colorHint = when (draft.colorDirection) {
            ShoppingColorDirection.NEUTRAL -> ShoppingColorDirection.WARM_NEUTRAL
            ShoppingColorDirection.WARM_NEUTRAL -> ShoppingColorDirection.COOL_NEUTRAL
            ShoppingColorDirection.COOL_NEUTRAL -> ShoppingColorDirection.WARM_NEUTRAL
            ShoppingColorDirection.ACCENT,
            ShoppingColorDirection.BRIGHT,
            ShoppingColorDirection.DARK,
            ShoppingColorDirection.LIGHT -> ShoppingColorDirection.NEUTRAL
        }.label(language)
        return if (language == AppLanguage.RU) {
            "Лучше искать вариант в направлении \"$colorHint\" или выбрать категорию, которая закрывает пробел."
        } else {
            "Consider a $colorHint option or a category that fills a real wardrobe gap."
        }
    }

    private fun ShoppingColorDirection.isSimilarTo(other: ShoppingColorDirection): Boolean {
        if (this == other) return true
        return this in neutralColors && other in neutralColors
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

    private val neutralColors = setOf(
        ShoppingColorDirection.NEUTRAL,
        ShoppingColorDirection.WARM_NEUTRAL,
        ShoppingColorDirection.COOL_NEUTRAL
    )
}

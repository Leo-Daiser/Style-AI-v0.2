package com.example.styleai.domain.capsule

import com.example.styleai.domain.model.AppLanguage
import com.example.styleai.domain.shopping.ShoppingCategory
import com.example.styleai.domain.shopping.ShoppingColorDirection
import com.example.styleai.domain.shopping.ShoppingOccasion
import com.example.styleai.domain.shopping.ShoppingSeason
import com.example.styleai.domain.shopping.label
import com.example.styleai.domain.wardrobe.WardrobeCategory
import com.example.styleai.domain.wardrobe.WardrobeItem
import com.example.styleai.domain.wardrobe.label

object CapsuleAnalyzer {
    fun analyze(items: List<WardrobeItem>, language: AppLanguage): CapsulePlan {
        val gaps = buildGaps(items, language)
        val duplicateWarnings = duplicateWarnings(items, language)
        val underusedWarnings = underusedWarnings(items, language)
        val avoidBuyingAgain = avoidBuyingAgain(items, duplicateWarnings, language)

        return CapsulePlan(
            capsuleCoverageScore = coverageScore(items, duplicateWarnings),
            highPriorityGaps = gaps.filter { it.priority == GapPriority.HIGH },
            duplicateWarnings = duplicateWarnings,
            underusedWarnings = underusedWarnings,
            suggestedNextPurchases = gaps.sortedBy { it.priority.ordinal }.take(3),
            avoidBuyingAgain = avoidBuyingAgain
        )
    }

    fun matchesHighPriorityGap(
        items: List<WardrobeItem>,
        category: ShoppingCategory,
        colorDirection: ShoppingColorDirection,
        occasion: ShoppingOccasion,
        language: AppLanguage
    ): Boolean {
        return analyze(items, language).highPriorityGaps.any { gap ->
            gap.category == category &&
                gap.suggestedColorDirection == colorDirection &&
                gap.suggestedOccasions.contains(occasion)
        }
    }

    private fun buildGaps(items: List<WardrobeItem>, language: AppLanguage): List<WardrobeGap> {
        val gaps = mutableListOf<WardrobeGap>()
        if (!items.any { it.category == WardrobeCategory.SHOES && it.colorDirection in neutralColors }) {
            gaps += WardrobeGap(
                id = "neutral-everyday-shoes",
                title = if (language == AppLanguage.RU) "Нейтральная обувь на каждый день" else "Neutral everyday shoes",
                category = ShoppingCategory.SHOES,
                priority = GapPriority.HIGH,
                reason = if (language == AppLanguage.RU) {
                    "Нейтральная обувь связывает больше повседневных и офисных образов."
                } else {
                    "Neutral shoes connect more everyday and office outfits."
                },
                suggestedColorDirection = ShoppingColorDirection.NEUTRAL,
                suggestedOccasions = listOf(ShoppingOccasion.EVERYDAY, ShoppingOccasion.OFFICE, ShoppingOccasion.TRAVEL),
                expectedOutfitGain = if (language == AppLanguage.RU) "+5-7 образов" else "+5-7 outfits"
            )
        }
        if (!items.any { it.category == WardrobeCategory.OUTERWEAR }) {
            gaps += WardrobeGap(
                id = "practical-outerwear-layer",
                title = if (language == AppLanguage.RU) "Практичный верхний слой" else "Practical outerwear layer",
                category = ShoppingCategory.OUTERWEAR,
                priority = GapPriority.HIGH,
                reason = if (language == AppLanguage.RU) {
                    "Верхний слой расширяет сезонность базовых вещей."
                } else {
                    "Outerwear extends the season range of core pieces."
                },
                suggestedColorDirection = ShoppingColorDirection.WARM_NEUTRAL,
                suggestedOccasions = listOf(ShoppingOccasion.EVERYDAY, ShoppingOccasion.OFFICE),
                expectedOutfitGain = if (language == AppLanguage.RU) "+4-6 образов" else "+4-6 outfits"
            )
        }
        if (!items.any { it.category == WardrobeCategory.BOTTOMS && it.colorDirection in neutralColors }) {
            gaps += WardrobeGap(
                id = "neutral-bottom-base",
                title = if (language == AppLanguage.RU) "Нейтральная база низа" else "Neutral bottom base",
                category = ShoppingCategory.BOTTOM,
                priority = GapPriority.HIGH,
                reason = if (language == AppLanguage.RU) {
                    "Нейтральный низ нужен для повторяемых капсульных сочетаний."
                } else {
                    "A neutral bottom is needed for repeatable capsule outfits."
                },
                suggestedColorDirection = ShoppingColorDirection.NEUTRAL,
                suggestedOccasions = listOf(ShoppingOccasion.EVERYDAY, ShoppingOccasion.OFFICE),
                expectedOutfitGain = if (language == AppLanguage.RU) "+5-8 образов" else "+5-8 outfits"
            )
        }
        if (!items.any { it.category == WardrobeCategory.BAGS || it.category == WardrobeCategory.ACCESSORIES }) {
            gaps += WardrobeGap(
                id = "accessory-anchor",
                title = if (language == AppLanguage.RU) "Нейтральный аксессуар-якорь" else "Neutral accessory anchor",
                category = ShoppingCategory.BAG,
                priority = GapPriority.MEDIUM,
                reason = if (language == AppLanguage.RU) {
                    "Сумка или ремень завершает базовые комплекты без лишних покупок."
                } else {
                    "A bag or belt finishes core outfits without adding clutter."
                },
                suggestedColorDirection = ShoppingColorDirection.WARM_NEUTRAL,
                suggestedOccasions = listOf(ShoppingOccasion.EVERYDAY, ShoppingOccasion.OFFICE),
                expectedOutfitGain = if (language == AppLanguage.RU) "+3-5 образов" else "+3-5 outfits"
            )
        }
        return gaps
    }

    private fun coverageScore(items: List<WardrobeItem>, duplicateWarnings: List<String>): Int {
        var score = 40
        if (items.any { it.category == WardrobeCategory.TOPS && it.colorDirection in neutralColors }) score += 10
        if (items.any { it.category == WardrobeCategory.BOTTOMS && it.colorDirection in neutralColors }) score += 10
        if (items.any { it.category == WardrobeCategory.SHOES && it.colorDirection in neutralColors }) score += 10
        if (items.any { it.category == WardrobeCategory.OUTERWEAR }) score += 10
        if (items.any { it.category == WardrobeCategory.BAGS || it.category == WardrobeCategory.ACCESSORIES }) score += 10
        if (items.count { it.versatilityScore >= 80 } >= 3) score += 10
        if (
            items.any { it.occasionTags.contains(ShoppingOccasion.OFFICE) } &&
            items.any { it.occasionTags.contains(ShoppingOccasion.EVERYDAY) }
        ) {
            score += 10
        }
        if (duplicateWarnings.isNotEmpty() || items.any { it.isDuplicateRisk }) score -= 10
        if (items.count { it.isRarelyUsed } >= 3) score -= 10
        if (items.none { it.seasons.contains(ShoppingSeason.ALL_SEASON) }) score -= 10
        return score.coerceIn(0, 100)
    }

    private fun duplicateWarnings(items: List<WardrobeItem>, language: AppLanguage): List<String> {
        val warnings = items
            .groupBy { it.category to it.colorDirection }
            .filter { (_, grouped) -> grouped.size >= 3 }
            .map { (key, grouped) ->
                val category = key.first.label(language).lowercase()
                val color = key.second.label(language).lowercase()
                if (language == AppLanguage.RU) {
                    "Слишком много похожих вещей: ${grouped.size} в группе $category / $color."
                } else {
                    "Possible duplication: ${grouped.size} items in $category / $color."
                }
            }
            .toMutableList()

        val accentCount = items.count { it.colorDirection in listOf(ShoppingColorDirection.ACCENT, ShoppingColorDirection.BRIGHT) }
        val neutralCoreCount = items.count {
            it.colorDirection in neutralColors &&
                it.category in listOf(WardrobeCategory.TOPS, WardrobeCategory.BOTTOMS, WardrobeCategory.SHOES)
        }
        if (accentCount >= 3 && neutralCoreCount < 3) {
            warnings += if (language == AppLanguage.RU) {
                "Слишком много акцентных вещей и мало базовых нейтралей."
            } else {
                "Too many accent items, not enough base pieces."
            }
        }
        return warnings
    }

    private fun underusedWarnings(items: List<WardrobeItem>, language: AppLanguage): List<String> {
        return items.filter { it.isRarelyUsed }.take(3).map { item ->
            if (language == AppLanguage.RU) {
                "${item.title.localizedTitle(language)}: редко используется, проверьте сочетания перед новыми покупками."
            } else {
                "${item.title}: rarely used, review styling options before buying more."
            }
        }
    }

    private fun avoidBuyingAgain(
        items: List<WardrobeItem>,
        duplicateWarnings: List<String>,
        language: AppLanguage
    ): List<String> {
        val avoid = mutableListOf<String>()
        items
            .groupBy { it.category to it.colorDirection }
            .filter { (_, grouped) -> grouped.size >= 2 }
            .forEach { (key, _) ->
                val category = key.first.label(language).lowercase()
                val color = key.second.label(language).lowercase()
                avoid += if (language == AppLanguage.RU) {
                    "Еще одну вещь категории $category в цвете $color."
                } else {
                    "Another $color $category item."
                }
            }
        if (duplicateWarnings.isNotEmpty()) {
            avoid += if (language == AppLanguage.RU) {
                "Импульсные акцентные покупки без понятного сценария."
            } else {
                "Impulse accent pieces without a clear use case."
            }
        }
        return avoid.distinct().take(4)
    }

    private val neutralColors = setOf(
        ShoppingColorDirection.NEUTRAL,
        ShoppingColorDirection.WARM_NEUTRAL,
        ShoppingColorDirection.COOL_NEUTRAL
    )

    private fun String.localizedTitle(language: AppLanguage): String {
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
}

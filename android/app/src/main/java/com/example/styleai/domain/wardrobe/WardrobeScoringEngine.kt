package com.example.styleai.domain.wardrobe

import com.example.styleai.domain.shopping.ShoppingColorDirection
import com.example.styleai.domain.shopping.ShoppingSeason

object WardrobeScoringEngine {
    fun score(item: WardrobeItem): Int {
        var score = 0

        if (item.colorDirection in neutralColors) score += 20
        if (item.occasionTags.size >= 3) score += 25
        if (item.seasons.contains(ShoppingSeason.ALL_SEASON) || item.seasons.size >= 3) score += 20
        if (item.capsuleRole == CapsuleRole.CORE_BASIC) score += 15
        if (item.isDuplicateRisk) score -= 20
        if (item.isRarelyUsed) score -= 15
        if (item.isWishlist) score -= 5
        if (
            item.category in listOf(WardrobeCategory.SHOES, WardrobeCategory.OUTERWEAR, WardrobeCategory.BAGS) &&
            item.colorDirection in neutralColors
        ) {
            score += 10
        }

        return score.coerceIn(0, 100)
    }

    fun withUpdatedScore(item: WardrobeItem): WardrobeItem {
        return item.copy(versatilityScore = score(item))
    }

    private val neutralColors = setOf(
        ShoppingColorDirection.NEUTRAL,
        ShoppingColorDirection.WARM_NEUTRAL,
        ShoppingColorDirection.COOL_NEUTRAL
    )
}

package com.example.styleai.domain.wishlist

import com.example.styleai.domain.shopping.CareComplexity
import com.example.styleai.domain.shopping.DecisionConfidence
import com.example.styleai.domain.shopping.DuplicateRiskLevel
import com.example.styleai.domain.shopping.ExpectedWearFrequency
import com.example.styleai.domain.shopping.ShoppingColorDirection
import com.example.styleai.domain.shopping.ShoppingItemDraft
import com.example.styleai.domain.shopping.ShoppingPriceLevel

object PurchasePriorityEngine {
    fun score(
        draft: ShoppingItemDraft,
        duplicateRiskLevel: DuplicateRiskLevel?,
        matchesHighPriorityGap: Boolean,
        matchesStyleProfile: Boolean = false
    ): Int {
        var score = 50
        if (matchesHighPriorityGap) score += 25
        if (draft.expectedWearFrequency in listOf(ExpectedWearFrequency.WEEKLY, ExpectedWearFrequency.MONTHLY)) score += 15
        if (draft.colorDirection in coreColors) score += 10
        if (duplicateRiskLevel == DuplicateRiskLevel.LOW || duplicateRiskLevel == null) score += 10
        if (matchesStyleProfile) score += 10
        if (duplicateRiskLevel == DuplicateRiskLevel.HIGH) score -= 25
        if (draft.expectedWearFrequency == ExpectedWearFrequency.ONE_TIME) score -= 20
        if (draft.priceLevel == ShoppingPriceLevel.VERY_HIGH) score -= 15
        if (draft.careComplexity == CareComplexity.DIFFICULT) score -= 10
        if (draft.confidence == DecisionConfidence.LOW) score -= 15
        return score.coerceIn(0, 100)
    }

    fun priority(score: Int): PurchasePriority = when {
        score >= 75 -> PurchasePriority.HIGH
        score >= 55 -> PurchasePriority.MEDIUM
        score >= 35 -> PurchasePriority.LOW
        else -> PurchasePriority.NOT_RECOMMENDED
    }

    fun withUpdatedPriority(item: WishlistItem): WishlistItem {
        return item.copy(priority = priority(item.wardrobeValueScore))
    }

    private val coreColors = setOf(
        ShoppingColorDirection.NEUTRAL,
        ShoppingColorDirection.WARM_NEUTRAL,
        ShoppingColorDirection.COOL_NEUTRAL
    )
}

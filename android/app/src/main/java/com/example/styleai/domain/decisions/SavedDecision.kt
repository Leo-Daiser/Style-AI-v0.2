package com.example.styleai.domain.decisions

import com.example.styleai.domain.shopping.ShoppingCategory
import com.example.styleai.domain.shopping.ShoppingColorDirection
import com.example.styleai.domain.shopping.DuplicateRiskLevel
import com.example.styleai.domain.shopping.ShoppingOccasion
import com.example.styleai.domain.shopping.ShoppingSeason
import com.example.styleai.domain.shopping.ShoppingVerdict

data class SavedDecision(
    val id: String,
    val category: ShoppingCategory,
    val colorDirection: ShoppingColorDirection,
    val occasion: ShoppingOccasion,
    val season: ShoppingSeason,
    val verdict: ShoppingVerdict,
    val score: Int,
    val mainReason: String,
    val positiveReasons: List<String>,
    val risks: List<String>,
    val estimatedOutfitCount: String,
    val capsuleImpact: String,
    val recommendation: String,
    val createdAtMillis: Long,
    val createdDateLabel: String,
    val duplicateRiskLevel: DuplicateRiskLevel? = null,
    val duplicateRiskScore: Int? = null,
    val similarOwnedItems: List<String> = emptyList(),
    val comparisonReason: String? = null,
    val betterAlternativeHint: String? = null,
    val wardrobeGapMatch: Boolean = false
)

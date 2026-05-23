package com.example.styleai.domain.shopping

data class ShoppingComparisonResult(
    val duplicateRisk: DuplicateRisk,
    val similarOwnedItems: List<String>,
    val betterAlternativeHint: String?,
    val wardrobeGapMatch: Boolean,
    val recommendationAdjustment: Int
)

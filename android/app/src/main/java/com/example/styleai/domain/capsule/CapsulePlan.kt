package com.example.styleai.domain.capsule

data class CapsulePlan(
    val capsuleCoverageScore: Int,
    val highPriorityGaps: List<WardrobeGap>,
    val duplicateWarnings: List<String>,
    val underusedWarnings: List<String>,
    val suggestedNextPurchases: List<WardrobeGap>,
    val avoidBuyingAgain: List<String>
)

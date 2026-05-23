package com.example.styleai.domain.capsule

import com.example.styleai.domain.model.AppLanguage
import com.example.styleai.domain.shopping.ShoppingCategory
import com.example.styleai.domain.shopping.ShoppingColorDirection
import com.example.styleai.domain.shopping.ShoppingOccasion

enum class GapPriority {
    HIGH,
    MEDIUM,
    LOW
}

data class WardrobeGap(
    val id: String,
    val title: String,
    val category: ShoppingCategory,
    val priority: GapPriority,
    val reason: String,
    val suggestedColorDirection: ShoppingColorDirection,
    val suggestedOccasions: List<ShoppingOccasion>,
    val expectedOutfitGain: String
)

fun GapPriority.label(language: AppLanguage): String = when (this) {
    GapPriority.HIGH -> if (language == AppLanguage.RU) "Высокий" else "High"
    GapPriority.MEDIUM -> if (language == AppLanguage.RU) "Средний" else "Medium"
    GapPriority.LOW -> if (language == AppLanguage.RU) "Низкий" else "Low"
}

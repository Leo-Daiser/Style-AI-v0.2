package com.example.styleai.domain.wishlist

import com.example.styleai.domain.model.AppLanguage
import com.example.styleai.domain.shopping.DuplicateRiskLevel
import com.example.styleai.domain.shopping.ShoppingCategory
import com.example.styleai.domain.shopping.ShoppingColorDirection
import com.example.styleai.domain.shopping.ShoppingOccasion
import com.example.styleai.domain.shopping.ShoppingSeason

enum class WishlistSource {
    MANUAL,
    SHOPPING_DECISION,
    CAPSULE_GAP,
    DUPLICATE_ALTERNATIVE,
    PROFILE_RULE
}

enum class PurchasePriority {
    HIGH,
    MEDIUM,
    LOW,
    NOT_RECOMMENDED
}

data class WishlistItem(
    val id: String,
    val title: String,
    val category: ShoppingCategory,
    val colorDirection: ShoppingColorDirection,
    val occasion: ShoppingOccasion,
    val season: ShoppingSeason,
    val source: WishlistSource,
    val priority: PurchasePriority,
    val wardrobeValueScore: Int,
    val duplicateRiskLevel: DuplicateRiskLevel?,
    val expectedOutfitGain: String,
    val reason: String,
    val createdAtMillis: Long,
    val createdDateLabel: String,
    val isArchived: Boolean
)

fun PurchasePriority.label(language: AppLanguage): String = when (this) {
    PurchasePriority.HIGH -> if (language == AppLanguage.RU) "Высокий" else "High"
    PurchasePriority.MEDIUM -> if (language == AppLanguage.RU) "Средний" else "Medium"
    PurchasePriority.LOW -> if (language == AppLanguage.RU) "Низкий" else "Low"
    PurchasePriority.NOT_RECOMMENDED -> if (language == AppLanguage.RU) "Не рекомендуется" else "Not recommended"
}

fun WishlistSource.label(language: AppLanguage): String = when (this) {
    WishlistSource.MANUAL -> if (language == AppLanguage.RU) "Вручную" else "Manual"
    WishlistSource.SHOPPING_DECISION -> if (language == AppLanguage.RU) "Решение о покупке" else "Shopping decision"
    WishlistSource.CAPSULE_GAP -> if (language == AppLanguage.RU) "Пробел капсулы" else "Capsule gap"
    WishlistSource.DUPLICATE_ALTERNATIVE -> if (language == AppLanguage.RU) "Альтернатива дублю" else "Duplicate alternative"
    WishlistSource.PROFILE_RULE -> if (language == AppLanguage.RU) "Правило профиля" else "Profile rule"
}

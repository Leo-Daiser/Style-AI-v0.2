package com.example.styleai.domain.wardrobe

import androidx.annotation.DrawableRes
import com.example.styleai.domain.model.AppLanguage
import com.example.styleai.domain.shopping.ShoppingColorDirection
import com.example.styleai.domain.shopping.ShoppingOccasion
import com.example.styleai.domain.shopping.ShoppingSeason

enum class WardrobeCategory {
    TOPS, BOTTOMS, DRESSES, OUTERWEAR, SHOES, BAGS, ACCESSORIES
}

enum class CapsuleRole {
    CORE_BASIC,
    STATEMENT,
    LAYERING_PIECE,
    SEASONAL_PIECE,
    OCCASION_PIECE,
    ACCESSORY_ANCHOR
}

data class WardrobeItem(
    val id: String,
    val title: String,
    val category: WardrobeCategory,
    val colorDirection: ShoppingColorDirection,
    val seasons: List<ShoppingSeason>,
    val occasionTags: List<ShoppingOccasion>,
    val capsuleRole: CapsuleRole,
    val versatilityScore: Int,
    val outfitCount: Int,
    val isWishlist: Boolean,
    val isRarelyUsed: Boolean,
    val isDuplicateRisk: Boolean,
    @DrawableRes val drawableRes: Int?
)

data class WardrobeInsightSummary(
    val coreItems: Int,
    val highVersatility: Int,
    val duplicateRisks: Int,
    val wishlistGaps: Int
)

fun WardrobeCategory.label(language: AppLanguage): String = when (this) {
    WardrobeCategory.TOPS -> if (language == AppLanguage.RU) "Верх" else "Tops"
    WardrobeCategory.BOTTOMS -> if (language == AppLanguage.RU) "Низ" else "Bottoms"
    WardrobeCategory.DRESSES -> if (language == AppLanguage.RU) "Платья" else "Dresses"
    WardrobeCategory.OUTERWEAR -> if (language == AppLanguage.RU) "Верхняя одежда" else "Outerwear"
    WardrobeCategory.SHOES -> if (language == AppLanguage.RU) "Обувь" else "Shoes"
    WardrobeCategory.BAGS -> if (language == AppLanguage.RU) "Сумки" else "Bags"
    WardrobeCategory.ACCESSORIES -> if (language == AppLanguage.RU) "Аксессуары" else "Accessories"
}

fun CapsuleRole.label(language: AppLanguage): String = when (this) {
    CapsuleRole.CORE_BASIC -> if (language == AppLanguage.RU) "Базовая вещь" else "Core basic"
    CapsuleRole.STATEMENT -> if (language == AppLanguage.RU) "Акцентная вещь" else "Statement"
    CapsuleRole.LAYERING_PIECE -> if (language == AppLanguage.RU) "Слой" else "Layering piece"
    CapsuleRole.SEASONAL_PIECE -> if (language == AppLanguage.RU) "Сезонная вещь" else "Seasonal piece"
    CapsuleRole.OCCASION_PIECE -> if (language == AppLanguage.RU) "Вещь для случая" else "Occasion piece"
    CapsuleRole.ACCESSORY_ANCHOR -> if (language == AppLanguage.RU) "Акцент аксессуаров" else "Accessory anchor"
}

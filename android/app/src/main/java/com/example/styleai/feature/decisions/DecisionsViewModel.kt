package com.example.styleai.feature.decisions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.styleai.R
import com.example.styleai.domain.decisions.SavedDecision
import com.example.styleai.domain.decisions.SavedDecisionRepository
import com.example.styleai.domain.model.AppLanguage
import com.example.styleai.domain.repository.StyleRepository
import com.example.styleai.domain.shopping.ShoppingCategory
import com.example.styleai.domain.shopping.ShoppingColorDirection
import com.example.styleai.domain.shopping.ShoppingOccasion
import com.example.styleai.domain.shopping.ShoppingSeason
import com.example.styleai.domain.shopping.ShoppingVerdict
import com.example.styleai.domain.wardrobe.CapsuleRole
import com.example.styleai.domain.wardrobe.WardrobeCategory
import com.example.styleai.domain.wardrobe.WardrobeItem
import com.example.styleai.domain.wardrobe.WardrobeRepository
import com.example.styleai.domain.wardrobe.WardrobeScoringEngine
import com.example.styleai.domain.wardrobe.label
import com.example.styleai.domain.wishlist.WishlistItem
import com.example.styleai.domain.wishlist.WishlistRepository
import com.example.styleai.domain.wishlist.WishlistSource
import com.example.styleai.domain.wishlist.PurchasePriorityEngine
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DecisionsViewModel(
    styleRepository: StyleRepository,
    private val savedDecisionRepository: SavedDecisionRepository,
    private val wardrobeRepository: WardrobeRepository,
    private val wishlistRepository: WishlistRepository
) : ViewModel() {
    val selectedLanguage: StateFlow<AppLanguage> = styleRepository.getSelectedLanguage()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AppLanguage.EN)

    val decisions: StateFlow<List<SavedDecision>> = savedDecisionRepository.observeDecisions()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val wishlist: StateFlow<List<WishlistItem>> = wishlistRepository.observeWishlist()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun deleteDecision(id: String) {
        viewModelScope.launch {
            savedDecisionRepository.deleteDecision(id)
        }
    }

    fun addToWardrobe(decision: SavedDecision) {
        if (decision.verdict != ShoppingVerdict.BUY) return
        viewModelScope.launch {
            wardrobeRepository.saveItem(decision.toWardrobeItem(selectedLanguage.value))
        }
    }

    fun markWishlistItemBought(item: WishlistItem) {
        viewModelScope.launch {
            wardrobeRepository.saveItem(item.toWardrobeItem(selectedLanguage.value))
            wishlistRepository.archiveWishlistItem(item.id)
        }
    }

    fun archiveWishlistItem(id: String) {
        viewModelScope.launch {
            wishlistRepository.archiveWishlistItem(id)
        }
    }

    fun deleteWishlistItem(id: String) {
        viewModelScope.launch {
            wishlistRepository.deleteWishlistItem(id)
        }
    }

    fun addManualWishlistCandidate() {
        viewModelScope.launch {
            val now = System.currentTimeMillis()
            val score = 62
            wishlistRepository.addWishlistItem(
                WishlistItem(
                    id = "manual_$now",
                    title = if (selectedLanguage.value == AppLanguage.RU) "Нейтральная базовая вещь" else "Neutral core candidate",
                    category = ShoppingCategory.TOP,
                    colorDirection = ShoppingColorDirection.NEUTRAL,
                    occasion = ShoppingOccasion.EVERYDAY,
                    season = ShoppingSeason.ALL_SEASON,
                    source = WishlistSource.MANUAL,
                    priority = PurchasePriorityEngine.priority(score),
                    wardrobeValueScore = score,
                    duplicateRiskLevel = null,
                    expectedOutfitGain = if (selectedLanguage.value == AppLanguage.RU) "3-4 образа" else "3-4 outfits",
                    reason = if (selectedLanguage.value == AppLanguage.RU) {
                        "Ручной кандидат без внешних ссылок и магазинов."
                    } else {
                        "Manual candidate without store links or marketplace behavior."
                    },
                    createdAtMillis = now,
                    createdDateLabel = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date(now)),
                    isArchived = false
                )
            )
        }
    }

    private fun SavedDecision.toWardrobeItem(language: AppLanguage): WardrobeItem {
        val category = category.toWardrobeCategory()
        val item = WardrobeItem(
            id = "wardrobe_from_$id",
            title = if (language == AppLanguage.RU) {
                "${category.label(language)} из проверки покупки"
            } else {
                "${category.label(language)} from shopping check"
            },
            category = category,
            colorDirection = colorDirection,
            seasons = listOf(season),
            occasionTags = listOf(occasion),
            capsuleRole = category.inferCapsuleRole(score),
            versatilityScore = score,
            outfitCount = parseOutfitCount(estimatedOutfitCount),
            isWishlist = false,
            isRarelyUsed = false,
            isDuplicateRisk = false,
            drawableRes = category.defaultDrawableRes()
        )
        return WardrobeScoringEngine.withUpdatedScore(item)
    }

    private fun ShoppingCategory.toWardrobeCategory(): WardrobeCategory = when (this) {
        ShoppingCategory.TOP -> WardrobeCategory.TOPS
        ShoppingCategory.BOTTOM -> WardrobeCategory.BOTTOMS
        ShoppingCategory.DRESS -> WardrobeCategory.DRESSES
        ShoppingCategory.OUTERWEAR -> WardrobeCategory.OUTERWEAR
        ShoppingCategory.SHOES -> WardrobeCategory.SHOES
        ShoppingCategory.BAG -> WardrobeCategory.BAGS
        ShoppingCategory.ACCESSORY -> WardrobeCategory.ACCESSORIES
    }

    private fun WardrobeCategory.inferCapsuleRole(score: Int): CapsuleRole = when (this) {
        WardrobeCategory.OUTERWEAR -> CapsuleRole.LAYERING_PIECE
        WardrobeCategory.DRESSES -> CapsuleRole.OCCASION_PIECE
        WardrobeCategory.BAGS, WardrobeCategory.ACCESSORIES -> CapsuleRole.ACCESSORY_ANCHOR
        else -> if (score >= 72) CapsuleRole.CORE_BASIC else CapsuleRole.SEASONAL_PIECE
    }

    private fun WardrobeCategory.defaultDrawableRes(): Int = when (this) {
        WardrobeCategory.TOPS -> R.drawable.white_cotton_shirt
        WardrobeCategory.BOTTOMS -> R.drawable.dark_blue_straight_jeans
        WardrobeCategory.DRESSES -> R.drawable.olive_midi_dress
        WardrobeCategory.OUTERWEAR -> R.drawable.cream_structured_blazer
        WardrobeCategory.SHOES -> R.drawable.black_leather_loafers
        WardrobeCategory.BAGS -> R.drawable.brown_leather_handbag
        WardrobeCategory.ACCESSORIES -> R.drawable.brown_leather_belt
    }

    private fun parseOutfitCount(label: String): Int {
        return Regex("""\d+""").find(label)?.value?.toIntOrNull() ?: 3
    }

    private fun WishlistItem.toWardrobeItem(language: AppLanguage): WardrobeItem {
        val category = category.toWardrobeCategory()
        val item = WardrobeItem(
            id = "wardrobe_from_wishlist_$id",
            title = if (language == AppLanguage.RU) {
                "$title из очереди покупок"
            } else {
                "$title from purchase queue"
            },
            category = category,
            colorDirection = colorDirection,
            seasons = listOf(season),
            occasionTags = listOf(occasion),
            capsuleRole = category.inferCapsuleRole(wardrobeValueScore),
            versatilityScore = wardrobeValueScore,
            outfitCount = parseOutfitCount(expectedOutfitGain),
            isWishlist = false,
            isRarelyUsed = false,
            isDuplicateRisk = duplicateRiskLevel?.name == "HIGH",
            drawableRes = category.defaultDrawableRes()
        )
        return WardrobeScoringEngine.withUpdatedScore(item)
    }
}

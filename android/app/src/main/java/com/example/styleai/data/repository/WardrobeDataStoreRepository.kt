package com.example.styleai.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.styleai.data.local.dataStore
import com.example.styleai.data.mock.VisualAssets
import com.example.styleai.domain.shopping.ShoppingColorDirection
import com.example.styleai.domain.shopping.ShoppingOccasion
import com.example.styleai.domain.shopping.ShoppingSeason
import com.example.styleai.domain.wardrobe.CapsuleRole
import com.example.styleai.domain.wardrobe.WardrobeCategory
import com.example.styleai.domain.wardrobe.WardrobeItem
import com.example.styleai.domain.wardrobe.WardrobeRepository
import com.example.styleai.domain.wardrobe.WardrobeScoringEngine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.json.JSONArray
import org.json.JSONObject

class WardrobeDataStoreRepository(
    private val context: Context
) : WardrobeRepository {
    private val wardrobeKey = stringPreferencesKey("wardrobe_items_json")

    override fun observeItems(): Flow<List<WardrobeItem>> {
        return context.dataStore.data.map { prefs ->
            val rawJson = prefs[wardrobeKey]
            if (rawJson == null) seedItems else ensureSeedCoverage(decodeItems(rawJson))
        }
    }

    override suspend fun saveItem(item: WardrobeItem) {
        val scored = WardrobeScoringEngine.withUpdatedScore(item)
        val current = observeItems().first().filterNot { it.id == scored.id }
        writeItems(listOf(scored) + current)
    }

    override suspend fun deleteItem(id: String) {
        writeItems(observeItems().first().filterNot { it.id == id })
    }

    override suspend fun clearItems() {
        writeItems(emptyList())
    }

    override suspend fun getItemById(id: String): WardrobeItem? {
        return observeItems().first().firstOrNull { it.id == id }
    }

    private suspend fun writeItems(items: List<WardrobeItem>) {
        context.dataStore.edit { prefs ->
            prefs[wardrobeKey] = encodeItems(items)
        }
    }

    private fun encodeItems(items: List<WardrobeItem>): String {
        val array = JSONArray()
        items.forEach { item ->
            array.put(
                JSONObject()
                    .put("id", item.id)
                    .put("title", item.title)
                    .put("category", item.category.name)
                    .put("colorDirection", item.colorDirection.name)
                    .put("seasons", JSONArray(item.seasons.map { it.name }))
                    .put("occasionTags", JSONArray(item.occasionTags.map { it.name }))
                    .put("capsuleRole", item.capsuleRole.name)
                    .put("versatilityScore", item.versatilityScore)
                    .put("outfitCount", item.outfitCount)
                    .put("isWishlist", item.isWishlist)
                    .put("isRarelyUsed", item.isRarelyUsed)
                    .put("isDuplicateRisk", item.isDuplicateRisk)
                    .put("drawableRes", item.drawableRes ?: 0)
            )
        }
        return array.toString()
    }

    private fun decodeItems(rawJson: String): List<WardrobeItem> {
        return try {
            val array = JSONArray(rawJson)
            buildList {
                for (index in 0 until array.length()) {
                    val item = array.getJSONObject(index)
                    add(
                        WardrobeScoringEngine.withUpdatedScore(
                            WardrobeItem(
                                id = item.getString("id"),
                                title = item.getString("title"),
                                category = WardrobeCategory.valueOf(item.getString("category")),
                                colorDirection = ShoppingColorDirection.valueOf(item.getString("colorDirection")),
                                seasons = item.getEnumList("seasons", ShoppingSeason::valueOf),
                                occasionTags = item.getEnumList("occasionTags", ShoppingOccasion::valueOf),
                                capsuleRole = CapsuleRole.valueOf(item.getString("capsuleRole")),
                                versatilityScore = item.optInt("versatilityScore", 0),
                                outfitCount = item.optInt("outfitCount", 0),
                                isWishlist = item.optBoolean("isWishlist", false),
                                isRarelyUsed = item.optBoolean("isRarelyUsed", false),
                                isDuplicateRisk = item.optBoolean("isDuplicateRisk", false),
                                drawableRes = item.optInt("drawableRes", 0).takeIf { it != 0 }
                            )
                        )
                    )
                }
            }
        } catch (exception: Exception) {
            emptyList()
        }
    }

    private fun ensureSeedCoverage(items: List<WardrobeItem>): List<WardrobeItem> {
        if (items.size >= 8) return items
        val existingIds = items.map { it.id }.toSet()
        return items + seedItems.filterNot { existingIds.contains(it.id) }
    }

    private fun <T> JSONObject.getEnumList(name: String, parser: (String) -> T): List<T> {
        val array = optJSONArray(name) ?: return emptyList()
        return buildList {
            for (index in 0 until array.length()) {
                add(parser(array.getString(index)))
            }
        }
    }

    private val seedItems: List<WardrobeItem> by lazy {
        VisualAssets.wardrobeItems.map { asset ->
            WardrobeScoringEngine.withUpdatedScore(
                WardrobeItem(
                    id = asset.id,
                    title = asset.titleEn,
                    category = asset.category.toWardrobeCategory(asset.id),
                    colorDirection = asset.colorDirection.toColorDirection(),
                    seasons = asset.season.toSeasons(),
                    occasionTags = asset.category.toOccasions(),
                    capsuleRole = asset.category.toCapsuleRole(asset.versatilityScore),
                    versatilityScore = asset.versatilityScore,
                    outfitCount = asset.outfitCount,
                    isWishlist = false,
                    isRarelyUsed = asset.versatilityScore < 80,
                    isDuplicateRisk = asset.id.contains("sneakers").not() && asset.category == "Shoes" && asset.versatilityScore < 80,
                    drawableRes = asset.drawableRes
                )
            )
        }
    }

    private fun String.toWardrobeCategory(id: String): WardrobeCategory = when (this) {
        "Tops" -> WardrobeCategory.TOPS
        "Bottoms" -> WardrobeCategory.BOTTOMS
        "Dresses" -> WardrobeCategory.DRESSES
        "Outerwear" -> WardrobeCategory.OUTERWEAR
        "Shoes" -> WardrobeCategory.SHOES
        "Accessories" -> if (id.contains("handbag")) WardrobeCategory.BAGS else WardrobeCategory.ACCESSORIES
        else -> WardrobeCategory.ACCESSORIES
    }

    private fun String.toColorDirection(): ShoppingColorDirection = when {
        equals("Neutral", ignoreCase = true) -> ShoppingColorDirection.NEUTRAL
        contains("Warm", ignoreCase = true) -> ShoppingColorDirection.WARM_NEUTRAL
        contains("Cool", ignoreCase = true) -> ShoppingColorDirection.COOL_NEUTRAL
        contains("Muted", ignoreCase = true) -> ShoppingColorDirection.ACCENT
        else -> ShoppingColorDirection.NEUTRAL
    }

    private fun String.toSeasons(): List<ShoppingSeason> {
        if (contains("All season", ignoreCase = true)) return listOf(ShoppingSeason.ALL_SEASON)
        return buildList {
            if (contains("Spring", ignoreCase = true)) add(ShoppingSeason.SPRING)
            if (contains("Summer", ignoreCase = true)) add(ShoppingSeason.SUMMER)
            if (contains("Autumn", ignoreCase = true)) add(ShoppingSeason.AUTUMN)
            if (contains("Winter", ignoreCase = true)) add(ShoppingSeason.WINTER)
        }.ifEmpty { listOf(ShoppingSeason.ALL_SEASON) }
    }

    private fun String.toOccasions(): List<ShoppingOccasion> = when (this) {
        "Outerwear" -> listOf(ShoppingOccasion.EVERYDAY, ShoppingOccasion.OFFICE, ShoppingOccasion.TRAVEL)
        "Tops" -> listOf(ShoppingOccasion.EVERYDAY, ShoppingOccasion.OFFICE, ShoppingOccasion.TRAVEL)
        "Bottoms" -> listOf(ShoppingOccasion.EVERYDAY, ShoppingOccasion.OFFICE, ShoppingOccasion.TRAVEL)
        "Dresses" -> listOf(ShoppingOccasion.DATE, ShoppingOccasion.EVENT, ShoppingOccasion.OFFICE)
        "Shoes" -> listOf(ShoppingOccasion.EVERYDAY, ShoppingOccasion.OFFICE, ShoppingOccasion.TRAVEL)
        else -> listOf(ShoppingOccasion.EVERYDAY, ShoppingOccasion.OFFICE, ShoppingOccasion.DATE)
    }

    private fun String.toCapsuleRole(score: Int): CapsuleRole = when (this) {
        "Outerwear" -> CapsuleRole.LAYERING_PIECE
        "Dresses" -> CapsuleRole.OCCASION_PIECE
        "Accessories" -> CapsuleRole.ACCESSORY_ANCHOR
        else -> if (score >= 85) CapsuleRole.CORE_BASIC else CapsuleRole.SEASONAL_PIECE
    }
}

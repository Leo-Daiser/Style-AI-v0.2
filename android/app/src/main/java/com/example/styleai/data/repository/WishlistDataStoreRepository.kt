package com.example.styleai.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.styleai.data.local.dataStore
import com.example.styleai.domain.shopping.DuplicateRiskLevel
import com.example.styleai.domain.shopping.ShoppingCategory
import com.example.styleai.domain.shopping.ShoppingColorDirection
import com.example.styleai.domain.shopping.ShoppingOccasion
import com.example.styleai.domain.shopping.ShoppingSeason
import com.example.styleai.domain.wishlist.PurchasePriority
import com.example.styleai.domain.wishlist.PurchasePriorityEngine
import com.example.styleai.domain.wishlist.WishlistItem
import com.example.styleai.domain.wishlist.WishlistRepository
import com.example.styleai.domain.wishlist.WishlistSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.json.JSONArray
import org.json.JSONObject

class WishlistDataStoreRepository(
    private val context: Context
) : WishlistRepository {
    private val wishlistKey = stringPreferencesKey("purchase_queue_json")

    override fun observeWishlist(): Flow<List<WishlistItem>> {
        return context.dataStore.data.map { prefs ->
            decodeWishlist(prefs[wishlistKey]).sortedWith(
                compareBy<WishlistItem> { it.isArchived }
                    .thenByDescending { it.wardrobeValueScore }
                    .thenByDescending { it.createdAtMillis }
            )
        }
    }

    override suspend fun addWishlistItem(item: WishlistItem) {
        val scored = PurchasePriorityEngine.withUpdatedPriority(item)
        val current = observeWishlist().first().filterNot { it.id == scored.id }
        writeWishlist(listOf(scored) + current)
    }

    override suspend fun updateWishlistItem(item: WishlistItem) {
        addWishlistItem(item)
    }

    override suspend fun archiveWishlistItem(id: String) {
        val updated = observeWishlist().first().map { item ->
            if (item.id == id) item.copy(isArchived = true) else item
        }
        writeWishlist(updated)
    }

    override suspend fun deleteWishlistItem(id: String) {
        writeWishlist(observeWishlist().first().filterNot { it.id == id })
    }

    override suspend fun clearWishlist() {
        writeWishlist(emptyList())
    }

    private suspend fun writeWishlist(items: List<WishlistItem>) {
        context.dataStore.edit { prefs ->
            prefs[wishlistKey] = encodeWishlist(items)
        }
    }

    private fun encodeWishlist(items: List<WishlistItem>): String {
        val array = JSONArray()
        items.forEach { item ->
            array.put(
                JSONObject()
                    .put("id", item.id)
                    .put("title", item.title)
                    .put("category", item.category.name)
                    .put("colorDirection", item.colorDirection.name)
                    .put("occasion", item.occasion.name)
                    .put("season", item.season.name)
                    .put("source", item.source.name)
                    .put("priority", item.priority.name)
                    .put("wardrobeValueScore", item.wardrobeValueScore)
                    .put("duplicateRiskLevel", item.duplicateRiskLevel?.name)
                    .put("expectedOutfitGain", item.expectedOutfitGain)
                    .put("reason", item.reason)
                    .put("createdAtMillis", item.createdAtMillis)
                    .put("createdDateLabel", item.createdDateLabel)
                    .put("isArchived", item.isArchived)
            )
        }
        return array.toString()
    }

    private fun decodeWishlist(rawJson: String?): List<WishlistItem> {
        if (rawJson.isNullOrBlank()) return emptyList()
        return try {
            val array = JSONArray(rawJson)
            buildList {
                for (index in 0 until array.length()) {
                    val item = array.getJSONObject(index)
                    add(
                        WishlistItem(
                            id = item.getString("id"),
                            title = item.getString("title"),
                            category = ShoppingCategory.valueOf(item.getString("category")),
                            colorDirection = ShoppingColorDirection.valueOf(item.getString("colorDirection")),
                            occasion = ShoppingOccasion.valueOf(item.getString("occasion")),
                            season = ShoppingSeason.valueOf(item.getString("season")),
                            source = WishlistSource.valueOf(item.getString("source")),
                            priority = PurchasePriority.valueOf(item.getString("priority")),
                            wardrobeValueScore = item.optInt("wardrobeValueScore", 0),
                            duplicateRiskLevel = item.optString("duplicateRiskLevel")
                                .takeIf { it.isNotBlank() && it != "null" }
                                ?.let { DuplicateRiskLevel.valueOf(it) },
                            expectedOutfitGain = item.optString("expectedOutfitGain"),
                            reason = item.optString("reason"),
                            createdAtMillis = item.optLong("createdAtMillis", 0L),
                            createdDateLabel = item.optString("createdDateLabel"),
                            isArchived = item.optBoolean("isArchived", false)
                        )
                    )
                }
            }
        } catch (exception: Exception) {
            emptyList()
        }
    }
}

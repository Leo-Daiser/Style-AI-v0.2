package com.example.styleai.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.styleai.data.local.dataStore
import com.example.styleai.domain.decisions.SavedDecision
import com.example.styleai.domain.decisions.SavedDecisionRepository
import com.example.styleai.domain.shopping.DuplicateRiskLevel
import com.example.styleai.domain.shopping.ShoppingCategory
import com.example.styleai.domain.shopping.ShoppingColorDirection
import com.example.styleai.domain.shopping.ShoppingOccasion
import com.example.styleai.domain.shopping.ShoppingSeason
import com.example.styleai.domain.shopping.ShoppingVerdict
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.json.JSONArray
import org.json.JSONObject

class SavedDecisionDataStoreRepository(
    private val context: Context
) : SavedDecisionRepository {
    private val decisionsKey = stringPreferencesKey("saved_shopping_decisions_json")

    override fun observeDecisions(): Flow<List<SavedDecision>> {
        return context.dataStore.data.map { prefs ->
            decodeDecisions(prefs[decisionsKey]).sortedByDescending { it.createdAtMillis }
        }
    }

    override suspend fun saveDecision(decision: SavedDecision) {
        val current = observeDecisions().first()
            .filterNot { it.id == decision.id }
        writeDecisions(listOf(decision) + current)
    }

    override suspend fun deleteDecision(id: String) {
        writeDecisions(observeDecisions().first().filterNot { it.id == id })
    }

    override suspend fun clearDecisions() {
        writeDecisions(emptyList())
    }

    override suspend fun getDecisionById(id: String): SavedDecision? {
        return observeDecisions().first().firstOrNull { it.id == id }
    }

    private suspend fun writeDecisions(decisions: List<SavedDecision>) {
        val json = encodeDecisions(decisions.sortedByDescending { it.createdAtMillis })
        context.dataStore.edit { prefs ->
            prefs[decisionsKey] = json
        }
    }

    private fun encodeDecisions(decisions: List<SavedDecision>): String {
        val array = JSONArray()
        decisions.forEach { decision ->
            array.put(
                JSONObject()
                    .put("id", decision.id)
                    .put("category", decision.category.name)
                    .put("colorDirection", decision.colorDirection.name)
                    .put("occasion", decision.occasion.name)
                    .put("season", decision.season.name)
                    .put("verdict", decision.verdict.name)
                    .put("score", decision.score)
                    .put("mainReason", decision.mainReason)
                    .put("positiveReasons", JSONArray(decision.positiveReasons))
                    .put("risks", JSONArray(decision.risks))
                    .put("estimatedOutfitCount", decision.estimatedOutfitCount)
                    .put("capsuleImpact", decision.capsuleImpact)
                    .put("recommendation", decision.recommendation)
                    .put("createdAtMillis", decision.createdAtMillis)
                    .put("createdDateLabel", decision.createdDateLabel)
                    .put("duplicateRiskLevel", decision.duplicateRiskLevel?.name)
                    .put("duplicateRiskScore", decision.duplicateRiskScore)
                    .put("similarOwnedItems", JSONArray(decision.similarOwnedItems))
                    .put("comparisonReason", decision.comparisonReason)
                    .put("betterAlternativeHint", decision.betterAlternativeHint)
                    .put("wardrobeGapMatch", decision.wardrobeGapMatch)
            )
        }
        return array.toString()
    }

    private fun decodeDecisions(rawJson: String?): List<SavedDecision> {
        if (rawJson.isNullOrBlank()) return emptyList()
        return try {
            val array = JSONArray(rawJson)
            buildList {
                for (index in 0 until array.length()) {
                    val item = array.getJSONObject(index)
                    add(
                        SavedDecision(
                            id = item.getString("id"),
                            category = ShoppingCategory.valueOf(item.getString("category")),
                            colorDirection = ShoppingColorDirection.valueOf(item.getString("colorDirection")),
                            occasion = ShoppingOccasion.valueOf(item.getString("occasion")),
                            season = ShoppingSeason.valueOf(item.getString("season")),
                            verdict = ShoppingVerdict.valueOf(item.getString("verdict")),
                            score = item.getInt("score"),
                            mainReason = item.getString("mainReason"),
                            positiveReasons = item.getStringList("positiveReasons"),
                            risks = item.getStringList("risks"),
                            estimatedOutfitCount = item.getString("estimatedOutfitCount"),
                            capsuleImpact = item.getString("capsuleImpact"),
                            recommendation = item.getString("recommendation"),
                            createdAtMillis = item.getLong("createdAtMillis"),
                            createdDateLabel = item.getString("createdDateLabel"),
                            duplicateRiskLevel = item.optString("duplicateRiskLevel")
                                .takeIf { it.isNotBlank() && it != "null" }
                                ?.let { DuplicateRiskLevel.valueOf(it) },
                            duplicateRiskScore = item.takeOptionalInt("duplicateRiskScore"),
                            similarOwnedItems = item.getStringList("similarOwnedItems"),
                            comparisonReason = item.takeOptionalString("comparisonReason"),
                            betterAlternativeHint = item.takeOptionalString("betterAlternativeHint"),
                            wardrobeGapMatch = item.optBoolean("wardrobeGapMatch", false)
                        )
                    )
                }
            }
        } catch (exception: Exception) {
            emptyList()
        }
    }

    private fun JSONObject.getStringList(name: String): List<String> {
        val array = optJSONArray(name) ?: return emptyList()
        return buildList {
            for (index in 0 until array.length()) {
                add(array.optString(index))
            }
        }
    }

    private fun JSONObject.takeOptionalString(name: String): String? {
        return optString(name).takeIf { it.isNotBlank() && it != "null" }
    }

    private fun JSONObject.takeOptionalInt(name: String): Int? {
        return if (has(name) && !isNull(name)) optInt(name) else null
    }
}
